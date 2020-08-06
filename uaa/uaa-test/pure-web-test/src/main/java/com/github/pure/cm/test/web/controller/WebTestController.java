package com.github.pure.cm.test.web.controller;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.date.DateUtil;
import com.github.pure.cm.test.web.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户验证控制类
 *
 * @author bairitan
 * @date 2019/12/9
 */
@Slf4j
@RestController
@RequestMapping("/web/test")
public class WebTestController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping("/syncSend")
    public Result<SendResult> syncSend() {
        Message message = new Message();
        message.setCreateTime(DateUtil.newIns().asStr());
        message.setId(1);
        message.setName("syncSend");
        message.setStatus("状态");
        SendResult sendResult = rocketMQTemplate.syncSend(Message.topic + ":test1", message);
        log.error("发送结果{}", sendResult);
        return Result.success(sendResult);
    }

    @PostMapping("/delaySyncSend")
    public Result<SendResult> delaySyncSend() {
        Message message = new Message();
        message.setCreateTime(DateUtil.newIns().asStr());
        message.setId(1);
        message.setName("syncSend");
        message.setStatus("状态");

        SendResult sendResult = rocketMQTemplate.syncSend(Message.topic + ":test1", new GenericMessage<>(message), 1000, 2);
        log.error("发送结果{}", sendResult);
        return Result.success(sendResult);
    }


    /**
     * 等待消费者返回结果
     */
    @PostMapping("/sendAndReceive")
    public Result<String> sendAndReceive() {
        Message message = new Message();
        message.setCreateTime(DateUtil.newIns().asStr());
        message.setId(1);
        message.setName("sendAndReceive");
        message.setStatus("状态");
        String result = rocketMQTemplate.sendAndReceive(Message.Reply_topic + ":test1", message, String.class);
        log.error("返回结果{}", result);
        return Result.success(result);
    }

    @PostMapping("sendMessageInTransaction")
    public Result<TransactionSendResult> sendMessageInTransaction() {
        Message message = new Message();
        message.setCreateTime(DateUtil.newIns().asStr());
        message.setId(1);
        message.setName("sendMessageInTransaction");
        message.setStatus("状态");

        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(Message.topic, new GenericMessage<>(message), "0--00");
        log.error("{}", transactionSendResult);
        return Result.success(transactionSendResult);
    }


}
