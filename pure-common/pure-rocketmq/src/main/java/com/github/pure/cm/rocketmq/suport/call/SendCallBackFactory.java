package com.github.pure.cm.rocketmq.suport.call;

import com.github.pure.cm.rocketmq.suport.msg.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>
 * mq消息异步发送消息回调，工厂类，统一处理消息
 * </p>
 *
 * @since : 陈欢 2020-08-13 17:58
 **/
@Component
@Slf4j
public class SendCallBackFactory {

    /**
     * 异步调用消息回调
     *
     * @param asyncMessage 异步发送消息时的参数
     * @return
     */
    public <T> SendCallback newIns(MqMessage<T> asyncMessage) {
        return new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.error("发送消息成功:{}", sendResult);
                if (Objects.nonNull(asyncMessage.getSendCallback())) {
                    asyncMessage.getSendCallback().onSuccess(sendResult);
                }
            }

            @Override
            public void onException(Throwable e) {
                log.error("发送消息失败:", e);
                if (Objects.nonNull(asyncMessage.getSendCallback())) {
                    asyncMessage.getSendCallback().onException(e);
                }
            }
        };
    }
}
