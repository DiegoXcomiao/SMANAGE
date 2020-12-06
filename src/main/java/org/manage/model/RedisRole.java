package org.manage.model;

import java.io.Serializable;

/**
 * @author: Diego
 * @date: 2020/6/22 11:16
 * @Des:
 */
public class RedisRole implements Serializable {
    private String roleId;
    private String roleName;
    private String roleType;
    private String roleDesc;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
