package com.nodeservice.controllers;

/**
 * Created by avorobey on 12.09.2016.
 * Класс является стартовым классом для всего веб-сервиса
 * В данном классе реализован контролер, который прослушивает несколько адресов
 * Каждый метод помеченный аннтоацией @Resources является слушателем того адреса, который прописан в value
 **/

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.DBOperation.*;
import com.nodeservice.instance.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@EnableScheduling
public class MainController {
    private final Logger _log = LogManager.getLogger(this.getClass());

    private ActiveDirectory ad = new ActiveDirectory();

    @Autowired
    IVerifyDate db = new VerifyDate();

    /**
     * Планировщик, который каждые 5 сек. запускает метод для
     * сверки текущей даты с забронированной датой источника
     */
    @Scheduled(fixedRate = 5000)
    public void checkChangeDueData() {
        db.verify();
    }

    /**
     * Метод прослушивающий корневой адрес
     * @param username
     * @return redirect:welcome - если пользователь авторизован
     *         redirect:login - если пользователь не авторизован
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String main(@CookieValue (value = "username", required = false) String username){
        if (username != null)
            return "redirect:welcome";
        else
            return "redirect:login";
    }

    /**
     *  Метод прослушивающий /login - страницу авторизации
     * @return /login
     */
    @RequestMapping(value = "/login",
            method = RequestMethod.GET)
    public ModelAndView login(@CookieValue(value = "username", required = false) String username){
        ModelAndView modelAndView = new ModelAndView();
        if (username != null){
            modelAndView.setViewName("redirect:welcome");
            return modelAndView;
        }else {
            modelAndView.addObject("user",new User());
            modelAndView.setViewName("login");
            return modelAndView;
        }
    }

    /**
     * Метод прослушивающий /welcome - главная страницу
     * @return /welcome
     */
    @RequestMapping(value = "/welcome",
            method = RequestMethod.GET)
    public ModelAndView welcomePage(@CookieValue(value = "username", required = false) String username) throws NamingException {
        ModelAndView modelAndView = new ModelAndView();
        if (username != null){
            Map<String,String> currentUser = new HashMap<>();
            if (username.equals("admin")) currentUser.put("currentUser","Администратор");
            else currentUser.put("currentUser",ad.getNameUser(username));
            modelAndView.addObject("user",currentUser);
            modelAndView.setViewName("welcome");
            return modelAndView;
        }else {
            modelAndView.setViewName("redirect:login");
            return modelAndView;
        }
    }
}
