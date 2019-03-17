package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
    public User findByUserName(String username);
    public int add(User user);
    public int edit(User user);
    public int delete(String ids);
    public List<User> findList(Map<String, Object> map);
    public int getTotal(Map<String, Object> map);
}
