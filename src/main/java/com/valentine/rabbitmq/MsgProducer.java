package com.valentine.rabbitmq;

import com.valentine.rabbitmq.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 编写消息的生产者
 *
 * @author Valentine
 * @since 2018/11/24 16:06
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @param  content 消息内容
     * @author Valentine
     * @since 2018/11/24 16:37
     */
    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_SPRING_CLOUD_CONFIG对应的队列当中去，对应的是队列EXCHANGE_SPRING_CLOUD_CONFIG
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_SPRING_CLOUD_CONFIG, RabbitConfig.ROUTINGKEY_SPRING_CLOUD_CONFIG, content, correlationId);
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" 回调id:" + correlationData);
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + cause);
        }
    }
}
