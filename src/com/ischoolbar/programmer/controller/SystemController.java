package com.ischoolbar.programmer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*
系统操作类控制器
 */
@Controller
@RequestMapping("/system")
public class SystemController {

//    @RequestMapping(value = "/index",method = RequestMethod.GET)
//    public String index(){
//        return "index";
//    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        modelAndView.addObject("name","胡旭东");
        return modelAndView;
    }
}
