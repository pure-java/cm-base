package com.github.canglan.cm.identity.controller;

import com.github.canglan.cm.common.bean.PageResult;
import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.model.ResultMessage;
import com.github.canglan.cm.common.util.StringUtil;
import com.github.canglan.cm.identity.service.IAuthorityService;
import com.github.canglan.cm.identity.entity.IdAuthority;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.Mapping;
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
 * @since 2019-11-20
 */
@Api(tags = "")
@RestController
@RequestMapping("/identity/idAuthority")
public class IdAuthorityController extends BaseController<IAuthorityService> {


  @ApiOperation(value = "分页查询")
  @PostMapping(value = "pageIdAuthority")
  public ResultMessage pageIdAuthority(@ModelAttribute PageWhere pageWhere, @ModelAttribute IdAuthority idAuthority) {
    return ResultMessage.success(PageResult.of(super.service.pageIdAuthority(pageWhere, idAuthority)));
  }


  @ApiOperation(value = "添加")
  @PostMapping(value = "modifyIdAuthority")
  // @PreAuthorize(value = "hasAuthority('idAuthority:modifyIdAuthority')")
  public ResultMessage modifyIdAuthority(@ModelAttribute @Valid IdAuthority idAuthority) {
    return ResultMessage.success(super.service.saveOrUpdateIdAuthority(idAuthority));
  }

  @ApiOperation(value = "删除")
  @ApiImplicitParam(name = "ids", value = "要进行删除的id字符串，使用逗号分隔")
  @PostMapping(value = "removeIdAuthority")
  @PreAuthorize(value = "hasAuthority('idAuthority:removeIdAuthority')")
  public ResultMessage removeIdAuthority(@RequestParam("ids") String ids) {
    boolean status = super.service.removeByIds(StringUtil.convertStringToId(ids, String.class));
    return ResultMessage.newInstance(status);
  }
}
