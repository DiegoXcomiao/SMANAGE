package org.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.manage.model.BaseIn;
import org.manage.model.BaseOut;
import org.manage.model.Role;
import org.manage.model.User;
import org.manage.service.RoleService;
import org.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/6/22 11:20
 * @Des:
 */

@RestController
@CrossOrigin
@Slf4j
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    private ResponseEntity<String> returnValue(BaseOut baseOut) {
        JSONObject json = new JSONObject();
        String s = JSONObject.toJSONString(baseOut);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("sever", "smanage");
        ResponseEntity<String> re = new ResponseEntity<String>(s, headers, HttpStatus.OK);
        return re;
    }

    private Role objectToRole(Object o) {
        Role role = JSON.parseObject(JSON.toJSONString(o), new TypeReference<Role>() {});
        return role;
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> addRole(@RequestBody BaseIn baseIn) {
        Role role = objectToRole(baseIn.getObj());
        log.info(role.getCode());
        log.info(role.getName());

        BaseOut baseOut = new BaseOut();

        try {
            roleService.addRole(role);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("新增权限失败");
            return returnValue(baseOut);
        }
        baseOut.setCode("0");
        baseOut.setMsg("新增权限成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> deleteRole(@RequestBody BaseIn baseIn) {
        Role role = objectToRole(baseIn.getObj());
        log.info(role.getCode());
        log.info(role.getName());

        BaseOut baseOut = new BaseOut();

        try {
            User user = new User();
            user.setRoleCode(role.getCode());
            List<User> l = userService.getUserByRoleCode(user);
            if (l.size() > 0) {
                baseOut.setCode("-1");
                baseOut.setMsg("有用户使用该权限请勿删除");
                return returnValue(baseOut);
            }

            roleService.deleteRoleById(role.getRoleId());
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("删除权限失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("删除权限成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/updateRole", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> updateRole(@RequestBody BaseIn baseIn) {
        Role role = objectToRole(baseIn.getObj());
        log.info(role.getCode());
        log.info(role.getName());

        BaseOut baseOut = new BaseOut();
        try {
            roleService.updateRole(role);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("修改权限失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("修改权限成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/queryRole", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryRole(@RequestBody BaseIn baseIn) {
        int page = baseIn.getPageIndex();
        int num = baseIn.getPageNumber();
        log.info(Integer.toString(page));
        log.info(Integer.toString(num));

        BaseOut baseOut = new BaseOut();

        if (page == 0 && num == 0) {
            try {
                List<Role> res = roleService.getAllRoles();

                List<Object> o = new ArrayList<Object>(res);
                baseOut.setLobj(o);
                baseOut.setCode("0");
                Integer allNum = roleService.getAllNum();
                baseOut.setTotalCount(allNum);
                baseOut.setMsg("查询权限成功");
            } catch (Exception e) {
                e.printStackTrace();
                baseOut.setCode("-1");
                baseOut.setMsg("查询权限失败");
                return returnValue(baseOut);
            }
        } else {
            try {
                PageHelper.startPage(page, num);

                List<Role> res = roleService.getAllRoles();

                PageInfo pageInfo = new PageInfo<Role>(res);

                List<Object> o = pageInfo.getList();
                baseOut.setLobj(o);
                baseOut.setCode("0");
                Integer allNum = roleService.getAllNum();
                baseOut.setTotalCount(allNum);
                baseOut.setMsg("查询权限成功");
            } catch (Exception e) {
                e.printStackTrace();
                baseOut.setCode("-1");
                baseOut.setMsg("查询权限失败");
                return returnValue(baseOut);
            }
        }

        baseOut.setCode("0");
        baseOut.setMsg("查询权限成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/queryCode", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryCode(@RequestBody BaseIn baseIn) {
        int page = baseIn.getPageIndex();
        int num = baseIn.getPageNumber();
        log.info(Integer.toString(page));
        log.info(Integer.toString(num));

        BaseOut baseOut = new BaseOut();

        String maxCode = "";

        try {
            maxCode = roleService.getMaxCode();
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("查询权限编码失败");
            return returnValue(baseOut);
        }
        Role role = new Role();
        if (maxCode == null) {
            role.setCode("000001");
        } else {
            Integer i = Integer.parseInt(maxCode) + 1;
            String s = String.format("%06d", i);
            role.setCode(s);
        }
        Object o = role;
        baseOut.setObj(o);
        baseOut.setCode("0");
        baseOut.setMsg("查询权限编码成功");

        return returnValue(baseOut);
    }
}
