package com.github.pure.cm.auth.server.api;

import com.github.pure.cm.auth.server.model.entity.SysResource;
import com.github.pure.cm.auth.server.service.SysResourceService;
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

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenhuan
 * @since 2019-11-20
 */
@Api(tags = "")
@RestController
@RequestMapping("/identity/idAuthority")
public class SysAuthorityController extends BaseController<SysResourceService> {

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "pageIdAuthority")
    //@PreAuthorize(value = "hasAuthority('sysAuthority:pageIdAuthority') and hasRole('default_user')")
    public PageResult<SysResource> pageIdAuthority(@ModelAttribute PageWhere pageWhere, @ModelAttribute SysResource sysResource) {
        return PageResult.of(super.service.pageIdAuthority(pageWhere, sysResource));
    }

    @ApiOperation(value = "添加")
    @PostMapping(value = "modifyIdAuthority")
    @PreAuthorize(value = "hasAuthority('sysAuthority:modifyIdAuthority')")
    //@AuthResource(name = "权限权限", code = "'sysAuthority:modifyIdAuthority'", groupCode = "sysAuthority")
    public boolean modifyIdAuthority(@ModelAttribute @Valid SysResource sysResource) {
        return super.service.saveOrUpdateIdAuthority(sysResource);
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
    @PostMapping(value = "removeIdAuthority")
    @PreAuthorize(value = "hasAuthority('idAuthority:removeIdAuthority')")
    public boolean removeIdAuthority(@RequestParam("ids") String ids) {
        return super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    }
}
