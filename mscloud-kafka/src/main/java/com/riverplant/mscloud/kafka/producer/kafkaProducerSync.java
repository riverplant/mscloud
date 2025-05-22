package com.riverplant.mscloud.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class kafkaProducerSync {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //0. 属性配置
        Properties properties = new Properties();
        //连接kafka集群
        //properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,server2:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        //只当对应的key和value的序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //1. 创建生产者对象
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {
            //2. 发送数据
            for (int i = 0; i < 5; i++) {
                kafkaProducer.send(
                        //这里可以指定分区，或者指定key
                        new ProducerRecord<>("test-topic", "nihao-riverplant" + i),
                        // metadata: metadata数据信息
                        // exception: 异常信息
                        (metadata, exception) -> {
                             if(exception == null) {
                                 System.out.println("topic:" + metadata.topic() + " || partition: " + metadata.partition());
                             }
                        }).get(); //同步发送,必须将所有的数据发送完毕才可以接收
            }

        }

        //3. 关闭资源
    }
}
