package org.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.manage.config.PictureConfig;
import org.manage.model.*;
import org.manage.service.NodeService;
import org.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: Diego
 * @date: 2020/8/11 9:52
 * @Des:
 */

@RestController
@CrossOrigin
@Slf4j
public class PictureController {
    @Autowired
    NodeService nodeService;

    @Autowired
    PictureConfig pictureConfig;

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

    public static Long getTimestamp(Date date){
        if (null == date) {
            return (long) 0;
        }
        String timestamp = String.valueOf(date.getTime());
        return Long.valueOf(timestamp);
    }

    @RequestMapping(value = "/addPic", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> addPic(@RequestParam Map<String,String> map, @RequestParam("file") MultipartFile[] file) {

        BaseOut baseOut = new BaseOut();
        if(map.size() >0){
            try{
                String name = map.get("name").toString();
                log.info(name);
                log.info(file[0].getOriginalFilename());
                String location = pictureConfig.getLocation();
                log.info(location);
                Date d = new Date();
                Long ltime = getTimestamp(d);
                String path = location.substring(5,location.length()) + "/" + name + ltime + ".jpg";
                log.info(path);
                File f = new File(path);
                if (f.exists() == true) {
                    f.delete();
                }
                file[0].transferTo(f);
                User user = new User();
                user.setCode(name);
                user.setPicpath("/image/" + name + ltime + ".jpg");
                userService.resetPicpath(user);
                baseOut.setObj(user);
                baseOut.setCode("0");
                baseOut.setMsg("新增图片成功");

            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            baseOut.setCode("-1");
            baseOut.setMsg("新增图片失败");
            return returnValue(baseOut);
        }
        return returnValue(baseOut);
    }

    private Code objectToCode(Object o) {
        Code code = JSON.parseObject(JSON.toJSONString(o), new TypeReference<Code>() {});
        return code;
    }

    @RequestMapping(value = "/deletePic", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> deletePic(@RequestBody BaseIn baseIn) {

        BaseOut baseOut = new BaseOut();

        Code code = objectToCode(baseIn.getObj());

        log.info(code.getCode());

        try{
            User user = new User();
            user.setCode(code.getCode());
            List<User> l = userService.getUsers(user);
            if (l.size() > 0) {
                String location = pictureConfig.getLocation();
                log.info(location);
                String name = l.get(0).getPicpath().substring(7);
                String path = location.substring(5, location.length()) + "/" + name;
                log.info(path);
                File f = new File(path);
                if (f.exists() == true) {
                    f.delete();
                }
                user.setPicpath("");
                userService.resetPicpath(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        baseOut.setCode("0");
        baseOut.setMsg("删除图片成功");
        return returnValue(baseOut);
    }
}
