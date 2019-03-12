package com.ischoolbar.programmer.dao;

import com.ischoolbar.programmer.entity.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityDao {
    public int add(Authority authority);
    public int delete(Long roleId);
    public List<Authority> findListByRoleId(Long roleId);
}
