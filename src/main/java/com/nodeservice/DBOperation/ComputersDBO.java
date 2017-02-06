package com.nodeservice.DBOperation;

import com.nodeservice.instance.Computers;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public String add(Computers cameras, String authorizedUser) {
        return null;
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
        Query query = session.createQuery("select count(*) from Cameras");
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
