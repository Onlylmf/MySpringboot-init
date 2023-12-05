package com.murphy.controller;

import com.murphy.annotation.AuthCheck;
import com.murphy.constants.UserConstant;
import com.murphy.model.entity.sys.SysUser;
import com.murphy.utils.LoginUserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {


    @RequestMapping("/test")
    public String test(){
        SysUser sysUser = LoginUserUtil.getSysUser();
        return sysUser.toString();
    }

    @RequestMapping("/test1")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public String test1(){
        SysUser sysUser = LoginUserUtil.getSysUser();
        return sysUser.toString();
    }

    @RequestMapping("/test2")
    @AuthCheck(mustRole = UserConstant.DEFAULT_ROLE)
    public String test2(){
        SysUser sysUser = LoginUserUtil.getSysUser();
        return sysUser.toString();
    }
}
