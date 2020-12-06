package org.manage.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/6 9:13
 * @Des:
 */
public class BaseOut implements Serializable {
    List<Object> lobj;
    Object obj;
    Integer totalCount;
    String code;
    String msg;

    public List<Object> getLobj() {
        return lobj;
    }

    public void setLobj(List<Object> lobj) {
        this.lobj = lobj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

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
