package com.nodeservice.DBOperation;

import com.nodeservice.instance.Cameras;
import com.nodeservice.instance.Computers;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VorobeyAlex on 03.11.2016.
 */
@Repository
@Transactional
public class ComputersDBO implements IDataBaseProvider<Computers> {

    private final Logger _log = LogManager.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    /**
     *
     * @param sessionFactory
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String update(Computers cameras, String authorizedUser) {
        return null;
    }

    @Override
    public String update(Computers items) {
        Session session = sessionFactory.getCurrentSession();
        try {
            String hql = "update Computers " +
                    "set computerIP = :computerIP, " +
                    "computerName = :computerName," +
                    "computerDescription = :computerDescription," +
                    "owner = :owner " + "where computerIp = :computerIp";
            Query query = session.createQuery(hql);
            query.setParameter("computerIP", items.getComputerIP());
            query.setParameter("computerName", items.getComputerName() != null ? items.getComputerName() : "");
            query.setParameter("computerDescription", items.getComputerDescription() != null ? items.getComputerDescription() : "");
            query.setParameter("owner", items.getOwner() != null ? items.getOwner() : "");
            query.setParameter("computerIp", items.getComputerIP());

            query.executeUpdate();
            _log.info("Обновление источника с IP-адресом " + items.getComputerIP() + " произошло успешно");
            return "success";// Все удачно добавлено, возвращаем success
        } catch (NullPointerException e){
            _log.error("Некорректно введен один из параметров " + e);
            return "error";//ошибка, возвращаем error
        }
    }

    @Override
    public String add(Computers cameras, String authorizedUser) {
        return null;
    }

    @Override
    public String add(Computers items) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from Computers");
        Long count = (Long)query.uniqueResult();
        try {
            for (int i = 1; i < count + 1; i++) {
                Computers source = (Computers) session.get(Computers.class, i);
                if (items.getComputerIP().equals(source.getComputerIP())) {
                    _log.info("Устройство с IP-адресом " + items.getComputerIP() + " уже добавлен в Базу Данных. Вызвана ошибка с кодом 601");
                    return "failed";// Источник с таким IP найден в БД, возвращаем failed
                }
            }
            session.save(items);

            _log.info("Устройство с IP-адресом " + items.getComputerIP() + " успешно добавлен");
            return "success";// Все удачно добавлено, возвращаем success
        } catch (NullPointerException e){
            _log.error("Произошла ошибка при добавлении устройства " + e);
            return "error";//ошибка, возвращаем error
        }
    }

    @Override
    public void removeFromReservation(Computers cameras, String username) {

    }

    @Override
    public void delete(Computers cameras) {

    }

    @Override
    public List<Computers> select() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(*) from Computers");
        Long count = (Long)query.uniqueResult();

        List<Computers> listItems = new ArrayList();

        for (int i = 1; i <= count; i++) {
            Computers source = (Computers) session.get(Computers.class, i);

            listItems.add(new Computers(
                    source.getComputerIP(),
                    source.getComputerName(),
                    source.getComputerDescription(),
                    source.getOwner()
                    )
            );
        }
        return listItems;
    }

    @Override
    public List getHistory(String item) {
        return null;
    }
}
