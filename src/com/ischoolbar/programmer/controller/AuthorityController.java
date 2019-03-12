package com.ischoolbar.programmer.controller;

import com.ischoolbar.programmer.entity.Authority;
import com.ischoolbar.programmer.entity.Menu;
import com.ischoolbar.programmer.service.AuthorityService;
import com.ischoolbar.programmer.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private MenuService menuService;

//    @RequestMapping(value = "/add",method = RequestMethod.POST)
//    public Map<String,Object> add(Authority authority){
//        Map<String,Object> map = new HashMap<>();
//        if(authority == null){
//            map.put("type","error");
//            map.put("msg","")
//        }
//    }

    @RequestMapping(value = "getAllMenu",method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> getAllMenu(){
        Map<String,Object> map = new HashMap<>();
        map.put("offset",0);
        map.put("psgeSize",9999);
        return menuService.findList(map);
    }
}
