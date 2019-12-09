package com.github.canglan.cm.auth.server.controller;

import com.github.canglan.cm.auth.server.model.entity.SysAuthority;
import com.github.canglan.cm.auth.server.service.ISysAuthorityService;
import com.github.canglan.cm.common.data.model.PageResult;
import com.github.canglan.cm.common.data.model.PageWhere;
import com.github.canglan.cm.common.data.base.BaseController;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class SysAuthorityController extends BaseController<ISysAuthorityService> {

  @ApiOperation(value = "分页查询")
  @PostMapping(value = "pageIdAuthority")
  public Result pageIdAuthority(@ModelAttribute PageWhere pageWhere, @ModelAttribute SysAuthority sysAuthority) {
    return Result.success(PageResult.of(super.service.pageIdAuthority(pageWhere, sysAuthority)));
  }

  @ApiOperation(value = "添加")
  @PostMapping(value = "modifyIdAuthority")
  // @PreAuthorize(value = "hasAuthority('sysAuthority:modifyIdAuthority')")
  public Result modifyIdAuthority(@ModelAttribute @Valid SysAuthority sysAuthority) {
    return Result.success(super.service.saveOrUpdateIdAuthority(sysAuthority));
  }

  @ApiOperation(value = "删除")
  @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
  @PostMapping(value = "removeIdAuthority")
  @PreAuthorize(value = "hasAuthority('idAuthority:removeIdAuthority')")
  public Result removeIdAuthority(@RequestParam("ids") String ids) {
    boolean status = super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    return Result.newInstance(status);
  }
}
