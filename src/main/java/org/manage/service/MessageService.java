package org.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import org.manage.mapper.MessageMapper;
import org.manage.mapper.UserMapper;
import org.manage.model.Message;
import org.manage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Diego
 * @date: 2020/7/23 17:38
 * @Des:
 */
@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    //短信平台相关参数
    private String apiUrl = "https://sms_developer.zhenzikj.com";
    private String appId = "106856";
    private String appSecret = "6cde510e-903b-439c-9437-d340eb490ef6";

    public int addMessage(Message message) {
        return messageMapper.addMessage(message);
    }
    public int updateMessage(Message message) {
        return messageMapper.updateMessage(message);
    }
    public int deleteMessage(Message message) {
        return messageMapper.deleteMessage(message);
    }
    public List<Message> getMessage(Message message) { return messageMapper.getMessage(message); }
    public int sendMessage(Message message) throws Exception {
        //发送短信
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("number", message.getTelno());
        params.put("templateId", "1835");
        String[] templateParams = new String[2];
        templateParams[0] = message.getVerifyCode();
        templateParams[1] = "5分钟";
        params.put("templateParams", templateParams);

        String result = client.send(params);

        System.out.println(result);

        JSONObject json = JSONObject.parseObject(result);
        if(json.getIntValue("code") != 0){//发送短信失败
            return -1;
        }
        return 0;
    }
}
