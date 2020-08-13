package com.github.pure.cm.test.web.controller;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.date.DateUtil;
import com.github.pure.cm.rocketmq.core.MqProducer;
import com.github.pure.cm.test.web.ConsumerMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private MqProducer mqProducer;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping("/syncSend")
    public Result<SendResult> syncSend() {
        ConsumerMessage consumerMessage = new ConsumerMessage();
        consumerMessage.setNxId(UUID.randomUUID().toString());
        consumerMessage.setCreateTime(DateUtil.newIns().asStr());
        consumerMessage.setId(1);
        consumerMessage.setName("syncSend");
        consumerMessage.setStatus("状态");
        SendResult sendResult = mqProducer.syncSend(ConsumerMessage.topic + ":test1", consumerMessage.setNxId(UUID.randomUUID().toString()));
        log.error("发送结果{}", sendResult);

        sendResult = mqProducer.syncSend(ConsumerMessage.topic + ":test2", consumerMessage.setNxId(UUID.randomUUID().toString()));
        log.error("发送结果{}", sendResult);
        return Result.success(sendResult);
    }

    // 异步发送
    //@Scheduled(/*cron = "10 * * * * ?"*/fixedDelay = 10000)
    @PostMapping("/asyncSend")
    public void asyncSend() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 1000, 1L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        threadPoolExecutor.execute(() -> {
            ConsumerMessage consumerMessage = new ConsumerMessage();
            consumerMessage.setNxId(UUID.randomUUID().toString());
            consumerMessage.setCreateTime(DateUtil.newIns().asStr());
            consumerMessage.setId(1);
            consumerMessage.setName("syncSend");
            consumerMessage.setStatus("状态");
            mqProducer.asyncSend(ConsumerMessage.topic + ":test1", consumerMessage.setNxId(UUID.randomUUID().toString()), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.error("发送成功:{}", consumerMessage);
                }

                @Override
                public void onException(Throwable e) {
                    log.error("发送失败:{}", consumerMessage, e);
                }
            });
            rocketMQTemplate.asyncSend(ConsumerMessage.topic + ":test1", consumerMessage.setNxId(UUID.randomUUID().toString()), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.error("发送成功:{}", consumerMessage);
                }

                @Override
                public void onException(Throwable e) {
                    log.error("发送失败:{}", consumerMessage, e);
                }
            });

        });

        System.out.println(mqProducer);
        System.out.println((rocketMQTemplate));
    }

    @PostMapping("/delaySyncSend")
    public Result<SendResult> delaySyncSend() {
        ConsumerMessage consumerMessage = new ConsumerMessage().setNxId(UUID.randomUUID().toString());
        consumerMessage.setCreateTime(DateUtil.newIns().asStr());
        consumerMessage.setId(1);
        consumerMessage.setName("syncSend");
        consumerMessage.setStatus("状态");

        SendResult sendResult = mqProducer.syncSend(ConsumerMessage.topic + ":test1", new GenericMessage<>(consumerMessage), 1000, 2);
        log.error("发送结果{}", sendResult);
        return Result.success(sendResult);
    }


    /**
     * 等待消费者返回结果
     */
    @PostMapping("/sendAndReceive")
    public Result<String> sendAndReceive() {
        ConsumerMessage consumerMessage = new ConsumerMessage().setNxId(UUID.randomUUID().toString())
                .setCreateTime(DateUtil.newIns().asStr())
                .setId(1)
                .setName("sendAndReceive")
                .setStatus("状态")
                .setNxId(UUID.randomUUID().toString());
        String result = mqProducer.sendAndReceive(ConsumerMessage.Reply_topic + ":test1", consumerMessage, String.class);
        log.error("返回结果{}", result);
        return Result.success(result);
    }

    @PostMapping("sendMessageInTransaction")
    public Result<TransactionSendResult> sendMessageInTransaction() {
        ConsumerMessage consumerMessage = new ConsumerMessage().setNxId(UUID.randomUUID().toString())
                .setCreateTime(DateUtil.newIns().asStr())
                .setId(1)
                .setName("sendMessageInTransaction")
                .setStatus("状态")
                .setNxId(UUID.randomUUID().toString());

        TransactionSendResult transactionSendResult = mqProducer.sendMessageInTransaction(ConsumerMessage.topic, new GenericMessage<>(consumerMessage), "0--00");
        log.error("{}", transactionSendResult);
        return Result.success(transactionSendResult);
    }


}
