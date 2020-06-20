package com.github.pure.cm.auth.server.controller;

import com.github.pure.cm.auth.server.model.entity.SysUser;
import com.github.pure.cm.auth.server.service.ISysUserService;
import com.github.pure.cm.common.data.model.PageResult;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.common.data.base.BaseController;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.StringUtil;
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
 * @since 2019-11-21
 */
@Api(tags = "")
@RestController
@RequestMapping("/identity/idUser")
public class SysUserController extends BaseController<ISysUserService> {

  @ApiOperation(value = "分页查询")
  @PostMapping(value = "pageIdUser")
  public Result pageIdUser(@ModelAttribute PageWhere pageWhere, @ModelAttribute SysUser sysUser) {
    return Result.success(PageResult.of(super.service.pageIdUser(pageWhere, sysUser)));
  }

  @ApiOperation(value = "添加")
  @PostMapping(value = "modifyIdUser")
  @PreAuthorize(value = "hasAuthority('idUser:modifyIdUser')")
  public Result modifyIdUser(@ModelAttribute @Valid SysUser sysUser) {
    return Result.success(super.service.saveOrUpdateIdUser(sysUser));
  }

  @ApiOperation(value = "删除")
  @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
  @PostMapping(value = "removeIdUser")
  @PreAuthorize(value = "hasAuthority('idUser:removeIdUser')")
  public Result removeIdUser(@RequestParam("ids") String ids) {
    boolean status = super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    return Result.newInstance(status);
  }
}
