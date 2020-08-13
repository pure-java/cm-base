package com.github.pure.cm.rocketmq.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Component;

/**
 * <p>
 * mq消息异步发送消息回调
 * </p>
 *
 * @since : 陈欢 2020-08-13 17:58
 **/
@Component
@Slf4j
public class CallBackFactory {

    /**
     * 异步调用消息回调
     *
     * @param asyncMessage 异步发送消息时的参数
     * @return
     */
    public SendCallback newIns(MqAsyncMessage asyncMessage) {
        return new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                log.error("发送消息成功:{}", sendResult);
                asyncMessage.getSendCallback().onSuccess(sendResult);
            }

            @Override
            public void onException(Throwable e) {
                log.error("发送消息失败:", e);
                asyncMessage.getSendCallback().onException(e);
            }
        };
    }
}
