package com.github.pure.cm.auth.server.api;

import com.github.pure.cm.auth.server.model.entity.SysRole;
import com.github.pure.cm.auth.server.service.SysRoleService;
import com.github.pure.cm.common.core.util.StringUtil;
import com.github.pure.cm.common.data.base.BaseController;
import com.github.pure.cm.common.data.model.PageResult;
import com.github.pure.cm.common.data.model.PageWhere;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenhuan
 * @since 2019-11-21
 */
@Api(tags = "")
@RestController
@RequestMapping("/identity/idRole")
public class SysRoleController extends BaseController<SysRoleService> {

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "pageIdRole")
    public PageResult<SysRole> pageIdRole(@ModelAttribute PageWhere pageWhere, @ModelAttribute SysRole sysRole) {
        return PageResult.of(super.service.pageIdRole(pageWhere, sysRole));
    }

    @ApiOperation(value = "添加")
    @PostMapping(value = "modifyIdRole")
    @PreAuthorize(value = "hasAuthority('idRole:modifyIdRole')")
    public boolean modifyIdRole(@ModelAttribute @Valid SysRole sysRole) {
        return super.service.saveOrUpdateIdRole(sysRole);
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
    @PostMapping(value = "removeIdRole")
    @PreAuthorize(value = "hasAuthority('idRole:removeIdRole')")
    public boolean removeIdRole(@RequestParam("ids") String ids) {
        return super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    }

    @PostMapping("selectMenuAuth")
    public boolean selectMenuAuth() {
        List<SysRole> sysRoles = super.service.selectMenuAuth();
        for (SysRole sysRole : sysRoles) {
            System.out.println(sysRole);
        }
        return true;
    }
}
