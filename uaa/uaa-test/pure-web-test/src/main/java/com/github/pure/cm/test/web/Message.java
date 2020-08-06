package com.github.pure.cm.test.web;

import lombok.Data;

/**
 * <p>消息
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-08-05 16:48
 **/
@Data
public class Message {

    public static final String topic = "test-topic";
    public static final String Reply_topic = "test-topic_Reply";

    private Integer id;
    private String name;
    private String status;
    private String createTime;
}
