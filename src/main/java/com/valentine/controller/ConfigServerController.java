package com.valentine.controller;

import com.valentine.rabbitmq.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Valentine
 * @since 2018/11/24 16:36
 */
@RestController
@RequestMapping(value = "/config/server")
public class ConfigServerController {

    @Autowired
    private MsgProducer msgProducer;

    @RequestMapping(value = "/activeconfig", method = RequestMethod.POST)
    public void activeConfig() {
        msgProducer.sendMsg("active");
    }

}
