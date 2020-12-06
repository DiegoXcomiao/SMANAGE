package org.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.manage.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/6 9:27
 * @Des:
 */
@Mapper
@Component
public interface RoleMapper {
    int addRole(Role Role);
    int deleteRoleById(Integer id);
    int updateRole(Role Role);
    String getMaxCode();
    List<Role> getAllRoles();
    Integer getAllNum();
    List<Role> getRole(List<String> list);
}
