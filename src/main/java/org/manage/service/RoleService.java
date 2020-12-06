package org.manage.service;

import org.manage.mapper.RoleMapper;
import org.manage.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/23 17:38
 * @Des:
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;

    public int addRole(Role Role) {
        return roleMapper.addRole(Role);
    }
    public int updateRole(Role Role) {
        return roleMapper.updateRole(Role);
    }
    public int deleteRoleById(Integer id) {
        return roleMapper.deleteRoleById(id);
    }
    public String getMaxCode() {
        return roleMapper.getMaxCode();
    }
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }
    public Integer getAllNum() {
        return roleMapper.getAllNum();
    }
    public List<Role> getRole(List<String> list) {
        return roleMapper.getRole(list);
    }
}
