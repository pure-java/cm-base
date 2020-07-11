package com.github.pure.cm.gate.gateway.feign.failback;

import com.github.pure.cm.gate.gateway.feign.AuthProvider;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author bairitan
 * @date 2019/12/23
 */
@Component
public class AuthProviderFail implements AuthProvider {

    @Override
    public String publicTokenKey() {
        return null;
    }

    @Override
    public Map<String, Object> checkToken(Map<String, Object> checkToken, String authorization) {
        throw new BusinessException(200, "token校验失败");
    }

    @Override
    public Map<String, Object> token(Map<String, Object> client) throws BusinessException {
        throw new BusinessException(Result.fail("获取token发生错误"));
    }
}
