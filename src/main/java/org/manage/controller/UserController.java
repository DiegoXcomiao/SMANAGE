package org.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.manage.config.PictureConfig;
import org.manage.model.*;
import org.manage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/8/11 9:52
 * @Des:
 */

@RestController
@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    NodeService nodeService;

    @Autowired
    RoleService roleService;

    @Autowired
    PictureConfig pictureConfig;

    @Autowired
    LoginService loginService;

    private ResponseEntity<String> returnValue(BaseOut baseOut) {
        JSONObject json = new JSONObject();
        String s = JSONObject.toJSONString(baseOut);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("sever", "smanage");
        ResponseEntity<String> re = new ResponseEntity<String>(s, headers, HttpStatus.OK);
        return re;
    }

    @RequestMapping(value = "/queryUserCode", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryCode(@RequestBody BaseIn baseIn) {

        BaseOut baseOut = new BaseOut();

        String maxCode = "";

        try {
            maxCode = userService.getMaxCode();
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("查询用户编码失败");
            return returnValue(baseOut);
        }
        User user = new User();
        if (maxCode == null) {
            user.setCode("000001");
        } else {
            Integer i = Integer.parseInt(maxCode) + 1;
            String s = String.format("%06d", i);
            user.setCode(s);
        }
        Object o = user;
        baseOut.setObj(o);
        baseOut.setCode("0");
        baseOut.setMsg("查询用户编码成功");

        return returnValue(baseOut);
    }

    @RequestMapping(value = "/queryUser", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryUser(@RequestBody BaseIn baseIn) {
        int page = baseIn.getPageIndex();
        int num = baseIn.getPageNumber();
        log.info(Integer.toString(page));
        log.info(Integer.toString(num));
        BaseOut baseOut = new BaseOut();

        User user = objectToUser(baseIn.getObj());

        if (page == 0 && num == 0) {
            try {
                log.info(user.getCode());
                log.info(user.getEnabled());
                log.info(user.getNodeCode());
                log.info(user.getRoleCode());
                log.info(user.getName());

                if (user.getRoleCode().equals("[]")) {
                    user.setRoleCode("");
                }

                List<User> res = userService.getUsers(user);

                List<Object> o = new ArrayList<Object>();
                o.add(res);

                baseOut.setLobj(o);
                baseOut.setCode("0");
                Integer allNum = userService.getNum(user);
                baseOut.setTotalCount(allNum);
                baseOut.setMsg("查询用户成功");
            } catch (Exception e) {
                e.printStackTrace();
                baseOut.setCode("-1");
                baseOut.setMsg("查询用户失败");
                return returnValue(baseOut);
            }
        } else {
            try {
                PageHelper.startPage(page, num);

                log.info(user.getCode());
                log.info(user.getEnabled());
                log.info(user.getNodeCode());
                log.info(user.getRoleCode());
                log.info(user.getName());

                if (user.getRoleCode().equals("[]")) {
                    user.setRoleCode("");
                }
                List<User> res = userService.getUsers(user);
                PageInfo pageInfo = new PageInfo<User>(res);

                List<Object> o = pageInfo.getList();
                baseOut.setLobj(o);
                baseOut.setCode("0");
                Integer allNum = userService.getNum(user);
                baseOut.setTotalCount(allNum);
                baseOut.setMsg("查询用户成功");
            } catch (Exception e) {
                e.printStackTrace();
                baseOut.setCode("-1");
                baseOut.setMsg("查询用户失败");
                return returnValue(baseOut);
            }
        }

        baseOut.setCode("0");
        baseOut.setMsg("查询用户成功");
        return returnValue(baseOut);
    }

    private User objectToUser(Object o) {
        User user = JSON.parseObject(JSON.toJSONString(o), new TypeReference<User>() {
        });
        return user;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> addUser(@RequestBody BaseIn baseIn) {
        User user = objectToUser(baseIn.getObj());
        log.info(user.getCode());
        log.info(user.getName());
        log.info(user.getRoleCode());
        log.info(user.getRoleName());
        log.info(user.getNodeCode());
        log.info(user.getNodeName());
        log.info(user.getName());
        log.info(user.getEnabled());
        log.info(user.getPassword());
        log.info(user.getPicpath());
        log.info(user.getTelno());
        log.info(user.getVerifyCode());

        BaseOut baseOut = new BaseOut();
        try {
            String password = DigestUtils.md5DigestAsHex("123456".getBytes());
            user.setPassword(password);
            Message message = new Message();
            message.setUserCode(user.getCode());

            List<Message> message1 = messageService.getMessage(message);
            if (message1.size() == 0) {
                baseOut.setCode("-1");
                baseOut.setMsg("验证码发送错误，请重新发送");
                return returnValue(baseOut);
            } else {
                if (message1.get(0).getVerifyCode().equals(user.getVerifyCode()) == false) {
                    baseOut.setCode("-1");
                    baseOut.setMsg("验证码错误，请重新输入");
                    return returnValue(baseOut);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("输入验证码错误");
            return returnValue(baseOut);
        }

        try {
            String path = user.getPicpath();
            if (path != null && !path.equals("")) {
                path = path.substring(path.indexOf("/image"));
            } else {
                path = "";
            }
            user.setPicpath(path);

            Node node = new Node();
            node.setCode(user.getNodeCode());
            List<Node> nodes = nodeService.getNode(node);

            if (nodes.size() > 0) {
                user.setNodeName(nodes.get(0).getName());
            }

            String sroles = user.getRoleCode();
            sroles = sroles.replace("\"", "");
            sroles = sroles.replace("[", "");
            sroles = sroles.replace("]", "");
            List<String> l = Arrays.asList(sroles.split("\\,"));
            List<Role> roles = roleService.getRole(l);
            String s = "";
            for (int i = 0; i < roles.size(); i++) {
                s += roles.get(i).getName();
                s += "；";
            }
            user.setRoleName(s.substring(0, s.length() - 1));

            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("新增用户失败");
            return returnValue(baseOut);
        }
        baseOut.setCode("0");
        baseOut.setMsg("新增用户成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> updatePassword(@RequestBody BaseIn baseIn) {
        User user = objectToUser(baseIn.getObj());
        log.info(user.getCode());
        log.info(user.getPassword());
        log.info(user.getVerifyCode());

        BaseOut baseOut = new BaseOut();

        try {
            Message message = new Message();
            message.setUserCode(user.getCode());

            List<Message> message1 = messageService.getMessage(message);
            if (message1.size() == 0) {
                baseOut.setCode("-1");
                baseOut.setMsg("验证码发送错误，请重新发送");
                return returnValue(baseOut);
            } else {
                if (message1.get(0).getVerifyCode().equals(user.getVerifyCode()) == false) {
                    baseOut.setCode("-1");
                    baseOut.setMsg("验证码错误，请重新输入");
                    return returnValue(baseOut);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("输入验证码错误");
            return returnValue(baseOut);
        }

        try {
            userService.updatePassword(user);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("修改密码失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("修改密码成功");
        return returnValue(baseOut);
    }


    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> updateUser(@RequestBody BaseIn baseIn) {
        User user = objectToUser(baseIn.getObj());
        log.info(user.getCode());
        log.info(user.getName());
        log.info(user.getRoleCode());
        log.info(user.getNodeCode());
        log.info(user.getName());
        log.info(user.getEnabled());
        log.info(user.getPassword());
        log.info(user.getPicpath());
        log.info(user.getTelno());
        log.info(user.getVerifyCode());

        BaseOut baseOut = new BaseOut();

        try {
            Message message = new Message();
            message.setUserCode(user.getCode());

            List<Message> message1 = messageService.getMessage(message);
            if (message1.size() == 0) {
                baseOut.setCode("-1");
                baseOut.setMsg("验证码发送错误，请重新发送");
                return returnValue(baseOut);
            } else {
                if (message1.get(0).getVerifyCode().equals(user.getVerifyCode()) == false) {
                    baseOut.setCode("-1");
                    baseOut.setMsg("验证码错误，请重新输入");
                    return returnValue(baseOut);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("输入验证码错误");
            return returnValue(baseOut);
        }

        try {
            String path = user.getPicpath();
            if (path != null && !path.equals("")) {
                path = path.substring(path.indexOf("/image"));
            } else {
                path = "";
            }
            user.setPicpath(path);

            Node node = new Node();
            node.setCode(user.getNodeCode());
            List<Node> nodes = nodeService.getNode(node);

            if (nodes.size() > 0) {
                user.setNodeName(nodes.get(0).getName());
            }

            String sroles = user.getRoleCode();
            sroles = sroles.replace("\"", "");
            sroles = sroles.replace("[", "");
            sroles = sroles.replace("]", "");
            List<String> l = Arrays.asList(sroles.split("\\,"));
            List<Role> roles = roleService.getRole(l);
            String s = "";
            for (int i = 0; i < roles.size(); i++) {
                s += roles.get(i).getName();
                s += "；";
            }
            user.setRoleName(s.substring(0, s.length() - 1));

            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("修改用户失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("修改用户成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> deleteUser(@RequestBody BaseIn baseIn) {
        User user = objectToUser(baseIn.getObj());
        log.info(user.getCode());

        BaseOut baseOut = new BaseOut();
        try {
            userService.deleteUser(user);

            String location = pictureConfig.getLocation();
            log.info(location);
            String path = location.substring(5, location.length()) + "/" + user.getCode() + ".jpg";
            log.info(path);
            File f = new File(path);
            if (f.exists() == true) {
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("删除用户失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("删除用户成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/resetPasswd", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> resetPasswd(@RequestBody BaseIn baseIn) {
        User user = objectToUser(baseIn.getObj());
        log.info(user.getCode());

        BaseOut baseOut = new BaseOut();
        try {
            String password = DigestUtils.md5DigestAsHex("123456".getBytes());
            user.setPassword(password);
            userService.resetPasswd(user);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("重置用户密码失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("重置用户密码成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/checkPasswd", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> checkPasswd(@RequestBody BaseIn baseIn) {
        User user = objectToUser(baseIn.getObj());
        log.info(user.getCode());
        log.info(user.getPassword());
        String md51 = "";
        String code = "";

        BaseOut baseOut = new BaseOut();
        try {
            List<User> res = userService.getUsers(user);
            if (res.size() != 0) {
                md51 = res.get(0).getPassword();
            }
            Message message = new Message();
            message.setUserCode(user.getCode());
            List<Message> message1 = messageService.getMessage(message);
            if (message1.size() != 0) {
                code = message1.get(0).getVerifyCode();
            }

            String password = DigestUtils.md5DigestAsHex((md51 + code).getBytes());
            log.info(password);
            if (!user.getPassword().equals(password)) {
                baseOut.setCode("-1");
                baseOut.setMsg("用户密码效验失败");
                return returnValue(baseOut);
            } else {
                Login login = new Login();
                login.setUserCode(user.getCode());
                login.setState("1");
                List<Login> l = loginService.getLogin(login);
                if (l.size() == 0) {
                    loginService.addLogin(login);
                } else {
                    loginService.updateLogin(login);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("用户密码效验失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("用户密码效验成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/updateLogin", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> updateLogin(@RequestBody BaseIn baseIn) {
        User user = objectToUser(baseIn.getObj());
        log.info(user.getCode());
        log.info(user.getPassword());
        String md51 = "";
        String code = "";

        BaseOut baseOut = new BaseOut();
        try {
            List<User> res = userService.getUsers(user);
            if (res.size() != 0) {
                md51 = res.get(0).getPassword();
            }
            Message message = new Message();
            message.setUserCode(user.getCode());
            List<Message> message1 = messageService.getMessage(message);
            if (message1.size() != 0) {
                code = message1.get(0).getVerifyCode();
            }

            String password = DigestUtils.md5DigestAsHex((md51 + code).getBytes());
            log.info(password);
            if (!user.getPassword().equals(password)) {
                baseOut.setCode("-1");
                baseOut.setMsg("用户密码效验失败");
                return returnValue(baseOut);
            } else {
                Login login = new Login();
                login.setUserCode(user.getCode());
                login.setState("1");
                List<Login> l = loginService.getLogin(login);
                if (l.size() == 0) {
                    loginService.addLogin(login);
                } else {
                    loginService.updateLogin(login);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("用户密码效验失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("用户密码效验成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> logout(@RequestBody BaseIn baseIn) {
        User user = objectToUser(baseIn.getObj());
        log.info(user.getCode());

        BaseOut baseOut = new BaseOut();
        try {
            Login login = new Login();
            login.setUserCode(user.getCode());
            login.setState("0");
            loginService.updateLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("用户注销失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("用户注销成功");
        return returnValue(baseOut);
    }
}
