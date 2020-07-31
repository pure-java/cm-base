package com.github.pure.cm.auth.sdk.util;

import com.github.pure.cm.common.core.exception.InnerSystemExceptions;
import com.github.pure.cm.common.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>权限辅助类
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-07-30 11:13
 **/
@Slf4j
@Component
public class AuthUtil {
    /**
     * 项目 code
     */
    private static String applicationCode;

    public AuthUtil() {
    }

    /**
     * 对权限码进行处理
     *
     * @param roleCode
     * @return
     */
    public static String convertRoleCode(String roleCode) {
        return roleCode.startsWith("ROLE_") ? roleCode : "ROLE_" + roleCode;
    }

    /**
     * 对权限码进行转换
     */
    public static String convertAuthCode(String authCode) {
        if (StringUtils.isBlank(authCode)) {
            return "";
        }
        // 转换为小写，并将 多个 中划线和下划线转换为一个下划线
        authCode = authCode.replaceAll("[-_]+", "_");

        String prefix = ("_" + applicationCode + "_").replaceAll("[-_]+", "_");
        authCode = authCode.startsWith(prefix) ? authCode : prefix + authCode;

        authCode = authCode.replaceAll("[-_]+", "_");
        return authCode.toLowerCase();
    }

    public static String getApplicationCode() {
        return applicationCode;
    }

    @Value("${pure.application.code}")
    public void setApplicationCode(String applicationCode) {
        if (StringUtil.isBlank(applicationCode)) {
            throw new InnerSystemExceptions("applicationCode is null;\t请配置 applicationCode;");
        }
        AuthUtil.applicationCode = applicationCode;
    }

}
