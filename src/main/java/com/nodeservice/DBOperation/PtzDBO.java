package com.nodeservice.DBOperation;

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.Configuration.Properties;
import com.nodeservice.instance.Cameras;
import com.nodeservice.instance.History;
import com.nodeservice.sender.MailSender;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import java.util.*;

/**
 * Created by avorobey on 21.09.2016.
 */

@Repository
@Transactional
public class PtzDBO implements IDataBaseProvider<Cameras> {
    private final Logger _log = LogManager.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    @Autowired
    private Environment myProperties = Properties.env;

    /**
     *
     * @param sessionFactory
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * @param cameras
     * @param authorizedUser
     * @return
     */
    @Override
    public String update(Cameras cameras, String authorizedUser) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Query getSourceOwnBy = session.createQuery("select ownBy from Cameras where sourceIp = :sourceIp");
            getSourceOwnBy.setParameter("sourceIp", cameras.getSourceIp());

            Query getSourceDueData = session.createQuery("select dueData from Cameras where sourceIp = :sourceIp");
            getSourceDueData.setParameter("sourceIp", cameras.getSourceIp());

            Query getSourceState = session.createQuery("select state from Cameras where sourceIp = :sourceIp");
            getSourceState.setParameter("sourceIp", cameras.getSourceIp());
            String ownBy = (String) getSourceOwnBy.list().get(0);

            String hql = "update Cameras " +
                    "set sourceModel = :sourceModel, " +
                    "sourceDescription = :sourceDescription," +
                    "audioCodec = :audioCodec," +
                    "comments = :comments," +
                    "ownBy = :ownBy," +
                    "dueData = :dueData," +
                    "state = :state where sourceIp = :sourceIp";
            Query query = session.createQuery(hql);
            query.setParameter("sourceModel", cameras.getSourceModel());
            query.setParameter("sourceDescription", cameras.getSourceDescription() != null ? cameras.getSourceDescription() : "");
            query.setParameter("audioCodec", cameras.getAudioCodec());
            query.setParameter("comments", cameras.getComments() != null ? cameras.getComments() : "");
            // TODO: Сделать нормальной логику

            if (ownBy == "" || ownBy.isEmpty()) {
                query.setParameter("ownBy", getStringOwnBy(cameras.getDueData(), authorizedUser));
                query.setParameter("dueData", cameras.getDueData());
                query.setParameter("state", getStringState(cameras.getDueData()));
            }else {
                query.setParameter("ownBy", getSourceOwnBy.list().get(0));
                query.setParameter("dueData", getSourceDueData.list().get(0));
                query.setParameter("state", getSourceState.list().get(0));
            }

            query.setParameter("sourceIp", cameras.getSourceIp());

            query.executeUpdate();

            Cameras camera = getCameras(session, cameras.getSourceIp());
            setHistory(camera,session,authorizedUser);

