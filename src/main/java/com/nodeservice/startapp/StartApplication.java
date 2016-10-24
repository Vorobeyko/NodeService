package com.nodeservice.startapp;

/**
 * Created by avorobey on 12.09.2016.
 * Контроллеры прослушивающие три адреса: корневой ('/'), логина ('/login') и главная ('welcome').
 * Данные авторизации отправляются на адрес /check
 **/

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.DBOperation.EntityOperation;
import com.nodeservice.DBOperation.IDBOperation;
import com.nodeservice.instance.Cameras;
import com.nodeservice.instance.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StartApplication {
    private final Logger _log = LogManager.getLogger(this.getClass());
    private static final int MAX_AGE_COOKIE = 3600*24*30; // Максимальное время жизни cookies
    private ActiveDirectory ad = new ActiveDirectory();
    @Autowired
    IDBOperation entityOperation = new EntityOperation();

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String main(@CookieValue (value = "username", required = false) String username){
        if (username != null){
            return "redirect:welcome";
        }else {
            return "redirect:login";
        }
    }

    @RequestMapping(value = "/login",
                    method = RequestMethod.GET)
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",new User());
        modelAndView.setViewName("login");
        return modelAndView;
    }
    /**
     * Метод для проверки существования пользователя в ActiveDirectory.
     * При удачной авторизации добавляет cookie в браузер с имененм пользователя.
     * Так же позволяет пользователю admin авторизоваться
     * **/
    @RequestMapping(value = "/check",
                    method = RequestMethod.POST)
    @ResponseBody
    public void check(@RequestBody  User user,
                      @CookieValue (value = "username", required = false) String username,
                      HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("username", null);
        try {
            ad.setLDAPConnection();
            if(ad.checkUser(user.getLogin()) || user.getLogin().equals("admin")){
                cookie.setValue(user.getLogin());
                cookie.setMaxAge(MAX_AGE_COOKIE);
                response.addCookie(cookie);
                _log.info("Пользователь " + user.getLogin() + " успешно прошел авторизацию.");
                welcomePage();//Если авторизация прошла успешно - переходим на главную страницу
            }
            else{
                response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED );
                _log.warn("Пользователь с логином " + user.getLogin() + " в базе ActiveDirectory не найден.");
            }
        } catch (NamingException e) {
            _log.error("Ошибка возникла из-за проблем получения данных из ActiveDirectory: " + e);
            e.printStackTrace();
        } catch (NullPointerException e){
            _log.error("Один из параметров не может быть нулевым. Возможео стоит проверить свойства объекта ActiveDirectory. " + e);
        }
    }

    @RequestMapping(value = "/welcome",
                    method = RequestMethod.GET)
    public ModelAndView welcomePage(){
//        List<Cameras> selectSourceInfo = entityOperation.selectSourceNotDeleted();
        ModelAndView modelAndView = new ModelAndView();
//        entityOperation.getAll();
//        modelAndView.addObject("lists", selectSourceInfo);
        modelAndView.setViewName("welcome");
        return modelAndView;
    }

    @RequestMapping(value = "/getNote",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Cameras> getNote(){
        List<Cameras> selectSourceInfo = entityOperation.selectSourceNotDeleted();
        return selectSourceInfo;
    }

    @RequestMapping(value = "/addSource",
            method = RequestMethod.POST)
    @ResponseBody
    public void addSource(@RequestBody  Cameras cameras,
                          @CookieValue (value = "username") String username,
                          HttpServletResponse response) {
        String addSource = entityOperation.addSource(cameras, username);
        operationResponse(addSource, response);
    }

    @RequestMapping(value = "/updateSource",
            method = RequestMethod.POST)
    @ResponseBody
    public void updateSource(@RequestBody  Cameras cameras,
                             @CookieValue (value = "username") String username,
                             HttpServletResponse response) {
        String updateSource = null;
        if (cameras.getOwnBy() == null || cameras.getOwnBy().equals("")) {
            updateSource = entityOperation.updateSource(cameras, username);
        } else {
            updateSource = entityOperation.updateSource(cameras, "");
        }
        operationResponse(updateSource, response);
    }

    @RequestMapping(value = "/deleteSource",
            method = RequestMethod.POST)
    @ResponseBody
    public void deleteSource(@RequestBody  Cameras cameras,
                          @CookieValue (value = "username") String username,
                          HttpServletResponse response) {
        entityOperation.deleteSource(cameras);
    }

    private void operationResponse(String operation, HttpServletResponse response){
        if (operation.equals("success")) response.setStatus(HttpServletResponse.SC_OK); //Код 200 - Устройство успещно обновлено
        else if (operation.equals("failed"))response.setStatus(601); //Код ошибки 601 - Устройство уже добавлено в БД
        else if (operation.equals("error"))response.setStatus(602); //Код ошибки 602 - Произошла одна из известных ошибок NullPointerException или NamingException
        else if (operation.equals("unknown")) response.setStatus(603); //Код ошибки 603 - Неизвестная ошибка, источник не добавлен
    }
}
