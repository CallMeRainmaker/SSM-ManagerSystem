package com.ischoolbar.programmer.controller;

import com.ischoolbar.programmer.util.CpachaUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/get_cpacha",method = RequestMethod.GET)
    public void generateCpacha(
            @RequestParam(name = "vl",required = false,defaultValue = "4") Integer vcodeLen,
            @RequestParam(name = "wd",required = false,defaultValue = "100") Integer width,
            @RequestParam(name = "ht",required = false,defaultValue = "30") Integer height,
            @RequestParam(name = "type",required = true,defaultValue = "loginCpacha") String cpachaType,
            HttpServletRequest request,
            HttpServletResponse response){
        CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen,width,height);
        String generatorVode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute(cpachaType,generatorVode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVode,true);
        try {
            ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
