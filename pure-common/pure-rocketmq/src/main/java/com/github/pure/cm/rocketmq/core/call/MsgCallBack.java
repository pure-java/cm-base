package com.github.pure.cm.rocketmq.core.call;

import com.github.pure.cm.rocketmq.core.msg.MqAsyncMessage;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * <p>
 * 消息异步发送回调
 * </p>
 *
 * @since : 陈欢 2020-08-14 09:08
 **/
public interface MsgCallBack<T> {

    void onSuccess(final MqAsyncMessage<T> mqAsyncMessage, final SendResult sendResult);

    void onException(final MqAsyncMessage<T> mqAsyncMessage, final Throwable e);
}
