 package com.github.canglan.cm.identity.controller;

import com.github.canglan.cm.common.bean.PageResult;
import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.model.Result;
import com.github.canglan.cm.common.util.StringUtil;
import com.github.canglan.cm.identity.entity.IdRole;

import com.github.canglan.cm.identity.service.IRoleService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.canglan.cm.common.controller.BaseController;

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
public class IdRoleController extends BaseController<IRoleService> {

  @ApiOperation(value = "分页查询")
  @PostMapping(value = "pageIdRole")
  public Result pageIdRole(@ModelAttribute PageWhere pageWhere, @ModelAttribute IdRole idRole) {
    return Result.success(PageResult.of(super.service.pageIdRole(pageWhere, idRole)));
  }


  @ApiOperation(value = "添加")
  @PostMapping(value = "modifyIdRole")
  @PreAuthorize(value = "hasAuthority('idRole:modifyIdRole')")
  public Result modifyIdRole(@ModelAttribute @Valid IdRole idRole) {
    return Result.success(super.service.saveOrUpdateIdRole(idRole));
  }


  @ApiOperation(value = "删除")
  @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
  @PostMapping(value = "removeIdRole")
  @PreAuthorize(value = "hasAuthority('idRole:removeIdRole')")
  public Result removeIdRole(@RequestParam("ids") String ids) {
    boolean status = super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    return Result.newInstance(status);
  }
}
