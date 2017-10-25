package com.sample.controller;

import com.mini.mvc.annotation.Controller;
import com.mini.mvc.annotation.PostParam;
import com.mini.mvc.annotation.RequestMapping;
import com.mini.mvc.domain.MediaType;
import com.mini.mvc.domain.RequestMethod;
import com.sample.entity.User;

/**
 * Created by zhanghaojie on 2017/10/21.
 * 演示代码
 */
@Controller
public class SampleController {

    /**
     * get 返回json
     *
     * @param name 用户名
     * @return
     */
    @RequestMapping(value = "/user/get", produces = MediaType.JSON_UTF_8)
    public User getUser(String name) {
        User user = new User();
        // 一般业务应该是从缓存或者数据库取用户信息,这里只是示例,直接伪造用户信息
        user.setName(name);
        user.setAge(23);
        return user;
    }

    /**
     * add 返回json
     * 
     * @param user 添加新用户
     * @return 反馈
     */
    @RequestMapping(value = "/user/add", method = RequestMethod.POST, produces = MediaType.JSON_UTF_8)
    public User addUser(@PostParam User user) {
        user.setCallback("Yes, you successed");
        return user;
    }
}
