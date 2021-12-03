package com.example.bootcrud.services;

import com.example.bootcrud.dao.RoleDao;
import com.example.bootcrud.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;

@Component
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findByname(String rolename) {
        return roleDao.findByname(rolename);
    }

    @Override
    public HashSet<Role> index() {
        return new HashSet<>(roleDao.index());
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role read(Long id) {
        return roleDao.read(id);
    }

    @Override
    public void update(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delete(Long id) {
        roleDao.delete(id);
    }

    public HashSet<Role> getRoleSet(String[] roles) {
        HashSet<Role> resSet = new HashSet<>();
        for( String roleId: roles) {
            resSet.add(roleDao.read(Long.parseLong(roleId)));
        }
        return resSet;
    }

}
