package com.ischoolbar.programmer.service;

import com.ischoolbar.programmer.entity.Menu;
import org.springframework.stereotype.Service;

@Service
public interface MenuService {
    public int add(Menu menu);
}
