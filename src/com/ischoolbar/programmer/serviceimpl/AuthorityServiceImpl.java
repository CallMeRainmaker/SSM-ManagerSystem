package com.ischoolbar.programmer.serviceimpl;

import com.ischoolbar.programmer.dao.AuthorityDao;
import com.ischoolbar.programmer.entity.Authority;
import com.ischoolbar.programmer.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public int add(Authority authority){
        return authorityDao.add(authority);
    }

    @Override
    public int delete(Long roleId){
        return authorityDao.delete(roleId);
    }

    @Override
    public List<Authority> findListByRoleId(Long roleId){
        return authorityDao.findListByRoleId(roleId);
    }

}
