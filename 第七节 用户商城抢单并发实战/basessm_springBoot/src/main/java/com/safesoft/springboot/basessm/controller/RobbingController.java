package com.safesoft.springboot.basessm.controller;

import com.safesoft.springboot.basessm.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jay.zhou
 * @date 2019/4/26
 * @time 16:00
 */
@RestController
public class RobbingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RobbingController.class);
    private static final String PREFIX = "/product/";

    @Autowired
    private InitService initService;

    @RequestMapping(value = PREFIX + "robbing")
    public void robbingProduct() {
        LOGGER.info("开抢");
        initService.generateMultiThread();
    }
}
