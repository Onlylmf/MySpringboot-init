package com.murphy.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.hutool.json.JSONObject;
import com.murphy.common.ErrorCode;
import com.murphy.exception.BusinessException;
import com.murphy.model.dto.user.UserRegisterRequest;
import com.murphy.model.vo.LoginUserVO;
import com.murphy.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.murphy.utils.SpringContextUtils;
import org.springframework.cache.Cache;
import com.murphy.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import com.murphy.model.entity.sys.SysUser;
import com.murphy.mapper.sys.SysUserMapper;
import com.murphy.service.sys.SysUserService;



/**
* @date  2023-12-04 10:20:31
*/
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService{

	/**
	 * 盐值，混淆密码
	 */
	private static final String SALT = "murphy";
	
    @Autowired
	private SysUserMapper sysUserDao;

	
	
	@Override
	public int countByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return  sysUserDao.selectCountByExample(example);
	}

	@Override
	@CacheEvict(value=CACHE_NAME,allEntries=true)// 清空  缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return  sysUserDao.deleteByExample(example);
	}

	@Override
	@CacheEvict(value =CACHE_NAME, key = "#id") // 清空 缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPrimaryKey(String id) throws Exception {
		
		return  sysUserDao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int save(UserRegisterRequest record) throws Exception {
		String username = record.getUserAccount();
		String password = record.getUserPassword();
		String checkPassword = record.getCheckPassword();
		// 1. 校验
		if (StringUtils.isAnyBlank(username, password)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
		}
		if (username.length() < 4) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
		}
		if (password.length() < 8 || password.length() < 8) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
		}
		// 密码和校验密码相同
		if (!password.equals(checkPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
		}
		synchronized (username.intern()){
			// 账户不能重复
			int countUser = sysUserDao.selectUserCountByUsername(username);
			if (countUser > 0){
				throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
			}
			//密码加密
			String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
			//插入数据
			SysUser sysUser = new SysUser();

			sysUser.setUsername(username).setPassword(encryptPassword).setNickname(username).setCreatetime(new Date())
					.setUpdatetime(new Date());
				int count = sysUserDao.insertSelective(sysUser);

			return count;
		}
	}

	@Override
	public JSONObject userLogin(String userAccount, String userPassword) {
		// 1. 校验
		if (StringUtils.isAnyBlank(userAccount, userPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
		}
		if (userAccount.length() < 4) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
		}
		if (userPassword.length() < 8) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
		}
		// 2. 加密
		String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
		// 查询用户是否存在
		SysUser user = new SysUser();
		user.setUsername(userAccount).setPassword(encryptPassword);
		SysUser sysUser = sysUserDao.selectOne(user);
		// 用户不存在
		if (sysUser == null){
			log.info("user login failed, userAccount cannot match userPassword");
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
		}
		// 3. 记录用户的登录态 生成token
		String token = JWTUtils.createToken(sysUser);
		LoginUserVO loginUserVO = this.getLoginUserVO(sysUser);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user", loginUserVO);
		jsonObject.put("token", token);
		return jsonObject;
	}

	@Override
	public List<SysUser> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);

		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		return sysUserDao.selectByExample(example);
	}

	@Override
	public PageInfo<SysUser> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
			throws Exception {
		Example example = createExample(condition);

		PageHelper.startPage(pageNum, pageSize, true);
		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		List<SysUser> list = sysUserDao.selectByExample(example);
		PageInfo<SysUser> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public SysUser selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		PageHelper.startPage(1, 1, true);
		List<SysUser> list = sysUserDao.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}else {
			return list.get(0);
		}
		
	}

	@Override
	@Cacheable(value =CACHE_NAME, key = "#id", sync=true) 
	public SysUser selectByPrimaryKey(String id) throws Exception {
		return sysUserDao.selectByPrimaryKey(id);
	}

	@Override
	@CacheEvict(value=CACHE_NAME,allEntries=true)// 清空  缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByCondition(SysUser record, HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return sysUserDao.updateByExample(record, example);
	}

	@Override
	@CacheEvict(value =CACHE_NAME, key = "#record.id") // 清除 缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByPrimaryKey(SysUser record) throws Exception {
		return sysUserDao.updateByPrimaryKeySelective(record);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPrimaryKeys(List<String> ids) throws Exception {
		Example example =new Example(SysUser.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", ids);
		Cache cache = SpringContextUtils.getCache(CACHE_NAME);
		((List<String>) ids).forEach((id) -> cache.evict(id));
		return sysUserDao.deleteByExample(example);
	}

	@Override
	public LoginUserVO getLoginUserVO(SysUser user) {
		if (user == null) {
			return null;
		}
		LoginUserVO loginUserVO = new LoginUserVO();
		BeanUtils.copyProperties(user, loginUserVO);
		return loginUserVO;
	}

	private Example createExample(HashMap<String, Object> condition) {
		Example example = new Example(SysUser.class);
		Example.Criteria criteria = example.createCriteria();
		if (condition != null) {
			if (condition.get("id") != null && !"".equals(condition.get("id"))) {
				criteria.andEqualTo("id", condition.get("id"));
			}
			if (condition.get("delFlag") != null && !"".equals(condition.get("delFlag"))) {
				criteria.andEqualTo("delFlag", condition.get("delFlag"));
			}
			if (condition.get("startTime") != null ) {
				criteria.andGreaterThanOrEqualTo("create_time", DateUtils.parse(condition.get("startTime")+""));
				
			}
			if (condition.get("endTime") != null ) {
				criteria.andLessThanOrEqualTo("create_time", DateUtils.parse(condition.get("endTime")+""));
			}
		}
		return example;
	}

	
	

}
