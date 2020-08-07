package com.github.pure.cm.rocketmq;

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
public class MqMessage<T> {

    /**
     * 业务自定义事务ID，不能重复;<br>
     * 使用该ID以保证消费端能够给去重，保证幂等性。
     */
    private String nxId;

    public MqMessage() {
    }

    public MqMessage(String nxId) {
        this.nxId = nxId;
    }

    public T setNxId(String nxId) {
        this.nxId = nxId;
        return (T) this;
    }
}
