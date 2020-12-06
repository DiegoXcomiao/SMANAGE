package org.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.manage.model.Message;
import org.manage.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Diego
 * @date: 2020/7/6 9:27
 * @Des:
 */
@Mapper
@Component
public interface MessageMapper {
    int addMessage(Message message);
    int deleteMessage(Message message);
    int updateMessage(Message message);
    List<Message> getMessage(Message message);
}
