package org.manage.service;

import org.manage.mapper.RoleMenusMapper;
import org.manage.model.RedisRole;
import org.manage.model.Role;
import org.manage.model.RoleMenus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/23 17:38
 * @Des:
 */
@Service
public class RoleMenusService {
    @Autowired
    RoleMenusMapper roleMenusMapper;

    public int addRoleMenus(RoleMenus roleMenus) {
        return roleMenusMapper.addRoleMenus(roleMenus);
    }
    public int updateRoleMenus(RoleMenus roleMenus) {
        return roleMenusMapper.updateRoleMenus(roleMenus);
    }
    public List<RoleMenus> getRoleMenus(RoleMenus roleMenus) {
        return roleMenusMapper.getRoleMenus(roleMenus);
    }
    public List<RoleMenus> getRoleMenus2(List<String> roleCode) {
        return roleMenusMapper.getRoleMenus2(roleCode);
    }
}
