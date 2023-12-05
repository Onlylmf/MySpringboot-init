package com.murphy.config.interceptor;


import com.fasterxml.jackson.annotation.JsonInclude;

//import com.murphy.model.sys.SysUser;
import com.murphy.model.entity.sys.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class Mycontext {
	private SysUser sysUser;

}
