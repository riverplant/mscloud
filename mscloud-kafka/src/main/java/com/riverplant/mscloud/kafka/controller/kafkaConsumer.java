package com.riverplant.mscloud.kafka.controller;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
public class kafkaConsumer {

    @KafkaListener(topics = "riverplant", groupId = "nina")
    public void consumerTopic(String msg) {
        System.out.println("收到消息:" + msg);
    }
}
