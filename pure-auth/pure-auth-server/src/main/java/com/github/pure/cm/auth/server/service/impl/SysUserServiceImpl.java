package com.github.pure.cm.auth.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pure.cm.auth.server.mapper.SysUserMapper;
import com.github.pure.cm.auth.server.model.entity.SysUser;
import com.github.pure.cm.auth.server.service.ISysUserService;
import com.github.pure.cm.common.data.base.BaseServiceImpl;
import com.github.pure.cm.common.data.constants.DatabaseConstants;
import com.github.pure.cm.common.data.model.PageWhere;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Service
@CacheConfig(cacheNames = "sysUserServiceImpl")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    @Lazy
    private PasswordEncoder encode;

    @Override
    public List<SysUser> pageIdUser(PageWhere pageWhere, SysUser sysUser) {
        QueryWrapper<SysUser> idUserWrapper = new QueryWrapper<>();
        pageWhere.startPage();
        return super.daoUtil.list(idUserWrapper);
    }

    @Override
    @Cacheable(value = "getUserByUserName", key = "#p0")
    public SysUser getUserByUserName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        return super.daoUtil.getOne(queryWrapper);
    }

    @Override
    @Cacheable(value = "getUserByUserName", key = "#p0")
    public SysUser getUserAuthByUserName(String userName) {
        return this.baseMapper.getUserAuthByUserName(userName);
    }


    @Override
    public List<SysUser> listQueryCondition(SysUser query) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        return super.daoUtil.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(List<SysUser> saveList) {
        if (CollectionUtils.isNotEmpty(saveList)) {
            List<List<SysUser>> partition = Lists.partition(saveList, DatabaseConstants.BATCH_SAVE_SIZE);
            for (List<SysUser> list : partition) {
                super.baseMapper.saveBatch(list);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"getUserByUserName"}, key = "#p0.userName")
    public boolean saveOrUpdateIdUser(SysUser sysUser) {
        if (sysUser != null) {
            sysUser.setPassword(encode.encode(sysUser.getPassword()));
        }
        return super.daoUtil.saveOrUpdate(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"getUserByUserName"}, allEntries = true)
    public boolean removeByIds(Set<String> idList) {
        return super.daoUtil.removeByIds(idList);
    }

}
