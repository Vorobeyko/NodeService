package com.nodeservice.instance;

import org.springframework.stereotype.Component;

/**
 * Created by avorobey on 31.01.2017.
 */
@Component
public class Feedback {
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
