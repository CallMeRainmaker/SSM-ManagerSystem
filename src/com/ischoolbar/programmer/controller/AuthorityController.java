package com.ischoolbar.programmer.controller;

import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.Authority;
import com.ischoolbar.programmer.entity.Menu;
import com.ischoolbar.programmer.service.AuthorityService;
import com.ischoolbar.programmer.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/addAuthority",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(
            @RequestParam(name = "ids",required = true) String ids,
            @RequestParam(name = "roleId",required = true) Long roleId){
        Map<String,String> map = new HashMap<>();
        if(StringUtil.isEmpty(ids)){
            map.put("type","error");
            map.put("msg","请选择权限");
        }
        if(roleId == null){
            map.put("type","error");
            map.put("msg","请选择角色");
        }
        if(ids.contains(",")){
            ids = ids.substring(0,ids.length()-1);
        }
        String[] idArr = ids.split(",");
        if(idArr.length > 0){
            authorityService.delete(roleId);
        }
        for(String id:idArr){
            Authority authority = new Authority();
            authority.setMenuId(Long.valueOf(id));
            authority.setRoleId(roleId);
            authorityService.add(authority);
        }
        map.put("type","success");
        map.put("msg","添加权限完成");
        return map;
    }

    @RequestMapping(value = "/getAllMenu",method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> getAllMenu(){
        Map<String,Object> map = new HashMap<>();
        map.put("offset",0);
        map.put("pageSize",9999);
        return menuService.findList(map);
    }
}
