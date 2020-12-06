package org.manage.model;

import java.io.Serializable;

public class RoleMenus implements Serializable {
    private Integer roleMenusId;
    private String roleCode;
    private String menusId;

    public Integer getRoleMenusId() {
        return roleMenusId;
    }

    public void setRoleMenusId(Integer roleMenusId) {
        this.roleMenusId = roleMenusId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getMenusId() {
        return menusId;
    }

    public void setMenusId(String menusId) {
        this.menusId = menusId;
    }
}
