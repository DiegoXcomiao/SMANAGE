package org.manage.service;

import org.manage.mapper.UserMapper;
import org.manage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/23 17:38
 * @Des:
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public int addUser(User user) {
        return userMapper.addUser(user);
    }
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int updatePassword(User user) {
        return userMapper.updatePassword(user);
    }
    public int deleteUser(User user) {
        return userMapper.deleteUser(user);
    }
    public String getMaxCode() {
        return userMapper.getMaxCode();
    }
    public List<User> getUsers(User user) {
        return userMapper.getUsers(user);
    }
    public Integer getNum(User user) {
        return userMapper.getNum(user);
    }
    public List<User> getUserByNodeCode(User user) {
        return userMapper.getUserByNodeCode(user);
    }
    public List<User> getUserByRoleCode(User user) {
        return userMapper.getUserByRoleCode(user);
    }
    public int resetPasswd(User user) {
        return userMapper.resetPasswd(user);
    }
    public int resetPicpath(User user) {
        return userMapper.resetPicpath(user);
    }
}
