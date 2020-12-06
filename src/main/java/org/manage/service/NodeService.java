package org.manage.service;

import org.manage.mapper.NodeMapper;
import org.manage.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/23 17:38
 * @Des:
 */
@Service
public class NodeService {
    @Autowired
    NodeMapper NodeMapper;

    public int addNode(Node Node) {
        return NodeMapper.addNode(Node);
    }
    public int updateNode(Node Node) {
        return NodeMapper.updateNode(Node);
    }
    public int deleteNode(Node node) {
        return NodeMapper.deleteNode(node);
    }
    public Node getNodeById(Integer id) {
        return NodeMapper.getNodeById(id);
    }
    public List<Node> getAllNodes() {
        return NodeMapper.getAllNodes();
    }
    public Integer getAllNum() {
        return NodeMapper.getAllNum();
    }
    public List<Node> getNode(Node node) {
        return NodeMapper.getNode(node);
    }
}
