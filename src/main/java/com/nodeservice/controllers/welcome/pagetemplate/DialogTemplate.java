package com.nodeservice.controllers.welcome.pagetemplate;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by VorobeyAlex on 18.12.2016.
 */
@Controller
public class DialogTemplate
{
    private final Logger _log = LogManager.getLogger(this.getClass());

    @RequestMapping(value = "/pagetemplate/add-sources-dialog",
            method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView addSourcesDialog(){
        return new ModelAndView("template\\add-sources-dialog");
    }

    @RequestMapping(value = "/pagetemplate/change-sources-dialog",
            method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView changeSourcesDialog(){
        return new ModelAndView("template\\change-sources-dialog");

    }

    @RequestMapping(value = "/pagetemplate/feedback-dialog",
            method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView feedbackDialog(){
        return new ModelAndView("template\\feedback-dialog");

    }
}
