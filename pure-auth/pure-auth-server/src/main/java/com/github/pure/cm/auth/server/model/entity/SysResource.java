package com.github.pure.cm.auth.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pure.cm.common.data.base.domain.BaseDomDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * @author 陈欢
 * @since 2018-03-01 16:36
 **/
@Data
@TableName("sys_authority")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class SysResource extends BaseDomDate<Long, SysResource> {


    /**
     * app code
     */
    private String appCode;
    /**
     * app 名称
     */
    private String appName;
    /**
     * 父节点ID
     */
    private Long parentId;

    /**
     * 权限名称
     */
    private String name;
    /**
     * code
     */
    private String code;
    /**
     * 权限code
     */
    private String authCode;
    /**
     * 序号
     */
    private Integer orderNum;

    /**
     * url
     */
    private String url;

    /**
     * 类型：0 - menuGroup ，1 - menuItem， 2 resource
     */
    private Integer type;

    /**
     * 权限描述
     */
    private String remark;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysResource)) return false;
        if (!super.equals(o)) return false;
        SysResource that = (SysResource) o;
        return Objects.equals(getAppCode(), that.getAppCode()) &&
                Objects.equals(getAppName(), that.getAppName()) &&
                Objects.equals(getParentId(), that.getParentId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getUrl(), that.getUrl()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAppCode(), getAppName(), getParentId(), getName(), getCode(), getUrl(), getType());
    }
}