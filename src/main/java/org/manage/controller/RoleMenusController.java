package org.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.manage.model.*;
import org.manage.service.RoleMenusService;
import org.manage.service.RoleService;
import org.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
public class RoleMenusController {
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

    private RoleMenus objectToRoleMenus(Object o) {
        RoleMenus roleMenus = JSON.parseObject(JSON.toJSONString(o), new TypeReference<RoleMenus>() {});
        return roleMenus;
    }

    @RequestMapping(value = "/updateRoleMenus", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> updateRoleMenus(@RequestBody BaseIn baseIn) {
        RoleMenus roleMenus = objectToRoleMenus(baseIn.getObj());
        log.info(roleMenus.getMenusId());
        log.info(roleMenus.getRoleCode());

        BaseOut baseOut = new BaseOut();

        try {
            String s = roleMenus.getMenusId();
            String[] ss = s.split("\\|");
            for (String res : ss) {
                log.info(res);
                Integer i = Integer.parseInt(res);
                String s1 = "";
                if (i % 100 != 0) {
                    s1 = res.substring(0, 2);
                    s1 += '0';
                    if (s.indexOf(s1) < 0) {
                        s += s1;
                        s += "|";
                    }
                }
            }
            ss = s.split("\\|");
            Arrays.sort(ss);
            String sRes = "";
            for (String res : ss) {
                sRes += res;
                sRes += "|";
            }

            roleMenus.setMenusId(sRes);
            log.info(roleMenus.getMenusId());

            List<RoleMenus> l = roleMenusService.getRoleMenus(roleMenus);
            if (l.size() > 0) {
                roleMenusService.updateRoleMenus(roleMenus);
            } else {
                roleMenusService.addRoleMenus(roleMenus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("修改菜单权限失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("修改菜单权限成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/queryRoleMenus", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryRoleMenus(@RequestBody BaseIn baseIn) {
        RoleMenus roleMenus = objectToRoleMenus(baseIn.getObj());
        log.info(roleMenus.getMenusId());
        log.info(roleMenus.getRoleCode());

        BaseOut baseOut = new BaseOut();

        List<RoleMenus> l = new ArrayList<RoleMenus>();
        try {
            l = roleMenusService.getRoleMenus(roleMenus);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("查询菜单权限失败");
            return returnValue(baseOut);
        }

        Object o = l;
        baseOut.setObj(o);
        baseOut.setCode("0");
        baseOut.setMsg("查询菜单权限成功");

        return returnValue(baseOut);
    }

    @RequestMapping(value = "/queryRoleMenus2", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryRoleMenus2(@RequestBody BaseIn baseIn) {
        RoleMenus roleMenus = objectToRoleMenus(baseIn.getObj());
        log.info(roleMenus.getRoleCode());

        BaseOut baseOut = new BaseOut();

        List<RoleMenus> l = new ArrayList<RoleMenus>();
        try {
            String sroles = roleMenus.getRoleCode();
            sroles = sroles.replace("\"", "");
            sroles = sroles.replace("[", "");
            sroles = sroles.replace("]", "");
            List<String> rl = Arrays.asList(sroles.split("\\,"));
            l = roleMenusService.getRoleMenus2(rl);
            List<Object> o = new ArrayList<Object>();
            o.add(l);
            baseOut.setLobj(o);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("查询菜单权限失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("查询菜单权限成功");

        return returnValue(baseOut);
    }
}
