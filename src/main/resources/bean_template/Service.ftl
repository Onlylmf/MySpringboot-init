package ${service_package_name};
import java.util.HashMap;
import java.util.List;
import com.github.pagehelper.PageInfo;
import ${entity_package_name}.${entity_name};
import com.murphy.service.BaseService;


/**
 * 
 * @ClassName: ${entity_name}Service 
 * @Description: 实现类 ${entity_name}ServiceImpl
 * @date  ${c_time}
 */
public interface ${entity_name}Service extends BaseService<${entity_name},${key_type}>{
	
    /**
	 *  CACHE_NAME     ${entity_name}
	 */
	public final static String CACHE_NAME = "${entity_name}";
	
	/**
	 * 查询符合条件的 ${entity_name}的个数
	 * @param condition 条件
	 * @return int count
	 * @throws Exception
	 */
	int countByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 删除符合条件的 ${entity_name}
	 * @param condition 条件
	 * @return 成功删除的个数
	 * @throws Exception
	 */
	int deleteByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键删除 ${entity_name}
	 * @param id
	 * @return 成功删除的个数
	 * @throws Exception
	 */
	int deleteByPrimaryKey(${key_type} id) throws Exception;

	/**
	 * 保存 ${entity_name}
	 * @param record
	 * @return  成功保存的个数
	 * @throws Exception
	 */
	int save(${entity_name} record) throws Exception;

	/**
	 * 查询所有符合条件的  ${entity_name}
	 * @param condition 条件
	 * @return  List<${entity_name}>
	 * @throws Exception
	 */
	List<${entity_name}> selectAllByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 分页查询所有符合条件的  ${entity_name}
	 * @param condition 条件 Integer pageNum 从1开始, Integer pageSize
	 * @return  PageInfo<${entity_name}>
	 * @throws Exception
	 */
	PageInfo<${entity_name}> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
			throws Exception;

	/**
	 * 查询所有符合条件的  第一个${entity_name}
	 * @param condition 条件
	 * @return  ${entity_name}  record
	 * @throws Exception
	 */
	${entity_name} selectFirstByCondition(HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键查询 ${entity_name}
	 * @param id
	 * @return  ${entity_name}  record
	 * @throws Exception
	 */
	${entity_name} selectByPrimaryKey(${key_type} id) throws Exception;

	/**
	 * 将所有符合条件的${entity_name} 更新为${entity_name}  record
	 * @param condition
	 * @return  ${entity_name}  record
	 * @throws Exception
	 */
	int updateByCondition(${entity_name} record, HashMap<String, Object> condition) throws Exception;

	/**
	 * 根据主键更新 ${entity_name}
	 * @param record
	 * @return  更新成功的个数
	 * @throws Exception
	 */
	int updateByPrimaryKey(${entity_name} record) throws Exception;

	
	/**
	 * 删除所有主键在List<${key_type}> ids的记录
	 * @param ids
	 * @return  更新成功的个数
	 * @throws Exception
	 */
	int deleteByPrimaryKeys(List<${key_type}> ids) throws Exception;

}
