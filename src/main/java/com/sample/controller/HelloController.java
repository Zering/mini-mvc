package com.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mini.mvc.annotation.Controller;
import com.mini.mvc.annotation.RequestMapping;
import com.mini.mvc.domain.MediaType;

/**
 * Created by zhanghaojie on 2017/10/19.
 * HELLO
 */
@Controller
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_UTF_8)
    public String hello(String name) {
        log.debug("Welcome : " + name);
        return "Welcome : " + name;
    }
}
