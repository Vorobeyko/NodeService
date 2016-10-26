package com.nodeservice.DBOperation;

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.Configuration.Properties;
import com.nodeservice.sender.MailSender;
import com.nodeservice.instance.Cameras;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class VerifyDate implements IVerifyDate {
    private final Logger _log = LogManager.getLogger(this.getClass());
    private SessionFactory sessionFactory;
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
        String sqlQuery = "select count(*) as id from SourceInfo";
        SQLQuery query = session.createSQLQuery(sqlQuery).addScalar("id", LongType.INSTANCE);
        List<Long> result = query.list();
        Long count = result.get(0);

        for (int i = 1; i <= count; i++ ) {
            Cameras source = (Cameras) session.get(Cameras.class, i);
            if (source.getDueData() != null) {
                if (new Date().after(source.getDueData())) {
                    String stringSQLQuery = "UPDATE sourceinfo SET OwnBy ='', DueData=NULL, State='free' WHERE SourceId = " + source.getSourceId();
                    SQLQuery sqlUpdateQuery = session.createSQLQuery(stringSQLQuery);
                    try {
                        MailSender mailSender = new MailSender(myProperties.getProperty("SENDER"), myProperties.getProperty("PASSWORD"));
                        ActiveDirectory ad = new ActiveDirectory();
                        ad.setLDAPConnection();
                        String userEmail = ad.getUsersEmail(source.getOwnBy());
                        if (userEmail != null) {
                            mailSender.send("Время бронирования источника с IP = " + source.getSourceIp() + " вышло.", //Тема письм
                                    "<h2>Время бронирования источника с IP = " + source.getSourceIp() + " вышло.</h2>", // Текст письма
                                    myProperties.getProperty("SENDER"), // От кого отправлять
                                    userEmail); // Кому?
                            _log.info("Сообщение успешно отправлено: " + userEmail);
                        }else
                            _log.error("Сообщение отправлено с ошибкой. Вероятно, что пользователя с именем " + source.getOwnBy() + " в базе ActiveDirectory не существует");

                        sqlUpdateQuery.executeUpdate();
                        _log.info(stringSQLQuery);
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        _log.error("Ошибка при отапрвалении сообщения пользователю");
                    } catch (NamingException e) {
                        e.printStackTrace();
                        _log.error("Ошибка в имени. Проблема с ActiveDirectory");
                    }
                }
            }
        }
    }
}
