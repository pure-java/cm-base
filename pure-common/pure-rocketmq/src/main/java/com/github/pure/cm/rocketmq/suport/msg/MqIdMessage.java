package com.github.pure.cm.rocketmq.suport.msg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>mq消息</p>
 * <p>
 * T:子类类型，以支持 链式表达式 模式
 *
 * @author : 陈欢
 * @date : 2020-08-06 16:58
 **/
@Getter
@Setter
@ToString
public class MqIdMessage<T> {

    /**
     * 业务自定义ID，不能重复;<br>
     * 使用该ID以保证消费端能够给去重，保证幂等性。<br>
     * 去重不应该使用MsgID和OffsetMsgId，因为可能相同的数据由于网络原因、或者其他原因导致数据成功存储在broker，<br>
     * 但是响应客户端ack时，失败了，导致重试，重复的数据生成不同的MsgID和OffsetMsgID了。
     */
    private String nxId;

    public MqIdMessage() {

    }

    public T setNxId(String nxId) {
        this.nxId = nxId;
        return (T) this;
    }
}
