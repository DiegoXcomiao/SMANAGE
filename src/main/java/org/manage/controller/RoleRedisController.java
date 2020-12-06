package org.manage.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.manage.common.redis.Redis;
import org.manage.model.BaseIn;
import org.manage.model.BaseOut;
import org.manage.model.RedisRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/6/22 11:20
 * @Des:
 */

@RestController
@Slf4j
public class RoleRedisController {
   @Autowired
    Redis<RedisRole> redis;

    private ResponseEntity<String> returnValue(BaseOut baseOut) {
        JSONObject json = new JSONObject();
        String s = JSONObject.toJSONString(baseOut);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("sever", "smanage");
        ResponseEntity<String> re = new ResponseEntity<String>(s, headers, HttpStatus.OK);
        return re;
    }

    private void setIndex(String index, RedisRole role) {
        role.setRoleId(index);
    }

    private RedisRole objectToRole(Object o) {
        LinkedHashMap<String, String> lhm = (LinkedHashMap<String, String>) o;
        RedisRole role = new RedisRole();
        role.setRoleId(lhm.get("roleId"));
        role.setRoleName(lhm.get("roleName"));
        role.setRoleType(lhm.get("roleType"));
        role.setRoleDesc(lhm.get("roleDesc"));
        return role;
    }

    @RequestMapping(value = "/addRoleRedis", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> addRoleRedis(@RequestBody BaseIn baseIn) {
        RedisRole role = objectToRole(baseIn.getObj());
        log.info(role.getRoleId());
        log.info(role.getRoleName());
        log.info(role.getRoleType());
        log.info(role.getRoleDesc());

        BaseOut baseOut = new BaseOut();

        try {
            String index = redis.getIndex(role);
            setIndex(index, role);
            redis.insert(role, index);
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

    @RequestMapping(value = "/deleteRoleRedis", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> deleteRoleRedis(@RequestBody BaseIn baseIn) {
        RedisRole role = objectToRole(baseIn.getObj());
        log.info(role.getRoleId());
        log.info(role.getRoleName());
        log.info(role.getRoleType());
        log.info(role.getRoleDesc());

        BaseOut baseOut = new BaseOut();

        try {
            redis.delete(role, role.getRoleId());
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

    @RequestMapping(value = "/updateRoleRedis", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> updateRoleRedis(@RequestBody BaseIn baseIn) {
        RedisRole role = objectToRole(baseIn.getObj());
        log.info(role.getRoleId());
        log.info(role.getRoleName());
        log.info(role.getRoleType());
        log.info(role.getRoleDesc());

        BaseOut baseOut = new BaseOut();
        try {
            redis.update(role, role.getRoleId());
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

    @RequestMapping(value = "/queryRoleRedis", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryRoleRedis(@RequestBody BaseIn baseIn) {
        log.info(Integer.toString(baseIn.getPageIndex()));
        log.info(Integer.toString(baseIn.getPageNumber()));

        BaseOut baseOut = new BaseOut();

        try {
            RedisRole role = new RedisRole();
            List<Object> res = redis.query(role, baseIn.getPageIndex(), baseIn.getPageNumber());
            baseOut.setLobj(res);
            baseOut.setCode("0");
            baseOut.setTotalCount(res.size());
            baseOut.setMsg("查询权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("查询权限失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("查询权限成功");
        return returnValue(baseOut);
    }
}
