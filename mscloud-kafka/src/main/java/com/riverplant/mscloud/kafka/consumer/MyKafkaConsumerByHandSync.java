package com.riverplant.mscloud.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class MyKafkaConsumerByHandSync {
    public static void main(String[] args) {

        /*0. 配置*/
        Properties properties = new Properties();

        // 连接kafka集群
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //groupId配置消费者组Id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerTest2");

        //设置分区分配策略
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");

        //防止重复消费或性能问题
        //关闭自动提交，设置为手动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");


        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        /*
         * 指定Offset消费
         * auto.offest.rest = earliest | latest | none 默认是latest
         * earliest： 自动将偏移量重置为最早的偏移量
         * latest: 自动将偏移量重置为最新的偏移量
         * none: 如果未找到消费者组的先前偏移量，则向消费者抛出异常
         */
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 没有 offset 时从最早的消息开始


        try ( //1.创建一个消费者
              KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)
        ) {

            //2. 定义主题列表
            consumer.subscribe(List.of("test-topic"));

            /*
             * 指定位置进行消费
             */
            Set<TopicPartition> assignment = consumer.assignment();//获得分区的集合

            //保证分区分配已经指定完毕
            while(assignment.isEmpty()) {
                consumer.poll(Duration.ofSeconds(1));
                assignment = consumer.assignment();
            }

            //  希望可以把时间转成对应的offset, 实现按照时间进行消费
            //TopicPartition: 分区信息
            //Long: Offset
            HashMap<TopicPartition, Long> topicPartitionLongHashMap = new HashMap<>();

            for(TopicPartition partition : assignment) {
                // 设置一天前的时间
                topicPartitionLongHashMap.put(partition, System.currentTimeMillis() - 24 *3600 * 1000);
            }
            Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = consumer.offsetsForTimes(topicPartitionLongHashMap);


            // 按照指定位置 offset进行消费
            for(TopicPartition partition : assignment) {

                consumer.seek(partition, topicPartitionOffsetAndTimestampMap.get(partition).offset());//指定从100这个位置开始向后消费

            }


            /*如果生产者将数据发送送给指定的分区
            例如将所有订单数据发送刚给以订单号为id
             **/
            //订阅主题对应的分区
     /*       consumer.assign(
                    List.of(
                            new TopicPartition("test-topic",0)
                    ));
**/
            //消费数据
            while (true) {

                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : consumerRecords) {
                    System.out.println(record);
                }

                consumer.commitSync();//同步提交，会阻塞当前线程，并且自带失败重试
                //consumer.commitAsync();//异步提交，没有错误重试

            }
        }


    }
}
