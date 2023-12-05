package com.murphy.controller.sys;


import java.util.HashMap;
import java.util.List;

import cn.hutool.json.JSONObject;
import com.murphy.common.ErrorCode;
import com.murphy.exception.BusinessException;
import com.murphy.model.dto.user.UserRegisterRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.murphy.constants.ContentConstant;
import com.murphy.common.validatgroup.AddGroup;
import com.murphy.common.validatgroup.UpdateGroup;
import com.murphy.model.common.Result;
import com.murphy.model.entity.sys.SysUser;
import com.murphy.service.sys.SysUserService;
import io.swagger.annotations.ApiOperation;

/**
* @date  2023-12-04 10:20:31
*/
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

	@Autowired
	SysUserService sysUserService;



	@PostMapping("/register")
	@ApiOperation(value = "添加sysUser", notes = "添加sysUser")
	public Result add(@RequestBody @Validated(value = { AddGroup.class }) UserRegisterRequest sysUser) throws Exception {
		if (sysUser == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String userAccount = sysUser.getUserAccount();
		String userPassword = sysUser.getUserPassword();
		String checkPassword = sysUser.getCheckPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword,checkPassword)) {
			return null;
		}
		sysUserService.save(sysUser);
		Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
		ok.put("record", sysUser);
		return ok;
	}

	@PostMapping("/login")
	public JSONObject userLogin(@RequestBody UserRegisterRequest userLoginRequest) {
		if (userLoginRequest == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String userAccount = userLoginRequest.getUserAccount();
		String userPassword = userLoginRequest.getUserPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		JSONObject loginUserVO = sysUserService.userLogin(userAccount, userPassword);
		return loginUserVO;
	}

	@GetMapping("/findById/{id}")
	@ApiOperation(value = "根据id查找", notes = "/sysUser/findById/1")
	public SysUser findById(@PathVariable String id) throws Exception {
		SysUser sysUser = sysUserService.selectByPrimaryKey(id);
		return sysUser;
	}

	@PostMapping("/findAll")
	@ApiOperation(value = "根据条件查找所有", notes = "条件 {'属性':'value'}")
	public List<SysUser> findAll(@RequestBody HashMap<String, Object> condition) throws Exception {
		List<SysUser> list = sysUserService.selectAllByCondition(condition);
		return list;
	}
	
	
	@PostMapping("/findPage/{page}/{size}")
	@ApiOperation(value = "根据条件分页查找", notes = "条件 {'属性':'value'}")
	public PageInfo<SysUser> findPage(@RequestBody HashMap<String, Object> condition,@PathVariable  int page, @PathVariable  int size) throws Exception {
		PageInfo<SysUser> pageInfo = sysUserService.selectPageByCondition(condition, page, size);
		return pageInfo;
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新sysUser", notes = "更新sysUser")
	public Result update(@RequestBody @Validated(value = { UpdateGroup.class }) SysUser sysUser) throws Exception {
		sysUserService.updateByPrimaryKey(sysUser);
		Result ok = Result.ok(ContentConstant.UPDATE_SUCCESS);
		ok.put("record", sysUser);
		return ok;
	}
	
	@DeleteMapping("/deleteById/{id}")
	@ApiOperation(value = "根据主键删除sysUser", notes = "根据主键删除sysUser ")
	public Result deleteById(@PathVariable String id) throws Exception {
		SysUser delete = new SysUser().setId(id).setDelFlag(true);
		sysUserService.updateByPrimaryKey(delete);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		return ok;
	}
	
	@DeleteMapping("/realDeleteById/{id}")
	@ApiOperation(value = "根据主键删除sysUser", notes = "根据主键删除sysUser ")
	public Result realDeleteById(@PathVariable String id) throws Exception {
		int count = sysUserService.deleteByPrimaryKey(id);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
	@PostMapping("/deleteByIds")
	@ApiOperation(value = "根据主键删除sysUser", notes = "根据主键删除sysUser [111,222,333]")
	public Result deleteByIds(@RequestBody List<String> ids) throws Exception {
		int count = sysUserService.deleteByPrimaryKeys(ids);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
}
