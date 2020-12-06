package org.manage.model;

import java.io.Serializable;

/**
 * @author: Diego
 * @date: 2020/7/6 9:13
 * @Des:
 */
public class PicOut implements Serializable {
    String code;
    String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
