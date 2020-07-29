package com.github.pure.cm.auth.sdk.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * <p>jwt格式用户信息</p>
 *
 * @author : 陈欢
 * @date : 2020-07-29 16:14
 **/
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 账号名
     */
    private String accountName;

    /**
     * 权限集合
     */
    private List<String> authorities;
}
