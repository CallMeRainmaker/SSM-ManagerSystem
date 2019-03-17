package com.ischoolbar.programmer.serviceimpl;

import com.ischoolbar.programmer.dao.UserDao;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserName(String username){
        return userDao.findByUserName(username);
    }

    @Override
    public int add(User user){
        return userDao.add(user);
    }

    @Override
    public int edit(User user){
        return userDao.edit(user);
    }

    @Override
    public int delete(String ids){
        return userDao.delete(ids);
    }

    @Override
    public List<User> findList(Map<String, Object> map){
        return userDao.findList(map);
    }

    @Override
    public int getTotal(Map<String, Object> map){
        return userDao.getTotal(map);
    }
}
