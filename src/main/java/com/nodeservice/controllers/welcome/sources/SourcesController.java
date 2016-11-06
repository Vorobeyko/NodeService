package com.nodeservice.controllers.welcome.sources;

import com.nodeservice.DBOperation.DataBaseProvider;
import com.nodeservice.DBOperation.IDataBaseProvider;
import com.nodeservice.instance.Cameras;
import com.nodeservice.instance.History;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by VorobeyAlex on 06.11.2016.
 */

@Controller
public class SourcesController {
    private final Logger _log = LogManager.getLogger(this.getClass());

    @Autowired
    IDataBaseProvider<Cameras> dataBaseProvider = new DataBaseProvider();

    /**
     * Метод для обновления источника
     * @param cameras
     * @param username
     * @param response
     */
    @RequestMapping(value = "/welcome/sources/{operation}",
            method = RequestMethod.POST)
    @ResponseBody
    public void updateSource(@RequestBody Cameras cameras,
                             @CookieValue(value = "username") String username,
                             HttpServletResponse response,
                             @PathVariable("operation") String operation) {
        switch (operation){
            case "add-source":
                String addSource = dataBaseProvider.add(cameras, username);
                operationResponse(addSource, response);
                break;
            case "update-source":
                String updateSource = null;
                if (cameras.getOwnBy() == null || cameras.getOwnBy().equals("")) {
                    updateSource = dataBaseProvider.update(cameras, username);
                } else {
                    updateSource = dataBaseProvider.update(cameras, "");
                }
                operationResponse(updateSource, response);
                break;
            case "delete-source":
                dataBaseProvider.delete(cameras);
                break;
            default:
                _log.error("Запрашиваемая операция не распозвнана");
        }
    }


    /**
     * Метод прослушивающий /getNote
     * @return возвращает список всех камер
     */
    @RequestMapping(value = "/welcome/sources/get-note",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Cameras> getNote(){
        List<Cameras> selectSourceInfo = dataBaseProvider.select();
        return selectSourceInfo;
    }

    /**
     * Метод прослушивающий /getNote
     * @param sourceIp
     * @return возвращает историю выбранного источника
     */
    @RequestMapping(value = "/welcome/sources/get-history",
            method = RequestMethod.POST)
    @ResponseBody
    public List<History> getHistory(@RequestBody String sourceIp){
        List<History> selectSourceInfo = dataBaseProvider.getHistory(sourceIp);
        return selectSourceInfo;
    }

    /**
     * Метод для генерации ошибок
     * @param operation
     * @param response
     */
    private void operationResponse(String operation, HttpServletResponse response){
        if (operation.equals("success")) response.setStatus(HttpServletResponse.SC_OK); //Код 200 - Устройство успещно обновлено
        else if (operation.equals("failed"))response.setStatus(601); //Код ошибки 601 - Устройство уже добавлено в БД
        else if (operation.equals("error"))response.setStatus(602); //Код ошибки 602 - Произошла одна из известных ошибок NullPointerException или NamingException
        else if (operation.equals("unknown")) response.setStatus(603); //Код ошибки 603 - Неизвестная ошибка, источник не добавлен
    }

}
