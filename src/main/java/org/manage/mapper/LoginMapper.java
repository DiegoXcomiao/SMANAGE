package org.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.manage.model.Login;
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
public interface LoginMapper {
    int addLogin(Login login);
    int updateLogin(Login login);
    List<Login> getLogin(Login Login);
}
