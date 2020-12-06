package org.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.manage.common.utils.MyExecutor;
import org.manage.config.PictureConfig;
import org.manage.model.*;
import org.manage.service.MessageService;
import org.manage.service.NodeService;
import org.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author: Diego
 * @date: 2020/8/11 9:52
 * @Des:
 */

@RestController
@CrossOrigin
@Slf4j
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    private ResponseEntity<String> returnValue(BaseOut baseOut) {
        JSONObject json = new JSONObject();
        String s = JSONObject.toJSONString(baseOut);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("sever", "smanage");
        ResponseEntity<String> re = new ResponseEntity<String>(s, headers, HttpStatus.OK);
        return re;
    }

    private Message objectToMessage(Object o) {
        Message message = JSON.parseObject(JSON.toJSONString(o), new TypeReference<Message>() {
        });
        return message;
    }

    @RequestMapping(value = "/sendCode", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> sendCode(@RequestBody BaseIn baseIn) {

        BaseOut baseOut = new BaseOut();

        Message message = objectToMessage(baseIn.getObj());

        log.info(message.getUserCode());
        log.info(message.getTelno());


        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        log.info(verifyCode);
        message.setVerifyCode(verifyCode);

        try {
            if (message.getTelno().equals("")) {
                User user = new User();
                user.setCode(message.getUserCode());
                List<User> res = userService.getUsers(user);
                if (res.size() != 0) {
                    message.setTelno(res.get(0).getTelno());
                }
            }
            if (messageService.sendMessage(message) < 0) {
                baseOut.setCode("-1");
                baseOut.setMsg("发送验证码失败");
                return returnValue(baseOut);
            }

            List<Message> message1 = messageService.getMessage(message);
            if (message1.size() == 0) {
                messageService.addMessage(message);
            } else {
                messageService.updateMessage(message);
            }
            MyExecutor myExecutor = new MyExecutor();
            myExecutor.run1(message);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("发送验证码失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("发送验证码成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/sendCode2", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> sendCode2(@RequestBody BaseIn baseIn) {

        BaseOut baseOut = new BaseOut();

        Message message = objectToMessage(baseIn.getObj());

        log.info(message.getUserCode());

        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        log.info(verifyCode);
        message.setVerifyCode(verifyCode);

        try {
            List<Message> message1 = messageService.getMessage(message);
            if (message1.size() == 0) {
                baseOut.setCode("-1");
                baseOut.setMsg("系统无此用户");
                return returnValue(baseOut);
            } else {
                messageService.updateMessage(message);
            }

            MyExecutor myExecutor = new MyExecutor();
            myExecutor.run2(message);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("发送验证码失败");
            return returnValue(baseOut);
        }

        baseOut.setObj(message);
        baseOut.setCode("0");
        baseOut.setMsg("发送验证码成功");
        return returnValue(baseOut);
    }
}
