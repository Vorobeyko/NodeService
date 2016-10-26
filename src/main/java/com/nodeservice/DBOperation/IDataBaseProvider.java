package com.nodeservice.DBOperation;

import com.nodeservice.instance.Cameras;

import java.util.List;

/**
 * Created by avorobey on 27.09.2016.
 */
public interface IDataBaseProvider {

    void getAll();

    String updateSource(Cameras cameras, String authorizedUser);

    String addSource(Cameras cameras, String authorizedUser);

    void deleteSource(Cameras cameras);

    List<Cameras> selectSourceNotDeleted();

    List getHistory(String item);

}
