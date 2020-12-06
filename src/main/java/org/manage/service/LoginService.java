package org.manage.service;

import org.manage.mapper.LoginMapper;
import org.manage.mapper.RoleMapper;
import org.manage.model.Login;
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
public class LoginService {
    @Autowired
    LoginMapper loginMapper;

    public int addLogin(Login login) {
        return loginMapper.addLogin(login);
    }
    public int updateLogin(Login login) {
        return loginMapper.updateLogin(login);
    }
    public List<Login> getLogin(Login login) {
        return loginMapper.getLogin(login);
    }
}
