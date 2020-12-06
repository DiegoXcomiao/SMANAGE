package org.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.manage.model.*;
import org.manage.service.LoginService;
import org.manage.service.RoleMenusService;
import org.manage.service.RoleService;
import org.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/6/22 11:20
 * @Des:
 */

@RestController
@CrossOrigin
@Slf4j
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    RoleMenusService roleMenusService;

    private ResponseEntity<String> returnValue(BaseOut baseOut) {
        JSONObject json = new JSONObject();
        String s = JSONObject.toJSONString(baseOut);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("sever", "smanage");
        ResponseEntity<String> re = new ResponseEntity<String>(s, headers, HttpStatus.OK);
        return re;
    }

    private Login objectToLogin(Object o) {
        Login login = JSON.parseObject(JSON.toJSONString(o), new TypeReference<Login>() {});
        return login;
    }

    @RequestMapping(value = "/queryLogin", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryLogin(@RequestBody BaseIn baseIn) {
        Login login = objectToLogin(baseIn.getObj());

        BaseOut baseOut = new BaseOut();
        List<Login> l = new ArrayList<Login>();
        try {
            User user = new User();
            user.setCode(login.getUserCode());
            List<User> res = userService.getUsers(user);
            if (res.size() > 0) {
                String sroles = res.get(0).getRoleCode();

                List<RoleMenus> ll = new ArrayList<RoleMenus>();
                sroles = sroles.replace("\"", "");
                sroles = sroles.replace("[", "");
                sroles = sroles.replace("]", "");
                List<String> rl = Arrays.asList(sroles.split("\\,"));
                ll = roleMenusService.getRoleMenus2(rl);

                Integer flag = 0;
                for (RoleMenus roles : ll) {
                    if (roles.getMenusId().contains(login.getMenuId())) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    log.info("登录失败");
                    baseOut.setCode("-1");
                    baseOut.setMsg("查询登录失败");
                    return returnValue(baseOut);
                }
            }
            l = loginService.getLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("查询登录失败");
            return returnValue(baseOut);
        }
        Object o = new Object();
        if (l.size() > 0) {
            o = l.get(0);
        }
        baseOut.setObj(o);
        baseOut.setCode("0");
        baseOut.setMsg("查询登录成功");

        return returnValue(baseOut);
    }
}
