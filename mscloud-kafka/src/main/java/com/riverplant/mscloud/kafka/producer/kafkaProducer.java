package com.riverplant.mscloud.kafka.producer;

import com.riverplant.mscloud.kafka.MyPartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class kafkaProducer {

    public static void main(String[] args) {

        //0. 属性配置
        Properties properties = new Properties();
        //连接kafka集群
        //properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,server2:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        //只当对应的key和value的序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //关联自定义分区管理器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());

        //生產者提高吞吐量!!!!!!!
        //缓存区大小,32M
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        //批次大小 16k(生成环境中可以改为32k)
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        //linger.ms: 1ms(生产环境5~100ms)
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        //压缩类型： gzip, snappy,lz4 和 zstd
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");


        //ACK配置: 0, 1, -1(all)
        properties.put(ProducerConfig.ACKS_CONFIG, "all");

        //重试:3次
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);


        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true");
        //指定事务id
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"transaction_id_01");

        //1. 创建生产者对象
        try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {

            //初始化事务
            kafkaProducer.initTransactions();

            // 启动事务
            kafkaProducer.beginTransaction();


            try {
                //2. 发送数据
                for (int i = 0; i < 500; i++) {
                    //可以在这里将消息发送给指定的partition
                    //ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, key, value);
                    kafkaProducer.send(new ProducerRecord<>("riverplant", "hello" + i),
                            // metadata: metadata数据信息
                            // exception: 异常信息
                            (metadata, exception) -> {
                                if (exception == null) {
                                    System.out.println("topic:" + metadata.topic() + " || partition: " + metadata.partition());
                                }
                            });
                }
                // 提交事务
                kafkaProducer.commitTransaction();
            } catch (Exception e) {
                kafkaProducer.abortTransaction();
            }

        }
        //3. 关闭资源
    }
}
