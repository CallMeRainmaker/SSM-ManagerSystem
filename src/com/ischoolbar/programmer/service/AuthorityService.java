package com.ischoolbar.programmer.service;

import com.ischoolbar.programmer.entity.Authority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorityService {
    public int add(Authority authority);
    public int delete(Long roleId);
    public List<Authority> findListByRoleId(Long roleId);
}
