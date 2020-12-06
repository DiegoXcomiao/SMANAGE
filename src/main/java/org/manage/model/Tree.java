package org.manage.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/8/17 16:55
 * @Des:
 */
public class Tree implements Serializable {
    String title;
    String code;
    List<Tree> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }
}
