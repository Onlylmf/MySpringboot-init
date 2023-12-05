package com.murphy.utils;


import com.murphy.config.interceptor.MyContextHolder;
import com.murphy.model.entity.sys.SysUser;

public class LoginUserUtil {


	public static SysUser getSysUser() {
		return MyContextHolder.getContext().getSysUser();
	}

	

	
}
