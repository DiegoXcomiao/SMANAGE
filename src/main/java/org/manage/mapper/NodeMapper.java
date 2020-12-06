package org.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.manage.model.Node;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/6 9:27
 * @Des:
 */
@Mapper
@Component
public interface NodeMapper {
    int addNode(Node node);
    int deleteNode(Node node);
    int updateNode(Node node);
    Node getNodeById(Integer id);
    List<Node> getAllNodes();
    Integer getAllNum();
    List<Node> getNode(Node node);
}
