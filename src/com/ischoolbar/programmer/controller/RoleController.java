package com.ischoolbar.programmer.controller;

import com.github.pagehelper.util.StringUtil;
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
        map.put("total",total);
        return map;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Role role){
        Map<String,String> map = new HashMap<>();
        if(role == null){
            map.put("type","error");
            map.put("msg","请填写正确的角色信息");
            return map;
        }
        if(StringUtil.isEmpty(role.getName())){
            map.put("type","error");
            map.put("msg","请填写角色名称");
            return map;
        }
        if(roleService.add(role) <= 0){
            map.put("type","error");
            map.put("msg","角色添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Role role){
        Map<String,String> map = new HashMap<>();
        if(role == null){
            map.put("type","error");
            map.put("msg","角色为空，请填写正确角色信息");
            return map;
        }
        if(StringUtil.isEmpty(role.getName())){
            map.put("type","error");
            map.put("msg","请填写角色名称");
            return map;
        }
        if(roleService.edit(role)<=0){
            map.put("type","error");
            map.put("msg","角色编辑失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","角色编辑成功");
        return map;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(Long id){
        Map<String,String> map = new HashMap<>();
        if(id == null){
            map.put("type","error");
            map.put("msg","请选择要删除的id");
            return map;
        }
        try{
            if(roleService.delete(id)<=0){
                map.put("type","error");
                map.put("msg","角色删除失败");
                return map;
            }
        }catch (Exception e){
            map.put("type","error");
            map.put("msg","该角色存在权限，不能删除");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }


}
