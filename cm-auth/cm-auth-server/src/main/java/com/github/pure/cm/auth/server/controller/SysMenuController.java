package com.github.pure.cm.auth.server.controller;

import com.github.pure.cm.auth.server.model.entity.SysMenu;
import com.github.pure.cm.auth.server.service.ISysMenuService;
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
@RequestMapping("/identity/idMenu")
public class SysMenuController extends BaseController<ISysMenuService> {

  @ApiOperation(value = "分页查询")
  @PostMapping(value = "pageIdMenu")
  public Result pageIdMenu(@ModelAttribute PageWhere pageWhere, @ModelAttribute SysMenu sysMenu) {
    return Result.success(PageResult.of(super.service.pageIdMenu(pageWhere, sysMenu)));
  }

  @ApiOperation(value = "添加")
  @PostMapping(value = "modifyIdMenu")
  @PreAuthorize(value = "hasAuthority('idMenu:modifyIdMenu')")
  public Result modifyIdMenu(@ModelAttribute @Valid SysMenu sysMenu) {
    return Result.success(super.service.saveOrUpdateIdMenu(sysMenu));
  }

  @ApiOperation(value = "删除")
  @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
  @PostMapping(value = "removeIdMenu")
  @PreAuthorize(value = "hasAuthority('idMenu:removeIdMenu')")
  public Result removeIdMenu(@RequestParam("ids") String ids) {
    boolean status = super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    return Result.newInstance(status);
  }
}
