package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuDao {
    public int add(Menu menu);
    public List<Menu> findList(Map<String,Object> queryMap);
    public List<Menu> findTopList();
    public int getTotal(Map<String,Object> queryMap);
}
