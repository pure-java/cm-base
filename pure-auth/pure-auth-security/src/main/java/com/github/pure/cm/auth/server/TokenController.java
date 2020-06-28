package com.github.pure.cm.auth.server;

import com.github.pure.cm.common.core.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈欢
 * @since 2020/6/28
 */
@RequestMapping("token")
@RestController
public class TokenController {
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Result<String> login(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authenticate == null) {

        }
        return Result.success();
    }
}
