package com.github.pure.cm.common.data.base;

/**
 * 基础mapper，主要用于提供通用操作<br>
 * 之所以继承 mybatisplus 的mapper，是为了实现不继承 mybatisplus 的 serviceImpl，来操作数据
 *
 * @author chenhuan
 * @date 2017-07-12 19:54
 **/
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

}
