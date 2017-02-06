package com.nodeservice.DBOperation;

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.Configuration.Properties;
import com.nodeservice.sender.MailSender;
import com.nodeservice.instance.Cameras;

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
import java.util.Date;

@Repository
@Transactional
public class VerifyDate implements IVerifyDate {
    private final Logger _log = LogManager.getLogger(this.getClass());
    private SessionFactory sessionFactory;
    //private Environment myProperties;
    @Autowired
    private Environment myProperties;

    public VerifyDate(){
        this.myProperties = Properties.env;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void verify(){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from Cameras");
        Long count = (Long)query.uniqueResult();

        for (int i = 1; i <= count; i++ ) {
            Cameras source = (Cameras) session.get(Cameras.class, i);
            if (source.getDueData() != null) {
                if (new Date().after(source.getDueData())) {
                    Query sqlUpdateQuery = session.createQuery("update Cameras set ownBy = :ownBy, dueData = :dueData, comments = :comments, state = :state " +
                            "where sourceId = :sourceId");
                    sqlUpdateQuery.setParameter("ownBy", "");
                    sqlUpdateQuery.setParameter("dueData", null);
                    sqlUpdateQuery.setParameter("comments", "");
                    sqlUpdateQuery.setParameter("state", "free");
                    sqlUpdateQuery.setParameter("sourceId",source.getSourceId());

                    sqlUpdateQuery.executeUpdate();

                    try {
                        MailSender mailSender = new MailSender(myProperties.getProperty("SENDER"), myProperties.getProperty("PASSWORD"));
                        ActiveDirectory ad = new ActiveDirectory();
                        String userEmail = ad.getUsersEmail(source.getOwnBy());
                        if (userEmail != null) {
                        new Thread(() -> {
                            mailSender.send("Время бронирования вышло.", //Тема письм
                                    "Время бронирования источника с IP = " + source.getSourceIp() + " вышло.", // Текст письма
                                    myProperties.getProperty("SENDER"), // От кого отправлять
                                    userEmail); // Кому?
                            _log.info("Сообщение успешно отправлено: " + userEmail);
                        }).start();
                        }else
                            _log.error("Сообщение отправлено с ошибкой. Вероятно, что пользователя с именем " + source.getOwnBy() + " в базе ActiveDirectory не существует");

                    } catch (NullPointerException e){
                        e.printStackTrace();
                        _log.error("Ошибка при отправалении сообщения пользователю");
                    } catch (NamingException e) {
                        e.printStackTrace();
                        _log.error("Ошибка в имени. Проблема с ActiveDirectory");
                    }
                }
            }
        }
    }
}
