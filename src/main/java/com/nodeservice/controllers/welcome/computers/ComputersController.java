package com.nodeservice.controllers.welcome.computers;

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.DBOperation.ComputersDataBaseOperations;
import com.nodeservice.DBOperation.IDataBaseProvider;
import com.nodeservice.instance.Computers;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by VorobeyAlex on 06.11.2016.
 */

@Controller
public class ComputersController {
    private final Logger _log = LogManager.getLogger(this.getClass());

    @Autowired
    IDataBaseProvider<Computers> computersDataBaseOperations = new ComputersDataBaseOperations();

    @RequestMapping(value = "/welcome/computers",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Computers> getComputers(){
        List<Computers> selectSourceInfo = computersDataBaseOperations.select();
        return selectSourceInfo;
    }
}
