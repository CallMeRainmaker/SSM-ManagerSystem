package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao {
    public int add(Role role);
    public int edit(Role role);
    public int delete(Long id);
    public List<Role> findRoleList(Map<String,Object> queryMap);
    public int getTotal(Map<String,Object> queryMap);
}
