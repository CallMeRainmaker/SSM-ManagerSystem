package com.ischoolbar.programmer.controller;

import com.ischoolbar.programmer.entity.Role;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        modelAndView.setViewName("/roleList");
        return modelAndView;
    }

    @RequestMapping(value = "/getlist",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(Page page,
                                      @RequestParam(name = "name",required = false,defaultValue = "")String name){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("name",name);
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        List<Role> list = roleService.findRoleList(queryMap);
        int total = roleService.getTotal(queryMap);
        map.put("rows",list);
        map.put("totao",total);
        return map;
    }

}
