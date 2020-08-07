package com.github.pure.cm.test.web;

import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-06 14:18
 **/
@Slf4j
@RocketMQTransactionListener()
public class TxTest implements RocketMQLocalTransactionListener {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 该方法用于消息 producer 端发送消息到 broker 后，检查本地事务是否执行成功，如果检查到本地事务执行成功，则消息可以被消费端消费，如果事务回滚，则消息会被 broker 删除。
     *
     * @param msg 发送的消息，可以检查消息是否被成功发送
     * @param arg 发送端带有的参数
     * @return producer 本地事务执行状态
     */
    @SneakyThrows
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.error("executeLocalTransaction、message={},arg={}", msg, arg);
        ConsumerMessage consumerMessage = JsonUtil.newIns().jsonToObject(new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8), ConsumerMessage.class);
        log.error("{}", consumerMessage);
        stringRedisTemplate.opsForValue().set("Transaction:" + msg.getHeaders().get("rocketmq_TRANSACTION_ID").toString(), "true");
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 用于事务回查，在事务执行过程时 【executeLocalTransaction】 失败，mq server 将会调用其他 producer 根据 transaction id 查询事务是否执行成功。
     *
     * @param msg 消息，包含事务ID
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.error("checkLocalTransaction、{}", msg);
        String transactionId = stringRedisTemplate.opsForValue().get("Transaction:" + msg.getHeaders().get("rocketmq_TRANSACTION_ID").toString());
        if (Boolean.valueOf(transactionId)) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
