package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public User findByUserName(String username);
}
