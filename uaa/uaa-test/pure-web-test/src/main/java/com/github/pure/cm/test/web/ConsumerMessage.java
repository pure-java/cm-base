package com.github.pure.cm.test.web;

import com.github.pure.cm.rocketmq.MqMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>消息
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-05 16:48
 **/
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConsumerMessage extends MqMessage<ConsumerMessage> {

    public static final String topic = "test-topic";
    public static final String Reply_topic = "test-topic_Reply";

    private Integer id;
    private String name;
    private String status;
    private String createTime;

}
