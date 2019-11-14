package com.github.canglan.cm.common.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.canglan.cm.common.entity.BaseDom;
import com.github.canglan.cm.common.util.date.DateUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 自动填充字段
 *
 * @author chenhuan
 * @date 2017-07-03 17:55
 **/
@Component
public class AutoFill implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    Object originalObject = metaObject.getOriginalObject();
    Class<?> originalObjectClass = originalObject.getClass();
    if (!(originalObject instanceof BaseDom)) {
      throw new RuntimeException(String.format("%s 该对象不支持自动填充 !", originalObjectClass));
    }
    Map<String, Object> fileMap = new HashMap<String, Object>(5);

    // SysUser user = UserUtil.getUser();
    // if (user != null) {
    //   if (user.getOid() != null) {
    //     fileMap.put("addUserName", user.getUserName());
    //     fileMap.put("addUserId", user.getOid());
    //   }
    // }
    fileMap.put("addDateTime", DateUtil.newIns().asDate());
    fileMap.put("delFlg", 0);

    // PropertyDescriptor oid = BeanUtils.getPropertyDescriptor(originalObjectClass, "oid");
    // if (oid != null) {
    //   TableId annotation = AnnotationUtils.findAnnotation(originalObjectClass, TableId.class);
    //
    //   if (Objects.isNull(annotation)) {
    //     throw new MyRuntimeException(String.format("%s 该对象的主键未填写 TableId主键 ", originalObjectClass));
    //   }
    //   IdType idType = annotation.type();
    //
    //   Class<?> oidClass = oid.getPropertyType();
    //   if (idType == IdType.UUID) {
    //     if (String.class.getName().equals(oidClass.getName())) {
    //       fileMap.put("oid", IdWorker.get32UUID());
    //     } else {
    //       String format = String.format("%s 该对象的主键类型不正确 ,主键类型为 %s， 应该是字符串", originalObjectClass, oidClass.getName());
    //       throw new MyRuntimeException(format);
    //     }
    //   } else if (idType == IdType.ID_WORKER) {
    //     if (Long.class.getName().equals(oidClass.getName())) {
    //       fileMap.put("oid", IdWorker.getId());
    //     } else {
    //       String format = String.format("%s 该对象的主键类型不正确 ,主键类型为 %s， 应该是Long", originalObjectClass, oidClass.getName());
    //       throw new MyRuntimeException(format);
    //     }
    //   } else if (idType == IdType.ID_WORKER_STR) {
    //     if (String.class.getName().equals(oidClass.getName())) {
    //       fileMap.put("oid", IdWorker.getIdStr());
    //     } else {
    //       String format = String.format("%s 该对象的主键类型不正确 ,主键类型为 %s， 应该是字符串", originalObjectClass, oidClass.getName());
    //       throw new MyRuntimeException(format);
    //     }
    //   } else if (idType == IdType.AUTO) {
    //
    //   } else {
    //     throw new MyRuntimeException(String.format("%s 该对象的主键不支持自动填充 ,主键类型为 %s", originalObjectClass, oidClass.getName()));
    //   }
    // }
    metaField(metaObject, fileMap, false);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    Map<String, Object> fileMap = new HashMap<String, Object>(3);
    // SysUser user = UserUtil.getUser();
    // if (user != null) {
    //   if (user.getOid() != null) {
    //     fileMap.put("optUserName", user.getUserName());
    //     fileMap.put("optUserId", user.getOid());
    //   }
    // }

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
