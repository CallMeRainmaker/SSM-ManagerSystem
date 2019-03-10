package com.ischoolbar.programmer.serviceimpl;

import com.ischoolbar.programmer.dao.RoleDao;
import com.ischoolbar.programmer.entity.Role;
import com.ischoolbar.programmer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public int add(Role role){
        return roleDao.add(role);
    }

    @Override
    public int edit(Role role){
        return roleDao.edit(role);
    }

    @Override
    public int delete(Long id){
        return roleDao.delete(id);
    }

    @Override
    public List<Role> findRoleList(Map<String,Object> queryMap){
        return roleDao.findRoleList(queryMap);
    }

    @Override
    public int getTotal(Map<String,Object> queryMap){
        return roleDao.getTotal(queryMap);
    }
}
