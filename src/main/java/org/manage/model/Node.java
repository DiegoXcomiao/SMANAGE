package org.manage.model;

import java.io.Serializable;

/**
 * @author: Diego
 * @date: 2020/8/11 9:42
 * @Des:
 */
public class Node implements Serializable {
    private Integer nodeId;
    private String code;
    private String name;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
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
