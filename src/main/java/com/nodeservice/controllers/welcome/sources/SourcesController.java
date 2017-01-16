package com.nodeservice.controllers.welcome.sources;

import com.nodeservice.DBOperation.PtzDBO;
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
    private IDataBaseProvider<Cameras> dataBaseProvider = new PtzDBO();

    /**
     * Метод для обновления источника
     * @param cameras
     * @param username
     * @param response
     */
    @RequestMapping(value = "/welcome/sources/{operation}",
            method = RequestMethod.POST)
    @ResponseBody
    public void source(@RequestBody Cameras cameras,
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
                if (cameras.getOwnBy() == null || cameras.getOwnBy().equals(""))
                    updateSource = dataBaseProvider.update(cameras, username);
                else
                    updateSource = dataBaseProvider.update(cameras, "");
                operationResponse(updateSource, response);
                break;
            case "remove-from-reservation":
                dataBaseProvider.removeFromReservation(cameras);
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
        switch (operation) {
            case "success":
                response.setStatus(HttpServletResponse.SC_OK); //Код 200 - Устройство успещно обновлено
                break;
            case "failed":
                response.setStatus(601); //Код ошибки 601 - Устройство уже добавлено в БД
                break;
            case "error":
                response.setStatus(602); //Код ошибки 602 - Произошла одна из известных ошибок NullPointerException или NamingException
                break;
            case "unknown":
                response.setStatus(603); //Код ошибки 603 - Неизвестная ошибка, источник не добавлен
                break;
        }
    }
}