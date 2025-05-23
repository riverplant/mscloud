package com.riverplant.mscloud.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class MyKafkaConsumer {
    public static void main(String[] args) {

        //0. 配置
        Properties properties = new Properties();

        // 连接kafka集群
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //groupId
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerTest");

        //防止重复消费或性能问题
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 没有 offset 时从最早的消息开始


        try ( //1.创建一个消费者
              KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        ) {

            //2. 定义主题列表
            consumer.subscribe(List.of("test-topic"));

            //消费数据
            while (true) {

                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : consumerRecords) {
                    System.out.println(record);
                }

            }
        }


    }
}
