package com.ischoolbar.programmer.controller;

import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.service.UserService;
import com.ischoolbar.programmer.util.CpachaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
系统操作类控制器
 */
@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private UserService userService;

//    @RequestMapping(value = "/index",method = RequestMethod.GET)
//    public String index(){
//        return "index";
//    }

    @RequestMapping(value = "/loginAct",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> loginAct(User user,String cpacha,HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        if(user == null){
            map.put("type","error");
            map.put("msg","请填写用户信息");
            return map;
        }
        if(StringUtil.isEmpty(cpacha)){
            map.put("type","error");
            map.put("msg","请填写验证码");
            return map;
        }
        if(StringUtil.isEmpty(user.getUsername())){
            map.put("type","error");
            map.put("msg","请填写用户名");
            return map;
        }
        if(StringUtil.isEmpty(user.getPassword())){
            map.put("type","error");
            map.put("msg","请填写密码");
            return map;
        }
        Object loginCpacha = request.getSession().getAttribute("loginCpacha");
        if(!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())){
            map.put("type","error");
            map.put("msg","验证码错误");
            return map;
        }
        User findByUserName = userService.findByUserName(user.getUsername());
        if(findByUserName == null){
            map.put("type","error");
            map.put("msg","用户名不存在");
            return map;
        }
        if(!user.getPassword().equals(findByUserName.getPassword())){
            map.put("type","error");
            map.put("msg","密码错误");
            return map;
        }
        request.getSession().setAttribute("user",findByUserName);
        map.put("type","success");
        map.put("msg","登陆成功");
        return map;
    }

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

    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    public ModelAndView welcome(ModelAndView modelAndView){
        modelAndView.setViewName("welcome");
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
