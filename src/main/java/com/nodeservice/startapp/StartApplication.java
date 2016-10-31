package com.nodeservice.startapp;

/**
 * Created by avorobey on 12.09.2016.
 * Класс является стартовым классом для всего веб-сервиса
 * В данном классе реализован контролер, который прослушивает несколько адресов
 * Каждый метод помеченный аннтоацией @Resources является слушателем того адреса, который прописан в value
 **/

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.DBOperation.VerifyDate;
import com.nodeservice.DBOperation.DataBaseProvider;
import com.nodeservice.DBOperation.IDataBaseProvider;
import com.nodeservice.DBOperation.IVerifyDate;
import com.nodeservice.instance.Cameras;
import com.nodeservice.instance.History;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableScheduling
public class StartApplication {
    private final Logger _log = LogManager.getLogger(this.getClass());
    private static final int MAX_AGE_COOKIE = 3600*24*30;
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

    @Autowired
    IDataBaseProvider dataBaseProvider = new DataBaseProvider();

    /**
     * Метод прослушивающий корневой адрес
     * @param username
     * @return redirect:welcome - если пользователь авторизован
     *         redirect:login - если пользователь не авторизован
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String main(@CookieValue (value = "username", required = false) String username){
        if (username != null){
            return "redirect:welcome";
        }else {
            return "redirect:login";
        }
    }

    /**
     *  Метод прослушивающий /login - страницу авторизации
     * @return /login
     */
    @RequestMapping(value = "/login",
                    method = RequestMethod.GET)
    public ModelAndView login(@CookieValue (value = "username", required = false) String username){
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
     * Метод для проверки существования пользователя в ActiveDirectory.
     * При удачной авторизации добавляет cookie в браузер с имененм пользователя.
     * Так же позволяет пользователю admin авторизоваться
     * @param user
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/check",
                    method = RequestMethod.POST)
    @ResponseBody
    public void check(@RequestBody  User user,
                      HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("username", null);
        try {
            if(user.getLogin().equals("admin") || ad.checkUser(user.getLogin())){
                cookie.setValue(user.getLogin());
                cookie.setMaxAge(MAX_AGE_COOKIE);
                response.addCookie(cookie);
                _log.info("Пользователь " + user.getLogin() + " успешно прошел авторизацию.");
                welcomePage(user.getLogin());//Если авторизация прошла успешно - переходим на главную страницу
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

    /**
     * Метод прослушивающий /welcome - главная страницу
     * @return /welcome
     */
    @RequestMapping(value = "/welcome",
                    method = RequestMethod.GET)
    public ModelAndView welcomePage(@CookieValue (value = "username", required = false) String username) throws NamingException {
        ModelAndView modelAndView = new ModelAndView();
        if (username != null){
            Map<String,String> currentUser = new HashMap<String, String>();
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

    /**
     * Метод прослушивающий /getNote
     * @return возвращает список всех камер
     */
    @RequestMapping(value = "/getNote",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Cameras> getNote(){
        List<Cameras> selectSourceInfo = dataBaseProvider.selectSourceNotDeleted();
        return selectSourceInfo;
    }

    /**
     * Метод прослушивающий /getNote
     * @param sourceIp
     * @return возвращает историю выбранного источника
     */
    @RequestMapping(value = "/getHistory",
            method = RequestMethod.POST)
    @ResponseBody
    public List<History> getHistory(@RequestBody String sourceIp){
        List<History> selectSourceInfo = dataBaseProvider.getHistory(sourceIp);
        return selectSourceInfo;
    }

    /**
     * Метод для добавления источника
     * @param cameras
     * @param username
     * @param response
     */
    @RequestMapping(value = "/addSource",
            method = RequestMethod.POST)
    @ResponseBody
    public void addSource(@RequestBody  Cameras cameras,
                          @CookieValue (value = "username") String username,
                          HttpServletResponse response) {
        String addSource = dataBaseProvider.addSource(cameras, username);
        operationResponse(addSource, response);
    }

    /**
     * Метод для обновления источника
     * @param cameras
     * @param username
     * @param response
     */
    @RequestMapping(value = "/updateSource",
            method = RequestMethod.POST)
    @ResponseBody
    public void updateSource(@RequestBody  Cameras cameras,
                             @CookieValue (value = "username") String username,
                             HttpServletResponse response) {
        String updateSource = null;
        if (cameras.getOwnBy() == null || cameras.getOwnBy().equals("")) {
            updateSource = dataBaseProvider.updateSource(cameras, username);
        } else {
            updateSource = dataBaseProvider.updateSource(cameras, "");
        }
        operationResponse(updateSource, response);
    }

    /**
     * Метод для удаления источника
     * @param cameras
     */
    @RequestMapping(value = "/deleteSource",
            method = RequestMethod.POST)
    @ResponseBody
    public void deleteSource(@RequestBody  Cameras cameras) {
        dataBaseProvider.deleteSource(cameras);
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
