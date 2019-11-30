package com.github.canglan.cm.identity.controller;

import com.github.canglan.cm.common.bean.PageResult;
import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.model.Result;
import com.github.canglan.cm.common.util.StringUtil;
import com.github.canglan.cm.identity.entity.IdMenu;

import com.github.canglan.cm.identity.service.IMenuService;
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
@RequestMapping("/identity/idMenu")
public class IdMenuController extends BaseController<IMenuService> {

  @ApiOperation(value = "分页查询")
  @PostMapping(value = "pageIdMenu")
  public Result pageIdMenu(@ModelAttribute PageWhere pageWhere, @ModelAttribute IdMenu idMenu) {
    return Result.success(PageResult.of(super.service.pageIdMenu(pageWhere, idMenu)));
  }

  @ApiOperation(value = "添加")
  @PostMapping(value = "modifyIdMenu")
  @PreAuthorize(value = "hasAuthority('idMenu:modifyIdMenu')")
  public Result modifyIdMenu(@ModelAttribute @Valid IdMenu idMenu) {
    return Result.success(super.service.saveOrUpdateIdMenu(idMenu));
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
