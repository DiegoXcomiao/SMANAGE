package org.manage.model;

import java.io.Serializable;

/**
 * @author: Diego
 * @date: 2020/6/22 11:16
 * @Des:
 */
public class Login implements Serializable {
    private Integer LoginId;
    private String userCode;
    private String state;
    private String menuId;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Integer getLoginId() {
        return LoginId;
    }

    public void setLoginId(Integer loginId) {
        LoginId = loginId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
