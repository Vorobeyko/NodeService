package com.nodeservice.DBOperation;

import javax.naming.NamingException;
import java.util.List;

/**
 * Created by avorobey on 27.09.2016.
 */
public interface IDataBaseProvider<T> {

    String update(T cameras, String authorizedUser);

    String add(T cameras, String authorizedUser);

    void removeFromReservation(T cameras, String username) throws NamingException;

    void delete(T cameras);

    List<T> select();

    List getHistory(String item);

}
