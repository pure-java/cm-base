package com.github.pure.cm.auth.sdk.core.support;

import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuGroup;
import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuItem;
import com.github.pure.cm.auth.sdk.core.annotation.AuthOption;
import com.github.pure.cm.auth.sdk.core.annotation.AuthResource;
import com.github.pure.cm.auth.sdk.core.annotation.AuthRole;
import com.github.pure.cm.auth.sdk.feign.AuthRegisterClient;
import com.github.pure.cm.auth.sdk.util.AuthUtil;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.model.auth.vo.AuthMenuGroupVo;
import com.github.pure.cm.model.auth.vo.AuthMenuItemVo;
import com.github.pure.cm.model.auth.vo.AuthRegisterVo;
import com.github.pure.cm.model.auth.vo.AuthResourceVo;
import com.github.pure.cm.model.auth.vo.AuthRoleVo;
import com.github.pure.cm.model.auth.vo.AuthVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 根据方法上的权限注解解析权限.<br>
 * 类上的 {@link AuthRole#authCode()} 和 方法上的 {@link AuthOption#authCode()} 来为每一个方法设置权限编码，然后将该权限编码<br>
 * 自动注册到权限中心，并赋予权限
 *
 * @author 陈欢
 * @since 2020/7/7
 */
@Slf4j
public abstract class AuthRegHandlerComponent implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AuthRegisterClient authRegisterClient;
    /**
     * 项目名称
     */
    @Value("${spring.application.name}")
    protected String applicationName;
    /**
     * 项目 code
     */
    @Value("${pure.application.code}")
    protected String applicationCode;

    /**
     * 获取请求映射信息
     *
     * @param authVo
     * @return
     */
    protected abstract Set<RequestMappingVO> getRequestMappingInfo(AuthVo authVo);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        new Thread(() -> {

            log.info("开始注册----------------------");
            AuthVo authVo = new AuthVo();
            Set<RequestMappingVO> parse = getRequestMappingInfo(authVo);
            parse.forEach(vo -> this.fillAuthVo(authVo, vo));

            AuthRegisterVo authRegisterVo = AuthRegisterVo.builder()
                    .authMenuGroupVos(Lists.newArrayList(authVo.getAuthMenuGroupVos().values()))
                    .authMenuItemVos(Lists.newArrayList(authVo.getAuthMenuItemVos().values()))
                    .authResourceVos(Lists.newArrayList(authVo.getAuthResourceVos().values()))
                    .authRoleVos(Lists.newArrayList(authVo.getAuthRoleVos().values()))
                    .serverCode(this.applicationCode)
                    .serverName(this.applicationName)
                    .build();
            Result<Boolean> booleanResult = authRegisterClient.registerAuth(authRegisterVo);
            if (booleanResult.getStatus()) {
                log.info("注册成功!!!");
            } else {
                log.info("注册失败!!!");
            }
        }).start();
    }

    /**
     * 填充要注册的权限信息 authVo
     *
     * @param authVo
     * @param vo     请求
     */
    private void fillAuthVo(AuthVo authVo, RequestMappingVO vo) {
        String methodUrl = vo.getMethodUrl();
        Class<?> beanType = vo.getBeanType();
        Method method = vo.getMethod();

        Set<String> securityCodes = Sets.newHashSet();
        {
            // 类上的角色注解
            AuthRole classAuthRole = beanType.getAnnotation(AuthRole.class);
            if (Objects.nonNull(classAuthRole)) {
                // 自动添加ROLE_前缀
                String roleAuthority = AuthUtil.convertRoleCode(classAuthRole.authCode());

                authVo.getAuthRoleVos()
                        .computeIfAbsent(roleAuthority,
                                key -> AuthRoleVo.builder()
                                        .serverName(this.applicationName)
                                        .serverCode(this.applicationCode)
                                        .authCode(AuthUtil.convertAuthCode(roleAuthority))
                                        .name(classAuthRole.name())
                                        .build()
                        );
                securityCodes.add(roleAuthority);
            }
        }

        // 解析
        {
            AuthOption authOption = method.getAnnotation(AuthOption.class);
            if (Objects.isNull(authOption)) {
                return;
            }

            securityCodes.add(AuthUtil.convertAuthCode(authOption.authCode()));

            // 处理权限组
            AuthMenuGroup[] groups = authOption.menuGroup();
            if (ArrayUtils.isNotEmpty(groups)) {
                for (AuthMenuGroup group : groups) {
                    putAuthMenuGroup(authVo.getAuthMenuGroupVos(), group);
                }
            }

            // 处理权限项
            AuthMenuItem[] items = authOption.menuItems();
            if (ArrayUtils.isNotEmpty(items)) {
                for (AuthMenuItem authMenuItem : items) {
                    putAuthMenuItem(authVo.getAuthMenuItemVos(), methodUrl, securityCodes, authMenuItem);
                }
            }

            // 处理资源
            AuthResource[] resources = authOption.resources();
            if (ArrayUtils.isNotEmpty(resources)) {
                for (AuthResource resource : resources) {
                    putAuthResource(authVo.getAuthResourceVos(), methodUrl, securityCodes, resource);
                }
            }
        }
        log.info("{}", JsonUtil.json(authVo));
    }

    /**
     * 添加菜单组权限
     */
    protected void putAuthMenuGroup(Map<String, AuthMenuGroupVo> authMenuGroupVos, AuthMenuGroup group) {
        String groupCode = AuthUtil.convertAuthCode(group.groupCode());

        Assert.state(!authMenuGroupVos.containsKey(groupCode), String.format("@AuthMenuGroup.code()重复:`%s`", groupCode));

        authMenuGroupVos.put(groupCode,
                AuthMenuGroupVo.builder()
                        .appName(this.applicationName)
                        .appCode(this.applicationCode)
                        //.authRoleVos()
                        .groupId(groupCode)
                        .groupName(group.groupName())
                        .parentId(AuthUtil.convertAuthCode(group.parentGroupCode()))
                        .build());
    }

    /**
     * 添加权限菜单项
     *
     * @param authMenuItemVos 存储菜单项 map
     * @param methodUrl       方法url
     * @param optionAuthCode  操作码
     * @param authMenuItem    菜单项
     */
    private void putAuthMenuItem(Map<String, AuthMenuItemVo> authMenuItemVos, String methodUrl, Set<String> optionAuthCode, AuthMenuItem authMenuItem) {
        String menuCode = AuthUtil.convertAuthCode(authMenuItem.itemCode());

        Assert.state(!authMenuItemVos.containsKey(menuCode), String.format("@AuthMenuItem.code()重复:`%s`", menuCode));

        authMenuItemVos.put(menuCode,
                AuthMenuItemVo.builder()
                        .appName(this.applicationName)
                        .appCode(this.applicationCode)
                        // 拼接上 optionAuthCode
                        .itemId(menuCode)
                        .name(authMenuItem.name())
                        .authCode(StringUtils.join(optionAuthCode, ";"))
                        .parentId(AuthUtil.convertAuthCode(authMenuItem.parentCode()))
                        .url(methodUrl)
                        .build());
    }

    /**
     * 添加权限资源项
     *
     * @param authResourceVos 存储资源项 map
     * @param methodUrl       url
     * @param optionAuthCode
     * @param resource        资源
     */
    private void putAuthResource(Map<String, AuthResourceVo> authResourceVos, String methodUrl, Set<String> optionAuthCode, AuthResource resource) {
        String resourceCode = AuthUtil.convertAuthCode(resource.resCode());

        Assert.state(!authResourceVos.containsKey(resourceCode), String.format("@AuthResource.code()重复:`%s`", resourceCode));

        authResourceVos.put(resourceCode, AuthResourceVo.builder()
                .appName(this.applicationName)
                .appCode(this.applicationCode)
                // 拼接上 optionAuthCode
                .resourceId(resourceCode)
                .name(resource.name())
                .authCode(StringUtils.join(optionAuthCode, ";"))
                .parentId(AuthUtil.convertAuthCode(resource.menuItemCode()))
                .url(methodUrl)
                .build());
    }

    @Data
    @Builder
    public static class RequestMappingVO {
        /**
         * 请求url
         */
        private String methodUrl;

        /**
         * 请求类
         */
        private Class<?> beanType;

        /**
         * 方法
         */
        private Method method;
    }
}
