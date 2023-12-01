package ${service_package_name}.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.murphy.constants.ConfigConstant;
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
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import ${entity_package_name}.${entity_name};
import ${rep_package_name}.${entity_name}Mapper;
import ${service_package_name}.${entity_name}Service;





/**
* @date  ${c_time}
*/
@Service
public class ${entity_name}ServiceImpl implements ${entity_name}Service{
	
	
    @Autowired
	private ${entity_name}Mapper ${model_name}Dao;

	
	
	@Override
	public int countByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return  ${model_name}Dao.selectCountByExample(example);
	}

	@Override
	@CacheEvict(value=CACHE_NAME,allEntries=true)// 清空  缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return  ${model_name}Dao.deleteByExample(example);
	}

	@Override
	@CacheEvict(value =CACHE_NAME, key = "#id") // 清空 缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPrimaryKey(${key_type} id) throws Exception {
		
		return  ${model_name}Dao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int save(${entity_name} record) throws Exception {
		
		return ${model_name}Dao.insertSelective(record);
	}

	@Override
	public List<${entity_name}> selectAllByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);

		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		return ${model_name}Dao.selectByExample(example);
	}

	@Override
	public PageInfo<${entity_name}> selectPageByCondition(HashMap<String, Object> condition, Integer pageNum, Integer pageSize)
			throws Exception {
		Example example = createExample(condition);

		PageHelper.startPage(pageNum, pageSize, true);
		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		List<${entity_name}> list = ${model_name}Dao.selectByExample(example);
		PageInfo<${entity_name}> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public ${entity_name} selectFirstByCondition(HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		String orderByClause = (String) condition.get("orderByClause");
		if(orderByClause!=null) {
			example.setOrderByClause(orderByClause);
		}
		PageHelper.startPage(1, 1, true);
		List<${entity_name}> list = ${model_name}Dao.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}else {
			return list.get(0);
		}
		
	}

	@Override
	@Cacheable(value =CACHE_NAME, key = "#id", sync=true) 
	public ${entity_name} selectByPrimaryKey(${key_type} id) throws Exception {
		return ${model_name}Dao.selectByPrimaryKey(id);
	}

	@Override
	@CacheEvict(value=CACHE_NAME,allEntries=true)// 清空  缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByCondition(${entity_name} record, HashMap<String, Object> condition) throws Exception {
		Example example = createExample(condition);
		return ${model_name}Dao.updateByExample(record, example);
	}

	@Override
	@CacheEvict(value =CACHE_NAME, key = "#record.${key_name}") // 清除 缓存
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByPrimaryKey(${entity_name} record) throws Exception {
		return ${model_name}Dao.updateByPrimaryKeySelective(record);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteByPrimaryKeys(List<${key_type}> ids) throws Exception {
		Example example =new Example(${entity_name}.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", ids);
		Cache cache = SpringContextUtils.getCache(CACHE_NAME);
		((List<${key_type}>) ids).forEach((id) -> cache.evict(id));
		return ${model_name}Dao.deleteByExample(example);
	}

	private Example createExample(HashMap<String, Object> condition) {
		Example example = new Example(${entity_name}.class);
		Example.Criteria criteria = example.createCriteria();
		if (condition != null) {
			if (condition.get("id") != null && !"".equals(condition.get("id"))) {
				criteria.andEqualTo("id", condition.get("id"));
			}
			<#if hasDelFlag >
			if (condition.get("delFlag") != null && !"".equals(condition.get("delFlag"))) {
				criteria.andEqualTo("delFlag", condition.get("delFlag"));
			}
			if (condition.get("startTime") != null ) {
				criteria.andGreaterThanOrEqualTo("${queryTimeFiled}", DateUtils.parse(condition.get("startTime")+""));
				
			}
			if (condition.get("endTime") != null ) {
				criteria.andLessThanOrEqualTo("${queryTimeFiled}", DateUtils.parse(condition.get("endTime")+""));
			}
			</#if>
			<#if isTree >
			if (condition.get("level") != null && !"".equals(condition.get("level"))) {
				criteria.andEqualTo("level", condition.get("level"));
			}
			if (condition.get("pids") != null ) {
				criteria.andIn("parentId", (List)condition.get("pids"));
			}
			</#if>
		}
		return example;
	}

	
	

}