            _log.info("Обновление источника с IP-адресом " + cameras.getSourceIp() + " произошло успешно");
            return "success";// Все удачно добавлено, возвращаем success
        } catch (NullPointerException e){
            _log.error("Некорректно введен один из параметров " + e);
            return "error";//ошибка, возвращаем error
        } catch (NamingException e) {
            e.printStackTrace();
            return "error";//ошибка, возвращаем error
        }
    }

    @Override
    public String update(Cameras items) {
        return null;
    }

    /**
     * Метод для добавления устройства в БД.
     * @param cameras - информация о добавляемом устройстве
     * @param authorizedUser - авторизованный пользователь
     * @return String
     *              success (запрос выполнен удачно),
     *              failed (добавляемый источник уже существует в БД),
     *              error (произошла одна из известных ошибок NullPointerException или NamingException),
     *              unknown (неизвестная ошибка, источник не добавлен)
     */
    @Override
    public String add(Cameras cameras, String authorizedUser) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from Cameras");
        Long count = (Long)query.uniqueResult();
        try {
            for (int i = 1; i < count + 1; i++) {
                Cameras source = (Cameras) session.get(Cameras.class, i);
                if (cameras.getSourceIp().equals(source.getSourceIp())) {
                    //TODO: Проверить флаг DeletedSource и если сорс удален, то восстановить его
                    _log.info("Устройство с IP-адресом " + cameras.getSourceIp() + " уже добавлен в Базу Данных. Вызвана ошибка с кодом 601");
                    return "failed";// Источник с таким IP найден в БД, возвращаем failed
                }
            }
            cameras.setOwnBy(getStringOwnBy(cameras.getDueData(), authorizedUser));//Set Own By
            session.save(cameras);

            Cameras camera = getCameras(session, cameras.getSourceIp());
            setHistory(camera,session, authorizedUser);
            _log.info("Устройство с IP-адресом " + cameras.getSourceIp() + " успешно добавлен");
            return "success";// Все удачно добавлено, возвращаем success
        } catch (NullPointerException e){
            _log.error("Произошла ошибка при добавлении устройства " + e);
            return "error";//ошибка, возвращаем error
        } catch (NamingException e) {
            e.printStackTrace();
            return "error";//ошибка, возвращаем error
        }
    }

    @Override
    public String add(Cameras items) {
        return null;
    }

    /**
     *
     */
    @Override
    public void removeFromReservation(Cameras cameras, String authorizedUser) throws NamingException {
        Session session = sessionFactory.getCurrentSession();
        if (cameras.getDueData() != null) {
            Query query = session.createQuery("update Cameras " + "set dueData = :dueData, " +
                    "ownBy = :ownBy," +
                    "comments = :comments," +
                    "state = :state" +
                    " where sourceIp = :sourceIp");
            query.setParameter("dueData",null);
            query.setParameter("ownBy","");
            query.setParameter("comments","");
            query.setParameter("state","free");
            query.setParameter("sourceIp",cameras.getSourceIp());

            ActiveDirectory ad = new ActiveDirectory();

            Query resultQuery = session.createQuery("select ownBy from Cameras where sourceIp = :sourceIp");
            resultQuery.setParameter("sourceIp", cameras.getSourceIp());
            List getOwnByFromDB = resultQuery.list(); // В переменной хранится OwnBy текущего сорса

            String toEmail = ad.getUsersEmail(getOwnByFromDB.get(0).toString()),
                    fullName = ad.getNameUser(authorizedUser);

            if (query.executeUpdate()>0){
//                _log.info(stringSQLQuery);
                MailSender mailSender = new MailSender(myProperties.getProperty("SENDER"), myProperties.getProperty("PASSWORD"));
                if (!fullName.equals(getOwnByFromDB.get(0).toString()) && toEmail != null) {
                    mailSender.send(
                            "Вам сбросили бронирование.",
                            "Пользователь " + fullName + " сбросил Ваше бронирование для устройства " + cameras.getSourceIp() + "!",
                            myProperties.getProperty("SENDER"),
                            toEmail);
                    _log.info("Сообщение отправлено пользователю " + getOwnByFromDB.get(0).toString());
                }
                else {
                    _log.error("При сбросе бронирования другим пользователем произошла ошибка NullPointerException. Парамет toEmail не может быть равен null");
                }
                Cameras camera = getCameras(session, cameras.getSourceIp());
                setHistory(camera, session, authorizedUser);
            }
        }
        else
            _log.info("Срок бронирования для источника " + cameras.getSourceIp() + " не указан");
    }

    /**
     *
     * @param cameras
     */
    @Override
    public void delete(Cameras cameras) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update Cameras set deletedSource = :deletedSource where sourceIp =:sourceIp");
        query.setParameter("deletedSource",true);
        query.setParameter("sourceIp", cameras.getSourceIp());

//        _log.info(stringSQLQuery);
        query.executeUpdate();
    }

    /**
     *
     * @return
     */
    @Override
    public List<Cameras> select() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Cameras where deletedSource != 1");
        List<Cameras> listItems = query.list();
        return listItems;
    }

    /**
     *
     * @param cameras
     * @param session
     */
    public void setHistory (Cameras cameras, Session session, String _authorizedUser) throws NamingException {
        History sourceHistory = new History(
                new Date(),
                new ActiveDirectory().getNameUser(_authorizedUser),
                cameras.getSourceIp(),
                cameras.getSourceModel(),
                cameras.getSourceDescription(),
                cameras.getOwnBy(),
                cameras.getComments(),
                cameras.getDueData()
        );

        session.save(sourceHistory);
    }

    @Override
    public List<History> getHistory(String sourceIp){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from History where sourceIp = :sourceIp order by lastUpdated desc");
        query.setParameter("sourceIp", sourceIp);
        query.setMaxResults(5);
        return query.list();
    }

    /**
     * Метод для получения полного имени юзера в формате Ф.И.О
     * @param DueData
     * @param user
     * @return Полное имя пользователя
     * @throws NamingException
     */
    private String getStringOwnBy(Date DueData, String user) throws NamingException {
            if (DueData != null) {
                if (user.equals("admin"))
                    return "admin";

                ActiveDirectory ad = new ActiveDirectory();
                return ad.getNameUser(user);
            }else {
                _log.error("Произошла ошибка в получении пользователя, вероятно пользователь зашел под логином admin");
                return "";
            }
    }

    /**
     *
     * @param session
     * @param _sourceIp
     * @return
     */
    private Cameras getCameras(Session session, String _sourceIp){
        Query query = session.createQuery("from Cameras where sourceIp = :sourceIp");
        query.setParameter("sourceIp",_sourceIp);

        for (Iterator iterator = query.list().iterator(); iterator.hasNext();)
            return (Cameras) iterator.next();

        return null;
    }

    /**
     * Метод возвращает состояние устройства free или busy в зависимости от DueData.
     * Если DueData указана, устрйоство - busy, иначе - free
     * @param DueData
     * @return State
     */
    private String getStringState(Date DueData){
        if (DueData != null) return "busy";
        else return "free";
    }
}