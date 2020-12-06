package org.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.manage.model.RoleMenus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/6 9:27
 * @Des:
 */
@Mapper
@Component
public interface RoleMenusMapper {
    int addRoleMenus(RoleMenus roleMenus);
    int updateRoleMenus(RoleMenus roleMenus);
    List<RoleMenus> getRoleMenus(RoleMenus roleMenus);
    List<RoleMenus> getRoleMenus2(List<String> roleCode);
}
