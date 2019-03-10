package com.ischoolbar.programmer.service;

import com.ischoolbar.programmer.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoleService {
    public int add(Role role);
    public int edit(Role role);
    public int delete(Long id);
    public List<Role> findRoleList(Map<String,Object> queryMap);
    public int getTotal(Map<String,Object> queryMap);
}
