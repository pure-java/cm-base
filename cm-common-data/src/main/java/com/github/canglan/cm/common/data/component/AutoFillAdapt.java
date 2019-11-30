package com.github.canglan.cm.common.data.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.canglan.cm.cloud.vo.user.UserInfo;
import com.github.canglan.cm.common.data.base.BaseDom;
import com.github.canglan.cm.common.util.date.DateUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * 自动填充字段
 *
 * @author chenhuan
 * @since 2017-07-03 17:55
 **/
@Component
@ConditionalOnMissingBean({AutoFillAdapt.class})
public class AutoFillAdapt implements MetaObjectHandler {

  protected UserInfo getCurrUser() {
    return null;
  }

  @Override
  public void insertFill(MetaObject metaObject) {
    Object originalObject = metaObject.getOriginalObject();
    Class<?> originalObjectClass = originalObject.getClass();
    if (!(originalObject instanceof BaseDom)) {
      throw new RuntimeException(String.format("%s 该对象不支持自动填充 !", originalObjectClass));
    }
    Map<String, Object> fileMap = new HashMap<String, Object>(5);

    UserInfo user = getCurrUser();
    if (user != null) {
      if (user.getOid() != null) {
        fileMap.put("addUserName", user.getUserName());
        fileMap.put("addUserId", user.getOid());
      }
    }
    fileMap.put("addDateTime", DateUtil.newIns().asDate());
    fileMap.put("delFlg", 0);

    metaField(metaObject, fileMap, false);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    Map<String, Object> fileMap = new HashMap<String, Object>(3);
    UserInfo user = getCurrUser();
    if (user != null) {
      if (user.getOid() != null) {
        fileMap.put("optUserName", user.getUserName());
        fileMap.put("optUserId", user.getOid());
      }
    }

    fileMap.put("optDateTime", DateUtil.newIns().asDate());
    metaField(metaObject, fileMap, true);
  }

  /**
   * @param isUpdate 是否是更新操作
   */
  private void metaField(MetaObject metaObject, Map<String, Object> map, boolean isUpdate) {
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = getFieldValByName(key, metaObject);
      if (isUpdate) {
        this.setFieldValByName(key, entry.getValue(), metaObject);
        // 字段值是否为空吗，为空不进行填充
      } else if (value == null) {
        this.setFieldValByName(key, entry.getValue(), metaObject);
      }
    }
  }
}
