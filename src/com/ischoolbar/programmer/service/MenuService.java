package com.ischoolbar.programmer.service;

import com.ischoolbar.programmer.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MenuService {
    public int add(Menu menu);
    public List<Menu> findList(Map<String,Object> queryMap);
    public List<Menu> findTopList();
    public int getTotal(Map<String,Object> queryMap);
}
