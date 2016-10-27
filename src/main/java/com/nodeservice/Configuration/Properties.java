package com.nodeservice.Configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import java.io.FileNotFoundException;

/**
 * Created by avorobey on 21.09.2016.
 * @author Vorobey Alexandr
 */
@Component
@PropertySource("resources/conf.properties")
public class Properties {
    private final Logger _log = LogManager.getLogger(this.getClass());

    public static Environment env;

    @Autowired
    public Properties(Environment _env) {
        try {
            env = _env;
            _log.info("Конфигурационный файл resources/conf.properties найден. Параметр env проинициализирован.");
        }catch (NullPointerException e){
            _log.error("Файл не найден");
        }
    }
}
