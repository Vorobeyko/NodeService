package com.nodeservice.instance;

import org.springframework.stereotype.Component;

/**
 * Created by avorobey on 12.09.2016.
 */
@Component
public class User {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
