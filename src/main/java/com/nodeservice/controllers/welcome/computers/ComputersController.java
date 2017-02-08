package com.nodeservice.controllers.welcome.computers;

import com.nodeservice.DBOperation.ComputersDBO;
import com.nodeservice.DBOperation.IDataBaseProvider;
import com.nodeservice.instance.Computers;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by VorobeyAlex on 06.11.2016.
 */

@Controller
public class ComputersController {
    private final Logger _log = LogManager.getLogger(this.getClass());

    @Autowired
    private IDataBaseProvider<Computers> computersDataBaseOperations = new ComputersDBO();

    @RequestMapping(value = "/welcome/computers",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Computers> getComputers(){
        return computersDataBaseOperations.select();
    }

    @RequestMapping(value = "/welcome/computers/{operation}",
    method = RequestMethod.POST)
    @ResponseBody
    public void addComputer(@RequestBody Computers computers,
                            @PathVariable("operation") String operation) {
        switch (operation) {
            case "add-computer":
                computersDataBaseOperations.add(computers);
                break;
            case "update-computer":
                computersDataBaseOperations.update(computers);
                break;
            case "delete-computer":
                computersDataBaseOperations.delete(computers);
                break;
            default:
                _log.error("Запрашиваемая операция не распозвнана");
        }
    }
}
