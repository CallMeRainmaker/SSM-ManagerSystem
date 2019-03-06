package com.ischoolbar.programmer.controller;

import com.ischoolbar.programmer.page.Page;
import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.Menu;
import com.ischoolbar.programmer.service.MenuService;
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

@RequestMapping("/menu")
@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView List(ModelAndView modelAndView){
        modelAndView.addObject("topList",menuService.findTopList());
        modelAndView.setViewName("/list");
        return modelAndView;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Menu menu){
        Map<String,String> map = new HashMap<>();
        if(menu == null){
            map.put("type","error");
            map.put("msg","请填写菜单信息");
            return map;
        }
        if(StringUtil.isEmpty(menu.getName())){
            map.put("type","error");
            map.put("msg","请填写菜单名称");
            return map;
        }
        if(menu.getParentId() == null){
            menu.setParentId(-1l);
        }
        if(StringUtil.isEmpty(menu.getIcon())){
            map.put("type","error");
            map.put("msg","请填写菜单图标");
            return map;
        }
        if(menuService.add(menu) <= 0){
            map.put("type","error");
            map.put("msg","添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","添加成功");
        return map;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMenuList(Page page,
                                          @RequestParam(name = "name",required = false) String name){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());
        queryMap.put("name",name);
        List<Menu> list = menuService.findList(queryMap);
        map.put("rows",list);
        map.put("total",menuService.getTotal(queryMap));
        return map;
    }
}
