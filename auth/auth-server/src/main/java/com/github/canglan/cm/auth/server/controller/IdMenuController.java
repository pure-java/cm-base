package com.github.canglan.cm.auth.server.controller;

import com.github.canglan.cm.auth.server.entity.IdMenu;
import com.github.canglan.cm.auth.server.service.IMenuService;
import com.github.canglan.cm.common.bean.PageResult;
import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.controller.BaseController;
import com.github.canglan.cm.common.model.ResultMessage;
import com.github.canglan.cm.common.util.StringUtil;
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
public class IdMenuController extends BaseController<IMenuService> {

  @ApiOperation(value = "分页查询")
  @PostMapping(value = "pageIdMenu")
  public ResultMessage pageIdMenu(@ModelAttribute PageWhere pageWhere, @ModelAttribute IdMenu idMenu) {
    return ResultMessage.success(PageResult.of(super.service.pageIdMenu(pageWhere, idMenu)));
  }

  @ApiOperation(value = "添加")
  @PostMapping(value = "modifyIdMenu")
  @PreAuthorize(value = "hasAuthority('idMenu:modifyIdMenu')")
  public ResultMessage modifyIdMenu(@ModelAttribute @Valid IdMenu idMenu) {
    return ResultMessage.success(super.service.saveOrUpdateIdMenu(idMenu));
  }

  @ApiOperation(value = "删除")
  @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
  @PostMapping(value = "removeIdMenu")
  @PreAuthorize(value = "hasAuthority('idMenu:removeIdMenu')")
  public ResultMessage removeIdMenu(@RequestParam("ids") String ids) {
    boolean status = super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    return ResultMessage.newInstance(status);
  }
}
