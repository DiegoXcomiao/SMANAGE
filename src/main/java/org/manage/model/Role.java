package org.manage.model;

import java.io.Serializable;

/**
 * @author: Diego
 * @date: 2020/6/22 11:16
 * @Des:
 */
public class Role implements Serializable {
    private Integer roleId;
    private String code;
    private String name;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
