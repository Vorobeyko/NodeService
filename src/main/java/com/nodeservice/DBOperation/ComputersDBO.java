package com.nodeservice.DBOperation;

import com.nodeservice.instance.Cameras;
import com.nodeservice.instance.Computers;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
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
    public String update(Cameras cameras, String authorizedUser) {
        return null;
    }

    @Override
    public String add(Cameras cameras, String authorizedUser) {
        return null;
    }

    @Override
    public void delete(Cameras cameras) {

    }

    @Override
    public List<Computers> select() {
        Session session = sessionFactory.getCurrentSession();
        String sqlQuery = "SELECT count(*) AS id FROM Computers";
        SQLQuery query = session.createSQLQuery(sqlQuery).addScalar("id", LongType.INSTANCE);
        List<Long> result = query.list();
        Long count = result.get(0);

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
