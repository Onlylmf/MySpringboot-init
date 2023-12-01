package ${controller_package_name};


import java.util.HashMap;
import java.util.List;

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
import ${entity_package_name}.${entity_name};
import ${service_package_name}.${entity_name}Service;
import io.swagger.annotations.ApiOperation;

/**
* @date  ${c_time}
*/
@RestController
@RequestMapping("/${model_name}")
public class ${entity_name}Controller {

	@Autowired
	${entity_name}Service ${model_name}Service;

	@GetMapping("/findById/{id}")
	@ApiOperation(value = "根据id查找", notes = "/${model_name}/findById/1")
	public ${entity_name} findById(@PathVariable ${key_type} id) throws Exception {
		${entity_name} ${model_name} = ${model_name}Service.selectByPrimaryKey(id);
		return ${model_name};
	}

	@PostMapping("/findAll")
	@ApiOperation(value = "根据条件查找所有", notes = "条件 {'属性':'value'}")
	public List<${entity_name}> findAll(@RequestBody HashMap<String, Object> condition) throws Exception {
		List<${entity_name}> list = ${model_name}Service.selectAllByCondition(condition);
		return list;
	}
	
	
	@PostMapping("/findPage/{page}/{size}")
	@ApiOperation(value = "根据条件分页查找", notes = "条件 {'属性':'value'}")
	public PageInfo<${entity_name}> findPage(@RequestBody HashMap<String, Object> condition,@PathVariable  int page, @PathVariable  int size) throws Exception {
		PageInfo<${entity_name}> pageInfo = ${model_name}Service.selectPageByCondition(condition, page, size);
		return pageInfo;
	}
	
	@PostMapping("/add")
	@ApiOperation(value = "添加${model_name}", notes = "添加${model_name}")
	public Result add(@RequestBody @Validated(value = { AddGroup.class }) ${entity_name} ${model_name}) throws Exception {
		${model_name}Service.save(${model_name});
		Result ok = Result.ok(ContentConstant.ADD_SUCCESS);
		ok.put("record", ${model_name});
		return ok;
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新${model_name}", notes = "更新${model_name}")
	public Result update(@RequestBody @Validated(value = { UpdateGroup.class }) ${entity_name} ${model_name}) throws Exception {
		${model_name}Service.updateByPrimaryKey(${model_name});
		Result ok = Result.ok(ContentConstant.UPDATE_SUCCESS);
		ok.put("record", ${model_name});
		return ok;
	}
	
	@DeleteMapping("/deleteById/{id}")
	@ApiOperation(value = "根据主键删除${model_name}", notes = "根据主键删除${model_name} ")
	public Result deleteById(@PathVariable ${key_type} id) throws Exception {
		${entity_name} delete = new ${entity_name}().setId(id).setDelFlag(true);
		${model_name}Service.updateByPrimaryKey(delete);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		return ok;
	}
	
	@DeleteMapping("/realDeleteById/{id}")
	@ApiOperation(value = "根据主键删除${model_name}", notes = "根据主键删除${model_name} ")
	public Result realDeleteById(@PathVariable ${key_type} id) throws Exception {
		int count = ${model_name}Service.deleteByPrimaryKey(id);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
	@PostMapping("/deleteByIds")
	@ApiOperation(value = "根据主键删除${model_name}", notes = "根据主键删除${model_name} [111,222,333]")
	public Result deleteByIds(@RequestBody List<${key_type}> ids) throws Exception {
		int count = ${model_name}Service.deleteByPrimaryKeys(ids);
		Result ok = Result.ok(ContentConstant.DELETE_SUCCESS);
		ok.put("count", count);
		return ok;
	}
	
	<#if isTree >
	@PostMapping("/getChildListBatch")
	@ApiOperation(value = "批量获取child", notes = "查找所有pid的child")
	public Result getChildListBatch(@RequestBody List<String> pids) throws Exception {
		HashMap<String, Object> condition=new HashMap<>();
		condition.put("pids", pids);
		List<${entity_name}> list = ${model_name}Service.selectAllByCondition(condition);
		Result ok = Result.ok(ContentConstant.QUERY_SUCCESS);
		ok.put("list", list);
		return ok;
	}
	</#if>
}
