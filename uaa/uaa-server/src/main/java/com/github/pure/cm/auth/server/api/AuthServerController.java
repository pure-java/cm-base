package com.github.pure.cm.auth.server.api;

import com.github.pure.cm.auth.server.service.SysResourceService;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.model.auth.vo.AuthRegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @since 2019/11/19
 */
@Slf4j
@RestController
@RequestMapping("authServer")
@Api(value = "权限服务", tags = "权限服务")
public class AuthServerController {

    @Autowired
    private SysResourceService sysResourceService;

    @ApiOperation(value = "注册权限")
    @PostMapping("/registerAuth")
    public Result<Boolean> registerAuth(@RequestBody @Validated AuthRegisterVo registerVo) {
        return Result.success(this.sysResourceService.registerAuth(registerVo));
    }

}
