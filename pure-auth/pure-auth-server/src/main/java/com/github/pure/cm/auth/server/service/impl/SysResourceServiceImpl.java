package com.github.pure.cm.auth.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pure.cm.auth.server.mapper.SysResourceMapper;
import com.github.pure.cm.auth.server.model.entity.SysResource;
import com.github.pure.cm.auth.server.service.SysResourceService;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.util.BusAsserts;
import com.github.pure.cm.common.core.util.StringUtil;
import com.github.pure.cm.common.core.util.collection.CollectUtils;
import com.github.pure.cm.common.data.base.BaseServiceImpl;
import com.github.pure.cm.common.data.constants.DatabaseConstants;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.model.auth.exception.AuthExceptionCode;
import com.github.pure.cm.model.auth.vo.AuthMenuGroupVo;
import com.github.pure.cm.model.auth.vo.AuthMenuItemVo;
import com.github.pure.cm.model.auth.vo.AuthRegisterVo;
import com.github.pure.cm.model.auth.vo.AuthResourceVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenhuan
 * @since 2019-11-20
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {

    @Override
    public List<SysResource> pageIdAuthority(PageWhere pageWhere, SysResource sysResource) {
        QueryWrapper<SysResource> idAuthorityWrapper = new QueryWrapper<>();
        pageWhere.startPage();
        return super.daoUtil.list(idAuthorityWrapper);
    }

    @Override
    @Transactional
    public boolean registerAuth(AuthRegisterVo authRegisterVo) {

        // 保存资源数据的 code 与对应 数据库ID
        Map<String, Long> resourceIdMap = Maps.newHashMap();

        // 菜单组
        CollectUtils
                .stream(authRegisterVo.getAuthMenuGroupVos())
                // 根据key自然排序
                .sorted(Comparator.comparing(AuthMenuGroupVo::getGroupId))
                .forEach(group -> {
                    // 处理根节点，没有父节点
                    SysResource resource = SysResource.builder()
                            .appCode(group.getAppCode())
                            .appName(group.getAppName())
                            .name(group.getGroupName())
                            .code(group.getGroupId())
                            .type(0)
                            .build();
                    registerResource(resourceIdMap, group.getParentId(), resource);
                });

        // 菜单项
        CollectUtils
                .stream(authRegisterVo.getAuthMenuItemVos())
                .sorted(Comparator.comparing(AuthMenuItemVo::getItemId))
                .forEach(itemVo -> {
                    SysResource item = SysResource.builder()
                            .appCode(itemVo.getAppCode())
                            .appName(itemVo.getAppName())
                            .name(itemVo.getName())
                            .code(itemVo.getItemId())
                            .authCode(itemVo.getAuthCode())
                            .type(1)
                            .url(itemVo.getUrl())
                            .build();
                    registerResource(resourceIdMap, itemVo.getParentId(), item);
                });

        // 菜单资源
        CollectUtils
                .stream(authRegisterVo.getAuthResourceVos())
                .sorted(Comparator.comparing(AuthResourceVo::getResourceId))
                .forEach(itemVo -> {
                    SysResource item = SysResource.builder()
                            .appCode(itemVo.getAppCode())
                            .appName(itemVo.getAppName())
                            .name(itemVo.getName())
                            .code(itemVo.getResourceId())
                            .authCode(itemVo.getAuthCode())
                            .type(2)
                            .url(itemVo.getUrl())
                            .build();
                    registerResource(resourceIdMap, itemVo.getParentId(), item);
                });
        return true;
    }

    @Override
    public SysResource byCodeAndAppCode(String appCode, String code) {
        //if (CollectionUtils.size(list.size()) != 1) {
        //    throw new BusinessException(AuthExceptionCode.NOT_FOUND_PARENT);
        //}
        return super.daoUtil.lambdaQuery().eq(SysResource::getAppCode, appCode).eq(SysResource::getCode, code).oneOpt().orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean saveBatch(List<SysResource> saveList) {
        if (CollectionUtils.isNotEmpty(saveList)) {
            List<List<SysResource>> partition = Lists.partition(saveList, DatabaseConstants.BATCH_SAVE_SIZE);
            for (List<SysResource> list : partition) {
                super.baseMapper.saveBatch(list);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean saveOrUpdateIdAuthority(SysResource sysResource) {
        return super.daoUtil.saveOrUpdate(sysResource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean removeByIds(Set<String> idList) {
        return super.daoUtil.removeByIds(idList);
    }

    /**
     * 注册权限资源；根据资源权限码找到原有数据，并与最新上报数据对比，如果不一致则进行更新；如果数据库找不到，则新增权限。
     *
     * @param resourceIdMap 存储权限 code ——> oid
     * @param parentCode    父级权限码
     * @param resource      资源信息
     */
    private void registerResource(Map<String, Long> resourceIdMap, String parentCode, SysResource resource) {
        // 有父节点，查找父节点
        if (StringUtil.isNotBlank(parentCode)) {
            Long parentId = resourceIdMap.get(parentCode);
            if (Objects.isNull(parentId)) {
                SysResource sysResource = this.byCodeAndAppCode(resource.getAppCode(), parentCode);

                // 查找不到父节点
                BusAsserts.nonNull(sysResource, AuthExceptionCode.NOT_FOUND_PARENT, "找不到父级菜单：父级`%s`", parentCode);

                parentId = sysResource.getOid();
                // 将code添加进去
                resourceIdMap.put(parentCode, parentId);
            }
            resource.setParentId(parentId);
        }

        // 查询数据库，找到则，进行修改；如果未找到，则进行添加
        SysResource dbCurrGroup = this.byCodeAndAppCode(resource.getAppCode(), resource.getCode());

        if (resource.resourceEquals(dbCurrGroup)) {
            resource.setOid(dbCurrGroup.getOid());
            this.daoUtil.updateById(resource);
        } else {
            this.daoUtil.save(resource);
        }

        resourceIdMap.put(resource.getCode(), resource.getOid());
    }
}
