package com.ischoolbar.programmer.controller;

import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.service.RoleService;
import com.ischoolbar.programmer.service.UserService;
import org.apache.commons.lang.StringUtils;
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

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView modelAndView){
        Map<String,Object> map = new HashMap<>();
        modelAndView.addObject("roleList",roleService.findRoleList(map));
        modelAndView.setViewName("userList");
        return modelAndView;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(Page page,
                                      @RequestParam(name = "username",required = false,defaultValue = "") String username,
                                      @RequestParam(name = "roleId",required = false) Long roleId,
                                      @RequestParam(name = "sex",required = false) Integer sex){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("name",username);
        queryMap.put("roleId",roleId);
        queryMap.put("sex",sex);
        List<User> list = userService.findList(queryMap);
        int total = userService.getTotal(queryMap);
        map.put("rows",list);
        map.put("total",total);
        return map;
    }

    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(User user){
        Map<String, String> ret = new HashMap<String, String>();
        if(user == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的用户信息！");
            return ret;
        }
        if(StringUtils.isEmpty(user.getUsername())){
            ret.put("type", "error");
            ret.put("msg", "请填写用户名！");
            return ret;
        }
        if(StringUtils.isEmpty(user.getPassword())){
            ret.put("type", "error");
            ret.put("msg", "请填写密码！");
            return ret;
        }
        if(user.getRoleId() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属角色！");
            return ret;
        }
        if(isExist(user.getUsername(), 0l)){
            ret.put("type", "error");
            ret.put("msg", "该用户名已经存在，请重新输入！");
            return ret;
        }
        if(userService.add(user) <= 0){
            ret.put("type", "error");
            ret.put("msg", "用户添加失败，请联系管理员！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "角色添加成功！");
        return ret;
    }

    public boolean isExist(String username,Long id){
        User user = userService.findByUserName(username);
        if(user == null) {
            return false;
        }
        if(user.getId().longValue() == id.longValue()){
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(User user){
        Map<String,String> map = new HashMap<>();
        if(user == null){
            map.put("type","error");
            map.put("msg","请选择用户");
            return map;
        }
        if(StringUtil.isEmpty(user.getUsername())){
            map.put("type","error");
            map.put("msg","请填写用户名称");
            return map;
        }
        if(StringUtil.isEmpty(user.getPassword())){
            map.put("type","error");
            map.put("msg","请填写密码");
            return map;
        }
        if(user.getRoleId() == null){
            map.put("type","error");
            map.put("msg","请选择权限");
            return map;
        }
        if(isExist(user.getUsername(),user.getId())){
            map.put("type","error");
            map.put("msg","用户已存在");
            return map;
        }
        if(userService.edit(user)<=0){
            map.put("type","error");
            map.put("msg","编辑失败");
            return map;
        }
        map.put("type","successr");
        map.put("msg","编辑成功");
        return map;
    }

    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delete(String ids) {
        Map<String, String> map = new HashMap<>();
        if (StringUtil.isEmpty(ids)) {
            map.put("type", "error");
            map.put("msg", "请选择删除");
            return map;
        }
        if (ids.contains(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        if (userService.delete(ids) <= 0) {
            map.put("type", "error");
            map.put("msg", "删除失败");
            return map;
        }
        map.put("type", "success");
        map.put("msg", "删除成功");
        return map;
    }
}
