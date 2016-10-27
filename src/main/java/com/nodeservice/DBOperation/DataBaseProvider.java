package com.nodeservice.DBOperation;

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.instance.Cameras;
import com.nodeservice.instance.History;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by avorobey on 21.09.2016.
 */

@Repository
@Transactional
public class DataBaseProvider implements IDataBaseProvider {
    private final Logger _log = LogManager.getLogger(this.getClass());
    private SessionFactory sessionFactory;
    private SimpleDateFormat formatCur = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String formatCurDate = formatCur.format(new Date());

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
    public String updateSource(Cameras cameras, String authorizedUser) {
        Session session = sessionFactory.getCurrentSession();
        try {
            String stringSQLQuery =
                String.format(
                    "UPDATE sourceinfo " +
                            "SET SourceModel='%s', SourceDescription='%s', OwnBy='%s',  Comments='%s', DueData=%s, State='%s'" +
                            " WHERE SourceIp='%s'",
                        cameras.getSourceModel(),
                        cameras.getSourceDescription() != null ? cameras.getSourceDescription() : "\'\'",
                        getStringOwnBy(cameras.getDueData(), authorizedUser),
                        cameras.getComments() != null ? cameras.getComments() : "\'\'",
                        getStringDueData(cameras.getDueData()),
                        getStringState(cameras.getDueData()),
                        cameras.getSourceIp()
                    );
            _log.info(stringSQLQuery);
            SQLQuery sqlQuery = session.createSQLQuery(stringSQLQuery);
            sqlQuery.executeUpdate();

            Cameras camera = getCameras(session, cameras.getSourceIp());
            setHistory(camera,session);

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
    public String addSource(Cameras cameras, String authorizedUser) {
        Session session = sessionFactory.getCurrentSession();
        String selectCount = "select count(*) as id from SourceInfo";
        SQLQuery query = session.createSQLQuery(selectCount).addScalar("id", LongType.INSTANCE);
        List<Long> result = query.list();
        Long count = result.get(0);
        Boolean existNote = false;
        try {
            for (int i = 1; i < count + 1; i++) {
                Cameras source = (Cameras) session.get(Cameras.class, i);
                if (cameras.getSourceIp().equals(source.getSourceIp())) {
                    existNote = true;
                    _log.info("Устройство с IP-адресом " + cameras.getSourceIp() + " уже добавлен в Базу Данных. Вызвана ошибка с кодом 601");
                    return "failed";// Источник с таким IP найден в БД, возвращаем failed
                }
            }

            if (!existNote){
                String addSourceSQL = String.format(
                        "INSERT INTO sourceinfo (SourceId, SourceIp, SourceModel, SourceDescription, Comments, OwnBy, DueData, State, DeletedSource) " +
                                "VALUES ('%s','%s','%s','%s','%s','%s',%s,'%s',%s)",
                        count+1,
                        cameras.getSourceIp(),
                        cameras.getSourceModel(),
                        cameras.getSourceDescription() != null ? cameras.getSourceDescription() : "\'\'",
                        cameras.getComments() != null ? cameras.getComments() : "\'\'",
                        getStringOwnBy(cameras.getDueData(), authorizedUser),
                        getStringDueData(cameras.getDueData()),
                        getStringState(cameras.getDueData()),
                        "b\'0\'"
                );
                _log.info(addSourceSQL);

                SQLQuery sqlQuery = session.createSQLQuery(addSourceSQL);
                sqlQuery.executeUpdate();

                Cameras camera = getCameras(session, cameras.getSourceIp());
                setHistory(camera,session);
                _log.info("Устройство с IP-адресом " + cameras.getSourceIp() + " успешно добавлен");
                return "success";// Все удачно добавлено, возвращаем success
            }
        } catch (NullPointerException e){
            _log.error("Произошла ошибка при добавлении устройства " + e);
            return "error";//ошибка, возвращаем error
        } catch (NamingException e) {
            e.printStackTrace();
            return "error";//ошибка, возвращаем error
        }
        _log.warn("При добавлении устройства произошла неизвестная ошибка. Устройство не добавлено");
        return "unknown";//Неизвестная причина ошибки
    }

    /**
     *
     * @param cameras
     */
    @Override
    public void deleteSource(Cameras cameras) {
        Session session = sessionFactory.getCurrentSession();
        String stringSQLQuery = String.format(
                "UPDATE sourceinfo " +
                        "SET deletedSource=b'1' " +
                        "WHERE SourceIp='%s'",
                cameras.getSourceIp()
        );
        _log.info(stringSQLQuery);
        SQLQuery sqlQuery = session.createSQLQuery(stringSQLQuery);
        sqlQuery.executeUpdate();
    }

    /**
     *
     * @return
     */
    @Override
    public List<Cameras> selectSourceNotDeleted() {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT count(*) AS id FROM SourceInfo";
        SQLQuery query = session.createSQLQuery(sqlQuery).addScalar("id", LongType.INSTANCE);
        List<Long> result = query.list();
        Long count = result.get(0);

        List<Cameras> listItems = new ArrayList();

        for (int i = 1; i <= count; i++) {
            Cameras source = (Cameras) session.get(Cameras.class, i);
            if(!source.getDeletedSource()) {
                listItems.add(new Cameras(
                                    source.getSourceIp(),
                                    source.getSourceModel(),
                                    source.getSourceDescription(),
                                    source.getOwnBy(),
                                    source.getComments(),
                                    source.getDueData(),
                                    source.getState()
                        )
                );
            }
        }
        return listItems;
    }

    /**
     *
     * @param cameras
     * @param session
     */
    public void setHistory (Cameras cameras, Session session){
        String sqlQuery = String.format(
                "INSERT INTO history (LastUpdated, SourceIp, SourceModel, SourceDescription, Comments, OwnBy, DueData)" +
                    " values ('%s','%s','%s','%s','%s','%s',%s)",
                formatCurDate,
                cameras.getSourceIp(),
                cameras.getSourceModel(),
                cameras.getSourceDescription(),
                cameras.getComments(),
                cameras.getOwnBy(),
                getStringDueData(cameras.getDueData())
        );
        SQLQuery query = session.createSQLQuery(sqlQuery);

        query.executeUpdate();
        _log.info(sqlQuery);
    }

    @Override
    public List<History> getHistory(String item){
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = String.format(
                "Select * FROM history WHERE SourceIp = '%s' ORDER BY LastUpdated DESC LIMIT 5",
                item
        );
        SQLQuery selectHistoryNode = session.createSQLQuery(sqlQuery);
        selectHistoryNode.addEntity(History.class);
        List selectedHistory = selectHistoryNode.list();
        return selectedHistory;
    }

    /**
     * Метод для получения полного имени юзера в формате Ф.И.О
     * @param DueData
     * @param user
     * @return Полное имя пользователя
     * @throws NamingException
     */
    private String getStringOwnBy(Date DueData, String user) throws NamingException {
        try {
            if (DueData != null) {
                if (user.equals("admin")) {
                    return "admin";
                }
                ActiveDirectory ad = new ActiveDirectory();
                ad.setLDAPConnection();
                return ad.getNameUser(user);
            }
        } catch (NullPointerException e){
            _log.error("Произошла ошибка в получении пользователя, вероятно пользователь зашел под логином admin");
        }
        return "";
    }

    /**
     *
     * @param session
     * @param SourceIp
     * @return
     */
    private Cameras getCameras(Session session, String SourceIp){
        SQLQuery query = session.createSQLQuery("SELECT * FROM sourceinfo WHERE SourceIp = \'"+ SourceIp +"\'");
        query.addEntity(Cameras.class);
        List cameras = query.list();

        for (Iterator iterator = cameras.iterator(); iterator.hasNext();) {
            Cameras employee = (Cameras) iterator.next();
            return employee;
        }
        return null;
    }

    /**
     * Метод проверяет введена ли дата пользователем.
     * Если пользователь ввел дату, то возвращается сама дата
     * Иначе возвращем NULL
     * @param DueData
     * @return DueData или NULL
     */
    private String getStringDueData(Date DueData){
        try {
            if (DueData == null) {
                return "NULL";
            } else {
                return "\'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DueData) + "\'";
            }
        }catch (NullPointerException e){
            _log.error("Вызвано исключение: " + e + ". Значение даты равно null, что некорректно для выполнения запроса на обновление или добавление источника.");
            return "NULL";
        }
    }

    /**
     * Метод возвращает состояние устройства free или busy в зависимости от DueData.
     * Если DueData указана, устрйоство - busy, иначе - free
     * @param DueData
     * @return State
     */
    private String getStringState(Date DueData){
        if (DueData != null) {
            return "busy";
        }
        else {
            return "free";
        }
    }
}
