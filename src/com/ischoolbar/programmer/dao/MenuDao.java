package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.entity.Menu;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao {
    public int add(Menu menu);
}
