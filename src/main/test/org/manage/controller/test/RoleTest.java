package org.manage.controller.test;

import org.junit.Assert;
import org.junit.Test;
import org.manage.model.BaseIn;
import org.manage.model.BaseOut;
import org.manage.model.RedisRole;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/1 10:36
 * @Des:
 */
public class RoleTest {
    private RestTemplate template = new RestTemplate();

    private RedisRole objectToRole(Object o) {
        LinkedHashMap<String, String> lhm = (LinkedHashMap<String, String>) o;
        RedisRole role = new RedisRole();
        role.setRoleId(lhm.get("roleId"));
        role.setRoleName(lhm.get("roleName"));
        role.setRoleType(lhm.get("roleType"));
        role.setRoleDesc(lhm.get("roleDesc"));
        return role;
    }

    @Test
    public void testAddRole() {
        try {
            String url = "http://localhost:" + 8011 + "/addRole";
            BaseIn baseIn = new BaseIn();
            RedisRole role = new RedisRole();
            role.setRoleName("role2");
            role.setRoleType("2");
            role.setRoleDesc("权限2");
            baseIn.setObj(role);
            Object res = template.postForObject(url, baseIn, BaseOut.class);

            Assert.assertNotNull("创建一条权限数据记录失败", res);

            BaseOut baseOut = (BaseOut) res;
            System.out.println(baseOut.getCode());
            System.out.println(baseOut.getLobj());
            System.out.println(baseOut.getMsg());
            System.out.println(baseOut.getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDeleteRole() {
        try {
            String url = "http://localhost:" + 8011 + "/deleteRole";
            BaseIn baseIn = new BaseIn();
            RedisRole role = new RedisRole();
            role.setRoleId("2");
            baseIn.setObj(role);

            Object res = template.postForObject(url, baseIn, BaseOut.class);

            Assert.assertNotNull("删除一条权限数据记录失败", res);

            BaseOut baseOut = (BaseOut) res;
            System.out.println(baseOut.getCode());
            System.out.println(baseOut.getLobj());
            System.out.println(baseOut.getMsg());
            System.out.println(baseOut.getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testUpdateRole() {
        try {
            String url = "http://localhost:" + 8011 + "/updateRole";
            BaseIn baseIn = new BaseIn();
            RedisRole role = new RedisRole();
            role.setRoleId("1");
            role.setRoleName("role1修改");
            role.setRoleType("1");
            role.setRoleDesc("权限1修改");
            baseIn.setObj(role);

            Object res = template.postForObject(url, baseIn, BaseOut.class);
            Assert.assertNotNull("修改一条权限数据记录失败", res);

            BaseOut baseOut = (BaseOut) res;
            System.out.println(baseOut.getCode());
            System.out.println(baseOut.getLobj());
            System.out.println(baseOut.getMsg());
            System.out.println(baseOut.getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testQueryRole() {
        try {
            String url = "http://localhost:" + 8011 + "/queryRole";
            BaseIn baseIn = new BaseIn();
            RedisRole role = new RedisRole();
            role.setRoleId("1");
            baseIn.setObj(role);
            baseIn.setPageIndex(1);
            baseIn.setPageNumber(10);

            Object res = template.postForObject(url, baseIn, BaseOut.class);
            Assert.assertNotNull("查询权限数据记录失败", res);

            BaseOut baseOut = (BaseOut) res;
            System.out.println(baseOut.getCode());
            System.out.println(baseOut.getLobj());
            System.out.println(baseOut.getMsg());
            System.out.println(baseOut.getTotalCount());
            List<Object> lObject = baseOut.getLobj();
            for (Object o : lObject) {
                RedisRole r = objectToRole(o);
                System.out.println(r.getRoleId());
                System.out.println(r.getRoleName());
                System.out.println(r.getRoleType());
                System.out.println(r.getRoleDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
