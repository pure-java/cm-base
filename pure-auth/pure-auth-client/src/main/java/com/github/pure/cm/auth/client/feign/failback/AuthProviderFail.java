package com.github.pure.cm.auth.client.feign.failback;

import com.github.pure.cm.auth.client.feign.AuthProvider;
import com.github.pure.cm.common.core.exception.ApiException;
import com.github.pure.cm.common.core.model.Result;

import java.util.Map;

/**
 * @author bairitan
 * @date 2019/12/23
 */
//@Component
public class AuthProviderFail implements AuthProvider {

    @Override
    public String publicTokenKey() {
        return null;
    }

    @Override
    public Map<String, Object> checkToken(Map<String, Object> checkToken) {
        return null;
    }

    @Override
    public Map<String, Object> token(Map<String, Object> client) throws ApiException {
        throw new ApiException(Result.fail("获取token发生错误"));
    }
}
