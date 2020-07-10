package com.github.pure.cm.auth.server.api;

import com.github.pure.cm.auth.server.model.entity.SysResource;
import com.github.pure.cm.auth.server.service.SysResourceService;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.data.model.PageResult;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.model.auth.vo.AuthMenuGroupVo;
import com.github.pure.cm.model.auth.vo.AuthMenuItemVo;
import com.github.pure.cm.model.auth.vo.AuthRegisterVo;
import com.github.pure.cm.model.auth.vo.AuthResourceVo;
import com.github.pure.cm.model.auth.vo.AuthRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        PageResult.of(sysResourceService.pageIdAuthority(new PageWhere(), new SysResource()));
        //this.sysResourceService.registerAuth(registerVo);

        return Result.success(true);
    }

    public static void main(String[] args) {
        System.out.println(BCrypt.checkpw("test", "$2a$10$Y31Y7qEwjVowsCEqTdPWieJwa7BVEavfUksfTXTRFAFn1bjKrMS.O"));
        ;
    }
}
