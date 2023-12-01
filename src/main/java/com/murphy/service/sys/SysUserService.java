package com.murphy.service.sys;
import java.util.HashMap;
import java.util.List;
import com.github.pagehelper.PageInfo;
import com.murphy.model.sys.SysUser;
import com.murphy.service.BaseService;


/**
 * 
 * @ClassName: SysUserService 
 * @Description: 实现类 SysUserServiceImpl
 * @date  2023-12-01 18:10:24
 */
public interface SysUserService extends BaseService<SysUser,String>{
	
    /**
	 *  CACHE_NAME     SysUser
	 */
	public final static String CACHE_NAME = "SysUser";
	
	/**
	 * 查询符合条件的 SysUser的个数
	 * @param condition 条件
	 * @return int count
	 * @throws Exception
	 */
	int countByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 删除符合条件的 SysUser
	 * @param condition 条件
	 * @return 成功删除的个数
	 * @throws Exception
	 */
	int deleteByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键删除 SysUser
	 * @param id
	 * @return 成功删除的个数
	 * @throws Exception
	 */
	int deleteByPrimaryKey(String id) throws Exception;

	/**
	 * 保存 SysUser
	 * @param record
	 * @return  成功保存的个数
	 * @throws Exception
	 */
	int save(SysUser record) throws Exception;

	/**
	 * 查询所有符合条件的  SysUser
	 * @param condition 条件
	 * @return  List<SysUser>
	 * @throws Exception
	 */
	List<SysUser> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 分页查询所有符合条件的  SysUser
	 * @param condition 条件 Integer pageNum 从1开始, Integer pageSize
	 * @return  PageInfo<SysUser>
	 * @throws Exception
	 */
	PageInfo<SysUser> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
			throws Exception;

	/**
	 * 查询所有符合条件的  第一个SysUser
	 * @param condition 条件
	 * @return  SysUser  record
	 * @throws Exception
	 */
	SysUser selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键查询 SysUser
	 * @param id
	 * @return  SysUser  record
	 * @throws Exception
	 */
	SysUser selectByPrimaryKey(String id) throws Exception;

	/**
	 * 将所有符合条件的SysUser 更新为SysUser  record
	 * @param condition
	 * @return  SysUser  record
	 * @throws Exception
	 */
	int updateByCondition(SysUser record, HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键更新 SysUser
	 * @param record
	 * @return  更新成功的个数
	 * @throws Exception
	 */
	int updateByPrimaryKey(SysUser record) throws Exception;

	
	/**
	 * 删除所有主键在List<String> ids的记录
	 * @param ids
	 * @return  更新成功的个数
	 * @throws Exception
	 */
	int deleteByPrimaryKeys(List<String> ids) throws Exception;

}
