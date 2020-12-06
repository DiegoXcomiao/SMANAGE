package org.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.manage.model.Role;
import org.manage.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/6 9:27
 * @Des:
 */
@Mapper
@Component
public interface UserMapper {
    int addUser(User Role);
    int deleteUser(User user);
    int updateUser(User user);
    int updatePassword(User user);
    String getMaxCode();
    List<User> getUsers(User user);
    Integer getNum(User user);
    List<User> getUserByNodeCode(User user);
    List<User> getUserByRoleCode(User user);
    int resetPasswd(User user);
    int resetPicpath(User user);
}
