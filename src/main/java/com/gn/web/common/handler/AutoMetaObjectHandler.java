package com.gn.web.common.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gn.web.common.utils.SecurityUtils;
import com.gn.web.sys.entity.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AutoMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime localDateTime=LocalDateTime.now();
        boolean bol1 = metaObject.hasSetter("createTime");
        if(bol1){
          Object createTime = this.getFieldValByName("createTime",metaObject);
          if(createTime == null){
              this.setFieldValByName("createTime",localDateTime,metaObject);
          }
        }
        boolean bol2 = metaObject.hasSetter("updateTime");
        if(bol2){
            Object createTime = this.getFieldValByName("updateTime",metaObject);
            if(createTime == null){
                this.setFieldValByName("updateTime",localDateTime,metaObject);
            }
        }
        boolean bol3 = metaObject.hasSetter("status");
        if(bol3){
            Object createTime = this.getFieldValByName("status",metaObject);
            if(createTime == null){
                this.setFieldValByName("status","0",metaObject);
            }
        }
        boolean bol4 = metaObject.hasSetter("createUserName");
        if(bol4){
            Object createTime = this.getFieldValByName("createUserName",metaObject);
            if(createTime == null){
                SysUser sysUser= SecurityUtils.getLoginUser();
                if(sysUser != null) {
                    this.setFieldValByName("createUserName", sysUser.getUserName(), metaObject);
                }else {
                    this.setFieldValByName("createUserName", "system", metaObject);
                }
            }
        }
        boolean bol5 = metaObject.hasSetter("updateUserName");
        if(bol5){
            Object createTime = this.getFieldValByName("updateUserName",metaObject);
            if(createTime == null){
                SysUser sysUser=SecurityUtils.getLoginUser();
                if(sysUser != null) {
                    this.setFieldValByName("updateUserName", sysUser.getUserName(), metaObject);
                }else {
                    this.setFieldValByName("updateUserName", "system", metaObject);
                }
            }
        }
        boolean bol6 = metaObject.hasSetter("createUserId");
        if(bol6){
            Object createTime = this.getFieldValByName("createUserId",metaObject);
            if(createTime == null){
                SysUser sysUser=SecurityUtils.getLoginUser();
                if(sysUser != null) {
                    this.setFieldValByName("createUserId", String.valueOf(sysUser.getUserId()), metaObject);
                }else {
                    this.setFieldValByName("createUserId", "-1", metaObject);
                }
            }
        }
        boolean bol7 = metaObject.hasSetter("updateUserId");
        if(bol7){
            Object createTime = this.getFieldValByName("updateUserId",metaObject);
            if(createTime == null){
                SysUser sysUser=SecurityUtils.getLoginUser();
                if(sysUser != null) {
                    this.setFieldValByName("updateUserId", String.valueOf(sysUser.getUserId()), metaObject);
                }else {
                    this.setFieldValByName("updateUserId", "-1", metaObject);
                }
            }
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime localDateTime=LocalDateTime.now();
        boolean bol1 = metaObject.hasSetter("updateTime");
        if(bol1){
            Object createTime = this.getFieldValByName("updateTime",metaObject);
            if(createTime == null){
                this.setFieldValByName("updateTime",localDateTime,metaObject);
            }
        }
        boolean bol2 = metaObject.hasSetter("updateUserName");
        if(bol2){
            Object createTime = this.getFieldValByName("updateUserName",metaObject);
            if(createTime == null){
                SysUser sysUser=SecurityUtils.getLoginUser();
                if(sysUser != null) {
                    this.setFieldValByName("updateUserName", sysUser.getUserName(), metaObject);
                }else {
                    this.setFieldValByName("updateUserName", "system", metaObject);
                }
            }
        }
        boolean bol3 = metaObject.hasSetter("updateUserId");
        if(bol3){
            Object createTime = this.getFieldValByName("updateUserId",metaObject);
            if(createTime == null){
                SysUser sysUser=SecurityUtils.getLoginUser();
                if(sysUser != null) {
                    this.setFieldValByName("updateUserId", String.valueOf(sysUser.getUserId()), metaObject);
                }else {
                    this.setFieldValByName("updateUserId", "-1", metaObject);
                }
            }
        }

    }
}
