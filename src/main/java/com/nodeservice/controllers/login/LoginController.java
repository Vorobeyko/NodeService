package com.nodeservice.controllers.login;

import com.nodeservice.AD.ActiveDirectory;
import com.nodeservice.instance.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.nodeservice.controllers.MainController.welcomePage;

/**
 * Created by VorobeyAlex on 06.11.2016.
 */

@Controller
public class LoginController {
    private final Logger _log = LogManager.getLogger(this.getClass());
    private static final int MAX_AGE_COOKIE = 3600*24*30;
    private ActiveDirectory ad = new ActiveDirectory();

    /**
     * Метод для проверки существования пользователя в ActiveDirectory.
     * При удачной авторизации добавляет cookie в браузер с имененм пользователя.
     * Так же позволяет пользователю admin авторизоваться
     * @param user
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/login/check",
            method = RequestMethod.POST)
    @ResponseBody
    public void check(@RequestBody User user,
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

}
