package com.ischoolbar.programmer.serviceimpl;

import com.ischoolbar.programmer.dao.MenuDao;
import com.ischoolbar.programmer.entity.Menu;
import com.ischoolbar.programmer.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    public int add(Menu menu){
        return menuDao.add(menu);
    }

    public List<Menu> findList(Map<String,Object> queryMap){
        return menuDao.findList(queryMap);
    }

}
