package com.github.pure.cm.rocketmq.suport.call;

import com.github.pure.cm.rocketmq.suport.msg.MqMessage;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * <p>
 * 消息异步发送回调
 * </p>
 *
 * @since : 陈欢 2020-08-14 09:08
 **/
public interface MsgCallBack<T> {

    void onSuccess(final MqMessage<T> mqMessage, final SendResult sendResult);

    void onException(final MqMessage<T> mqMessage, final Throwable e);
}
