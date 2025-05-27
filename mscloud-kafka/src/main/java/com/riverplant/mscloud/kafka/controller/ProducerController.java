package com.riverplant.mscloud.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class ProducerController {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/send")
    public String data(String msg) {
        //msg接收到数据之后，通过kafka发送出去
        kafkaTemplate.send("riverplant", msg);
        return "ok";
    }
}
