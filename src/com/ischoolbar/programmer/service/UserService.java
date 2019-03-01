package com.ischoolbar.programmer.service;

import com.ischoolbar.programmer.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User findByUserName(String username);
}
