package com.github.canglan.cm.identity.controller;

import com.github.canglan.cm.common.bean.PageResult;
import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.controller.BaseController;
import com.github.canglan.cm.common.model.ResultMessage;
import com.github.canglan.cm.common.util.StringUtil;
import com.github.canglan.cm.identity.entity.IdUser;
import com.github.canglan.cm.identity.service.IUserService;
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
public class IdUserController extends BaseController<IUserService> {

  @ApiOperation(value = "分页查询")
  @PostMapping(value = "pageIdUser")
  public ResultMessage pageIdUser(@ModelAttribute PageWhere pageWhere, @ModelAttribute IdUser idUser) {
    return ResultMessage.success(PageResult.of(super.service.pageIdUser(pageWhere, idUser)));
  }

  @ApiOperation(value = "添加")
  @PostMapping(value = "modifyIdUser")
  @PreAuthorize(value = "hasAuthority('idUser:modifyIdUser')")
  public ResultMessage modifyIdUser(@ModelAttribute @Valid IdUser idUser) {
    return ResultMessage.success(super.service.saveOrUpdateIdUser(idUser));
  }

  @ApiOperation(value = "删除")
  @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
  @PostMapping(value = "removeIdUser")
  @PreAuthorize(value = "hasAuthority('idUser:removeIdUser')")
  public ResultMessage removeIdUser(@RequestParam("ids") String ids) {
    boolean status = super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    return ResultMessage.newInstance(status);
  }
}
