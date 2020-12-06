package org.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.manage.model.*;
import org.manage.service.NodeService;
import org.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Diego
 * @date: 2020/8/11 9:52
 * @Des:
 */

@RestController
@CrossOrigin
@Slf4j
public class NodeController {
    @Autowired
    NodeService nodeService;

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

    @RequestMapping(value = "/queryNode", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> queryNode(@RequestBody BaseIn baseIn) {
        int page = baseIn.getPageIndex();
        int num = baseIn.getPageNumber();
        log.info(Integer.toString(page));
        log.info(Integer.toString(num));
        BaseOut baseOut = new BaseOut();

        if (page == 0 && num == 0) {
            try {
                List<Node> res = nodeService.getAllNodes();

                Tree tree = new Tree();
                List<Tree> list1 = new ArrayList<Tree>();
                List<Tree> list2 = new ArrayList<Tree>();
                List<Tree> list3 = new ArrayList<Tree>();

                for (Node node : res) {
                    if (node.getCode().length() == 2) {
                        tree.setTitle(node.getName());
                        tree.setCode(node.getCode());
                    }
                    if (node.getCode().length() == 4) {
                        Tree t1 = new Tree();
                        t1.setTitle(node.getName());
                        t1.setCode(node.getCode());
                        list1.add(t1);
                    }
                    if (node.getCode().length() == 6) {
                        Tree t2 = new Tree();
                        t2.setTitle(node.getName());
                        t2.setCode(node.getCode());
                        list2.add(t2);
                    }
                    if (node.getCode().length() == 8) {
                        Tree t3 = new Tree();
                        t3.setTitle(node.getName());
                        t3.setCode(node.getCode());
                        list3.add(t3);
                    }
                }
                for (Tree t2 : list2) {
                    List<Tree> tmp = new ArrayList<Tree>();
                    for (Tree t3 : list3) {
                        if (t2.getCode().equals(t3.getCode().substring(0,6))) {
                            tmp.add(t3);
                        }
                    }
                    t2.setChildren(tmp);
                }
                for (Tree t1 : list1) {
                    List<Tree> tmp = new ArrayList<Tree>();
                    for (Tree t2 : list2) {
                        if (t1.getCode().equals(t2.getCode().substring(0,4))) {
                            tmp.add(t2);
                        }
                    }
                    t1.setChildren(tmp);
                }

                tree.setChildren(list1);

                List<Object> o = new ArrayList<Object>();
                o.add(tree);

                baseOut.setLobj(o);
                baseOut.setCode("0");
                Integer allNum = nodeService.getAllNum();
                baseOut.setTotalCount(allNum);
                baseOut.setMsg("查询机构成功");
            } catch (Exception e) {
                e.printStackTrace();
                baseOut.setCode("-1");
                baseOut.setMsg("查询机构失败");
                return returnValue(baseOut);
            }
        }
        else {
            try {
                PageHelper.startPage(page,num);

                List<Node> res = nodeService.getAllNodes();

                PageInfo pageInfo = new PageInfo<Node>(res);

                List<Object> o = pageInfo.getList();
                baseOut.setLobj(o);
                baseOut.setCode("0");
                Integer allNum = nodeService.getAllNum();
                baseOut.setTotalCount(allNum);
                baseOut.setMsg("查询机构成功");
            } catch (Exception e) {
                e.printStackTrace();
                baseOut.setCode("-1");
                baseOut.setMsg("查询机构失败");
                return returnValue(baseOut);
            }
        }

        baseOut.setCode("0");
        baseOut.setMsg("查询机构成功");
        return returnValue(baseOut);
    }

    private Node objectToNode(Object o) {
        Node node = JSON.parseObject(JSON.toJSONString(o), new TypeReference<Node>() {});
        return node;
    }

    @RequestMapping(value = "/addNode", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> addNode(@RequestBody BaseIn baseIn) {
        Node node = objectToNode(baseIn.getObj());
        log.info(node.getCode());
        log.info(node.getName());

        BaseOut baseOut = new BaseOut();

        try {
            nodeService.addNode(node);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("新增机构失败");
            return returnValue(baseOut);
        }
        baseOut.setCode("0");
        baseOut.setMsg("新增机构成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/updateNode", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> updateNode(@RequestBody BaseIn baseIn) {
        Node node = objectToNode(baseIn.getObj());
        log.info(node.getCode());
        log.info(node.getName());

        BaseOut baseOut = new BaseOut();
        try {
            nodeService.updateNode(node);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("修改机构失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("修改机构成功");
        return returnValue(baseOut);
    }

    @RequestMapping(value = "/deleteNode", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"
    })
    @ResponseBody
    ResponseEntity<String> deleteNode(@RequestBody BaseIn baseIn) {
        Node node = objectToNode(baseIn.getObj());
        log.info(node.getCode());

        BaseOut baseOut = new BaseOut();
        try {
            User user = new User();
            user.setNodeCode(node.getCode());
            List<User> l = userService.getUserByNodeCode(user);
            if (l.size() > 0) {
                baseOut.setCode("-1");
                baseOut.setMsg("有用户使用该机构请勿删除");
                return returnValue(baseOut);
            }

            nodeService.deleteNode(node);
        } catch (Exception e) {
            e.printStackTrace();
            baseOut.setCode("-1");
            baseOut.setMsg("删除机构失败");
            return returnValue(baseOut);
        }

        baseOut.setCode("0");
        baseOut.setMsg("删除机构成功");
        return returnValue(baseOut);
    }
}
