package com.nodeservice.DBOperation;

import com.nodeservice.instance.Cameras;

import java.util.List;

/**
 * Created by avorobey on 27.09.2016.
 */
public interface IDataBaseProvider<T> {

    String update(Cameras cameras, String authorizedUser);

    String add(Cameras cameras, String authorizedUser);

    void delete(Cameras cameras);

    List<T> select();

    List getHistory(String item);

}
