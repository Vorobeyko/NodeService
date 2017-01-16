package com.nodeservice.DBOperation;

import com.nodeservice.instance.Cameras;

import java.util.List;

/**
 * Created by avorobey on 27.09.2016.
 */
public interface IDataBaseProvider<T> {

    String update(T cameras, String authorizedUser);

    String add(T cameras, String authorizedUser);

    void removeFromReservation(T cameras);

    void delete(T cameras);

    List<T> select();

    List getHistory(String item);

}
