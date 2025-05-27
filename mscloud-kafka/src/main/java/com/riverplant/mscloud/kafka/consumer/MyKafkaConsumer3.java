package com.riverplant.mscloud.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class MyKafkaConsumer3 {
    public static void main(String[] args) {

        //0. 配置
        Properties properties = new Properties();

        // 连接kafka集群
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //groupId
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerTest2");

        //设置分区分配策略
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");

        //防止重复消费或性能问题
        //设置为自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        //提交时间间隔
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 没有 offset 时从最早的消息开始


        try ( //1.创建一个消费者
              KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        ) {

            //2. 定义主题列表
            consumer.subscribe(List.of("test-topic"));

            /*如果生产者将数据发送送给指定的分区
            例如将所有订单数据发送刚给以订单号为id
             **/
            //订阅主题对应的分区
         /*   consumer.assign(
                    List.of(
                            new TopicPartition("test-topic",0)
                    ));
*/
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
