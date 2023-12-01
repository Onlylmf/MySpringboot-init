package com.murphy.generator;



import com.murphy.utils.CodeGenUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GeneratorBean {

	
	private static DataSource dataSource;

	
	@BeforeAll
	static void before_bean() throws Exception {
		HikariConfig configuration =new HikariConfig();
		configuration.setJdbcUrl("jdbc:mysql://localhost:3306/my_dba?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false");
		configuration.setUsername("root");
		configuration.setPassword("root");
		dataSource=new HikariDataSource(configuration );
	}

	@Test
	public void generatorAll() throws Exception {
		String entityPackageName = "com.murphy.model.sys";
		String daoPackageName = "com.murphy.mapper.sys";
		String servicePackageName = "com.murphy.service.sys";
		String controllerPackageName = "com.murphy.controller.sys";
//		String servicePackageName = "";
//		String controllerPackageName = "";
//		String queryTimeFiled=null;
		String queryTimeFiled="create_time";
		String tableName = "sys_user";
		CodeGenUtil.generatorAll(tableName, queryTimeFiled,entityPackageName, daoPackageName, servicePackageName,controllerPackageName, dataSource);
		
	} 
	
	@Test
	public void generatorHtml() throws Exception {
		//String dir = "C:\\Users\\Administrator\\Desktop\\temp_code\\sys_user";
		String dir = "D:\\refund_record";
		String tableName = "book";
		String nameFiled="";//名字字段 用于树形
		Map<String, Boolean> queryField=new HashMap<>();
		queryField.put("customer_id", true);
		//queryField.put("refund_time", true);
		//queryField.put("state", true);
		String orderBy="release_time desc";
		boolean isTree = false; //最重要
		CodeGenUtil.generatorHtml(tableName, dir, queryField, orderBy, dataSource,isTree,nameFiled);
	} 
	@Test
	public void generatorEntity() throws Exception {
		String packageName = "com.murphy.model.sys";
		String tableName = "sys_user";
		CodeGenUtil.generatorEntity(tableName, packageName, dataSource);
	} 
	@Test
	public void generatorMapper() throws Exception {
		String repositoryPackageName = "com.murphy.mapper.sys";
		String keyType="String";
		String entityPackageName="com.murphy.model.sys";
		String entityName="SysUser";
		CodeGenUtil.generatorMapper(repositoryPackageName, entityPackageName, entityName, keyType);
	} 
	@Test
	public void generatorService() throws Exception {
		String repositoryPackageName = "com.murphy.repository.sys";
		String servicePackageName = "com.murphy.service.sys";
		String keyType="String";
		String entityPackageName="com.murphy.entity.sys";
		String entityName="SysDept";
		String keyName="deptId";
		//CodeGenUtil.generatorService(servicePackageName,repositoryPackageName, entityPackageName, entityName, keyType,keyName);
	} 
	
	@Test
	public void generatorController() throws Exception {
		String controllerPackageName = "com.murphy.controller.sys";
		String servicePackageName = "com.murphy.service.sys";
		String entityPackageName="com.murphy.model.sys";
		String entityName="SysRole";
		//CodeGenUtil.generatorController(controllerPackageName,servicePackageName, entityPackageName, entityName,"Long");
		
	} 
	
}
