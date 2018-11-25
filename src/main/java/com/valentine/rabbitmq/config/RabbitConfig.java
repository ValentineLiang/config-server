package com.valentine.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 *
 * @author Valentine
 * @since 2018/11/24 16:01
 */
@Configuration
public class RabbitConfig {
    public static final String EXCHANGE_SPRING_CLOUD_CONFIG = "exchange_spring_cloud_config";
    public static final String QUEUE_SPRING_CLOUD_CONFIG = "queue_spring_cloud_config";
    public static final String ROUTINGKEY_SPRING_CLOUD_CONFIG = "routingkey_spring_cloud_config";

    /**
     * direct：发送者发送一个消息给指定的队列
     * <p>
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_SPRING_CLOUD_CONFIG);
    }

    /**
     * 获取springCloudConfig队列
     */
    @Bean
    public Queue queueSpringCloudConfig() {
        // durable：队列持久
        return new Queue(QUEUE_SPRING_CLOUD_CONFIG, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueSpringCloudConfig()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_SPRING_CLOUD_CONFIG);
    }


}
