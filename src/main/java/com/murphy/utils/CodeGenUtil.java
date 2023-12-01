package com.murphy.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;


import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Velocity工具类
 */
public class CodeGenUtil {
	
	/**
	 * 生成entity
	 * @param tableName
	 * @param packageName
	 * @throws Exception 
	 */
	public static Map<String, Object> generatorEntity(String tableName ,String packageName,DataSource dataSource) throws Exception {
		
		String basePath = System.getProperty("user.dir");
		String tempPath =basePath+"\\src\\main\\resources\\bean_template";
		String beanPath =basePath+"\\src\\main\\java\\";
		String replaceAll = packageName.replaceAll("\\.", "\\\\");
		beanPath=beanPath+replaceAll;
		if(StringUtils.isBlank(tableName)||StringUtils.isBlank(packageName)) {
			return null; 
		}
		
		String beanName =StringUtils.toCapitalizeCamelCase(tableName);
		boolean isTree=false;
		List<Map<String, String>> colList = new ArrayList<Map<String, String>>(); // 存储字段信息
		Connection connection = dataSource.getConnection();
		Statement st = connection.createStatement();
		String keyType="";
		String keyName="";
		ResultSet columnRs = st.executeQuery("show full columns from " + tableName);
		while (columnRs.next()) {
			Map<String, String> colMap = new HashMap<String, String>();
			String column = columnRs.getString(1);//列名
			colMap.put("column", column);//列名
			colMap.put("filed", StringUtils.toCamelCase(column));//字段名
			String database_type = columnRs.getString(2);
			
			colMap.put("database_type", database_type);//数据库类型
			String filedType = StringUtils.typeTrans(columnRs.getString(2));
			
			colMap.put("bean_type",filedType);//字段类型
			String key = columnRs.getString(5);
			
			if(key.equals("PRI")&&StringUtils.isBlank(keyType)) {
				keyType=filedType;
				keyName=StringUtils.toCamelCase(column);
			}
			colMap.put("isId", key);
			String comment = columnRs.getString(9);
			String[] split = comment.split("@");
			colMap.put("comment",split[0]);
			if(split.length==3) {
				colMap.put("comment",split[0]+split[2]);
			}
			colMap.put("nullable",columnRs.getString(4));
			if(colMap.get("filed").equals("hasChild")){
				isTree=true;
			}
			colList.add(colMap);
		}
			
		//第一步:实例化Freemarker的配置类
		Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

		Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(tempPath));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("table_name", tableName);
            dataMap.put("entity_package_name", packageName);
            dataMap.put("key_type", keyType);
            dataMap.put("key_name", keyName);
            dataMap.put("bean_name", beanName);
            dataMap.put("col_List", colList);
            dataMap.put("c_time", DateUtils.now());
            dataMap.put("isTree", isTree);
            dataMap.put("hasDelFlag", true);
            // step4 加载模版文件
            Template template = configuration.getTemplate("Bean.ftl");
            // step5 生成数据
            //判断文件夹是否存在
            File docFile = new File( beanPath+"\\" + beanName+".java");
            FileOutputStream fos = FileUtils.openOutputStream(docFile);
            out = new BufferedWriter(new OutputStreamWriter(fos));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println(beanName+"已经生成完成  位置 在 "+beanPath);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		return null; 
	 
	}
	/**
	 * 生成repository
	 * @param repositoryPackageName  生成到哪里 
	 * @param entityPackageName  entity所在的包  用于导入包
	 * @param entityName 对应的 entity名字
	 * @param keyType entity的主键类型
	 * 
	 */
	public static Map<String, Object> generatorMapper(String repositoryPackageName,String entityPackageName,String entityName,String keyType){
		String basePath = System.getProperty("user.dir");
		String tempPath =basePath+"\\src\\main\\resources\\bean_template";
		String beanPath =basePath+"\\src\\main\\java\\";
		String replaceAll = repositoryPackageName.replaceAll("\\.", "\\\\");
		beanPath=beanPath+replaceAll;
		if(StringUtils.isBlank(keyType)) {
			keyType="Long";
		}
		if(StringUtils.isBlank(repositoryPackageName)||StringUtils.isBlank(entityPackageName)||StringUtils.isBlank(entityName)) {
			return null;
		}
				//第一步:实例化Freemarker的配置类
				Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

				Writer out = null;
		        try {
		            // step2 获取模版路径
		            configuration.setDirectoryForTemplateLoading(new File(tempPath));
		            // step3 创建数据模型
		            Map<String, Object> dataMap = new HashMap<String, Object>();
		            dataMap.put("rep_package_name", repositoryPackageName);
		            dataMap.put("entity_package_name", entityPackageName);
		            dataMap.put("key_type", keyType);
		            dataMap.put("entity_name", entityName);
		            dataMap.put("c_time", DateUtils.now());
		            // step4 加载模版文件
		            Template template = configuration.getTemplate("Mapper.ftl");
		            // step5 生成数据
		            //判断文件夹是否存在
		            File docFile = new File( beanPath+"\\" + entityName+"Mapper.java");
		            FileOutputStream fos =FileUtils.openOutputStream(docFile); 
		            out = new BufferedWriter(new OutputStreamWriter(fos));
		            // step6 输出文件
		            template.process(dataMap, out);
		            System.out.println(entityName+"Mapper"+"已经生成完成  位置 在 "+beanPath);
		            return dataMap;
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (null != out) {
		                    out.flush();
		                    out.close();
		                }
		            } catch (Exception e2) {
		                e2.printStackTrace();
		            }
		        }
		return null;
		
	}
	/**
	 * 
	 * @Title: 产生mongoservice
	 *@param servicePackageName 生成的文件放在哪个包下
	 *@param entityName  entity的全限定名
	 *@return : Map<String,Object>
	 */
	public static Map<String, Object> generatorMongoService(String servicePackageName,String fullentityName){
		
		String basePath = System.getProperty("user.dir");
		String tempPath =basePath+"\\src\\main\\resources\\bean_template";
		String beanPath =basePath+"\\src\\main\\java\\";
		String replaceAll = servicePackageName.replaceAll("\\.", "\\\\");
		beanPath=beanPath+replaceAll;
		if(StringUtils.isBlank(servicePackageName)||StringUtils.isBlank(fullentityName)) {
			return null;
		}
		//第一步:实例化Freemarker的配置类
		Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

		Writer out = null;
		Writer outImpl = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(tempPath));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("service_package_name", servicePackageName);
            
            dataMap.put("full_entity_name", fullentityName);
            String entityName =fullentityName.substring(fullentityName.lastIndexOf(".")+1, fullentityName.length());
            dataMap.put("entity_name", entityName);
            dataMap.put("model_name", StringUtils.toLowerCaseFirstOne(entityName));
            dataMap.put("c_time", DateUtils.now());
            // step4 加载模版文件
            Template templateService = configuration.getTemplate("MongoService.ftl");
            Template templateServiceImpl = configuration.getTemplate("MongoServiceImpl.ftl");
            // step5 生成数据
            //判断文件夹是否存在
            File docFile = new File( beanPath+"\\" + entityName+"Service.java");
            File docFileImpl = new File( beanPath+"\\" + entityName+"ServiceImpl.java");
            FileOutputStream fos =FileUtils.openOutputStream(docFile); 
            FileOutputStream fosImpl =FileUtils.openOutputStream(docFileImpl); 
            out = new BufferedWriter(new OutputStreamWriter(fos));
            outImpl = new BufferedWriter(new OutputStreamWriter(fosImpl));
            // step6 输出文件
            templateService.process(dataMap, out);
            templateServiceImpl.process(dataMap, outImpl);
            System.out.println(entityName+"Service"+"已经生成完成  位置 在 "+beanPath);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
                if (null != outImpl) {
                	outImpl.flush();
                	outImpl.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		return null;
	}
	/**
	 * 生成 service 和serviceImpl
	 * @param queryTimeFiled
	 * @param servicePackageName  
	 * @param repositoryPackageName
	 * @param entityPackageName
	 * @param entityName 
	 * @param keyType
	 * @return
	 */
	public static Map<String, Object> generatorService(String queryTimeFiled,String servicePackageName,String repositoryPackageName,
			String entityPackageName,String entityName,String keyType,String keyName,boolean isTree,boolean hasDelFlag){
		String basePath = System.getProperty("user.dir");
		String tempPath =basePath+"\\src\\main\\resources\\bean_template";
		String beanPath =basePath+"\\src\\main\\java\\";
		String replaceAll = servicePackageName.replaceAll("\\.", "\\\\");
		beanPath=beanPath+replaceAll;
		String implPath=beanPath+"\\impl\\";
		if(StringUtils.isBlank(keyType)) {
			keyType="Long";
		}
		if(StringUtils.isBlank(servicePackageName)||StringUtils.isBlank(repositoryPackageName)||StringUtils.isBlank(entityPackageName)||StringUtils.isBlank(entityName)||StringUtils.isBlank(keyType)) {
			return null;
		}
		//第一步:实例化Freemarker的配置类
		Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

		Writer out = null;
		Writer outImpl = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(tempPath));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("service_package_name", servicePackageName);
            dataMap.put("rep_package_name", repositoryPackageName);
            dataMap.put("entity_package_name", entityPackageName);
            dataMap.put("key_type", keyType);
            dataMap.put("key_name", keyName);
            dataMap.put("isTree", isTree);
            dataMap.put("hasDelFlag", hasDelFlag);
            dataMap.put("entity_name", entityName);
            dataMap.put("model_name", StringUtils.toLowerCaseFirstOne(entityName));
            dataMap.put("c_time", DateUtils.now());
            dataMap.put("queryTimeFiled", queryTimeFiled);
            // step4 加载模版文件
            Template templateService = configuration.getTemplate("Service.ftl");
            Template templateServiceImpl = configuration.getTemplate("ServiceImpl.ftl");
            // step5 生成数据
            //判断文件夹是否存在
            File docFile = new File( beanPath+"\\" + entityName+"Service.java");
            File docFileImpl = new File( implPath+"\\" + entityName+"ServiceImpl.java");
            FileOutputStream fos =FileUtils.openOutputStream(docFile); 
            FileOutputStream fosImpl =FileUtils.openOutputStream(docFileImpl); 
            out = new BufferedWriter(new OutputStreamWriter(fos));
            outImpl = new BufferedWriter(new OutputStreamWriter(fosImpl));
            // step6 输出文件
            templateService.process(dataMap, out);
            templateServiceImpl.process(dataMap, outImpl);
            System.out.println(entityName+"Service"+"已经生成完成  位置 在 "+beanPath);
            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
                if (null != outImpl) {
                	outImpl.flush();
                	outImpl.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		return null;
	}
	
	/**
	 * 
	 * @param controllerPackageName
	 * @param servicePackageName
	 * @param entityPackageName
	 * @param entityName
	 * @param modelName
	 */
	public static void generatorController(String controllerPackageName, String servicePackageName,
			String entityPackageName, String entityName,String keyType,boolean isTree) {
		
		String basePath = System.getProperty("user.dir");
		String tempPath =basePath+"\\src\\main\\resources\\bean_template";
		String beanPath =basePath+"\\src\\main\\java\\";
		String replaceAll = controllerPackageName.replaceAll("\\.", "\\\\");
		beanPath=beanPath+replaceAll;
		if(StringUtils.isBlank(controllerPackageName)
				||StringUtils.isBlank(servicePackageName)
				||StringUtils.isBlank(entityPackageName)
				||StringUtils.isBlank(entityName)
				) {
			return ;
		}
				//第一步:实例化Freemarker的配置类
				Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

				Writer out = null;
		        try {
		            // step2 获取模版路径
		            configuration.setDirectoryForTemplateLoading(new File(tempPath));
		            // step3 创建数据模型
		            Map<String, Object> dataMap = new HashMap<String, Object>();
		            dataMap.put("controller_package_name", controllerPackageName);
		            dataMap.put("entity_package_name", entityPackageName);
		            dataMap.put("service_package_name", servicePackageName);
		            dataMap.put("entity_name", entityName);
		            dataMap.put("c_time", DateUtils.now());
		            dataMap.put("isTree", isTree);
		            dataMap.put("model_name", StringUtils.toLowerCaseFirstOne(entityName));
		            dataMap.put("key_type",keyType);
		            // step4 加载模版文件
		            Template template = configuration.getTemplate("Controller.ftl");
		            // step5 生成数据
		            //判断文件夹是否存在
		            File docFile = new File( beanPath+"\\" + entityName+"Controller.java");
		            FileOutputStream fos =FileUtils.openOutputStream(docFile); 
		            out = new BufferedWriter(new OutputStreamWriter(fos));
		            // step6 输出文件
		            template.process(dataMap, out);
		            System.out.println(entityName+"Controller"+"已经生成完成  位置 在 "+beanPath);
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (null != out) {
		                    out.flush();
		                    out.close();
		                }
		            } catch (Exception e2) {
		                e2.printStackTrace();
		            }
		        }
		return;
	}
	/**
	 * 
	 * @param dir
	 * @param queryField 需要查询的自动 
	 * @throws SQLException 
	 */
	public static void generatorHtml(String tableName ,String dir, Map<String,Boolean> queryField,String orderBy,DataSource dataSource,boolean isTree,String nameFiled) throws SQLException {
		if(StringUtils.isBlank(tableName)||StringUtils.isBlank(dir)) {
			return ;
		}
		String basePath = System.getProperty("user.dir");
		String tempPath =basePath+"\\src\\main\\resources\\bean_template\\html";
		String beanPath =dir;
		
		String beanName =StringUtils.toCapitalizeCamelCase(tableName);
		String entityName =beanName;
		List<Map<String, Object>> colList = new ArrayList<Map<String, Object>>(); // 存储字段信息
		Connection connection = dataSource.getConnection();
		Statement st = connection.createStatement();
		String keyType="";
		String keyName="";
		boolean hasDelFlag=false;
		ResultSet columnRs = st.executeQuery("show full columns from " + tableName);
		while (columnRs.next()) {
			Map<String, Object> colMap = new HashMap<String, Object>();
			String column = columnRs.getString(1);//列名
			if(column.equals("del_flag")) {
				hasDelFlag=true;
			}
			colMap.put("column", column);//列名
			colMap.put("filed", StringUtils.toCamelCase(column));//字段名
			String database_type = columnRs.getString(2);
			
			colMap.put("database_type", database_type);//数据库类型
			String filedType = StringUtils.typeTrans(columnRs.getString(2));
			
			colMap.put("bean_type",filedType);//字段类型
			String key = columnRs.getString(5);
			
			if(key.equals("PRI")&&StringUtils.isBlank(keyType)) {
				keyType=filedType;
				keyName=StringUtils.toCamelCase(column);
			}
			colMap.put("isId", key);
			String comment = columnRs.getString(9);
			String[] split = comment.split("@");
			colMap.put("comment",split[0]);
			if(split.length==3) {
				colMap.put("comment",split[0]+split[2]);
				colMap.put("component",split[1]);
				String[] values = split[2].split("#");
				
				ArrayList<Map<String,String>> valueList=new ArrayList<>();
				for (int i = 0; i < values.length; i++) {
					String[] split2 = values[i].split("=");
					HashMap<String, String> hashMap = new HashMap<>();
					hashMap.put("key", split2[1]);
					hashMap.put("value", split2[0]);
					valueList.add(hashMap);
				}
				
				colMap.put("selectList",valueList);
			}
			colMap.put("nullable",columnRs.getString(4));
			if(queryField.containsKey(column)) {
				colMap.put("isQuery", "yes");
			}else {
				colMap.put("isQuery", "no");
			}
			colList.add(colMap);
		}
		//第一步:实例化Freemarker的配置类
		Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

		Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(tempPath));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("entity_name", entityName);
            dataMap.put("entityName", entityName);
            dataMap.put("bean_name", entityName);
            dataMap.put("name_filed", nameFiled);
            dataMap.put("mid_score_name", StringUtils.toMidScoreCase(entityName));
            dataMap.put("c_time", DateUtils.now());
            dataMap.put("model_name", StringUtils.toLowerCaseFirstOne(entityName));
            dataMap.put("key_type",keyType);
            dataMap.put("orderByClause",orderBy);
            dataMap.put("col_List",colList);
            dataMap.put("isTree",isTree);
            dataMap.put("hasDelFlag",hasDelFlag);
            // step4 加载模版文件
            Template template = configuration.getTemplate("api.ftl");
            // step5 生成数据
            //判断文件夹是否存在
            File docFile = new File( beanPath+"\\" + entityName+".js");
            FileOutputStream fos =FileUtils.openOutputStream(docFile); 
            out = new BufferedWriter(new OutputStreamWriter(fos));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println(entityName+".js"+"已经生成完成  位置 在 "+beanPath);
            //############list页面
            Template listtemplate ;
            if(isTree) {
            	listtemplate = configuration.getTemplate("tree.ftl");
            }else {
            	listtemplate = configuration.getTemplate("List.ftl");
            }
            File listdocFile = new File( dir+"\\" + entityName+"List.vue");
            FileOutputStream listfos =FileUtils.openOutputStream(listdocFile); 
            out = new BufferedWriter(new OutputStreamWriter(listfos));
            // step6 输出文件
            listtemplate.process(dataMap, out);
            System.out.println(entityName+"List.vue"+"已经生成完成  位置 在 "+dir);
            
            //############modal页面
            Template modaltemplate = configuration.getTemplate("Modal.ftl");
            File modaldocFile = new File( dir+"\\modules\\" + entityName+"Modal.vue");
            FileOutputStream modalfos =FileUtils.openOutputStream(modaldocFile); 
            out = new BufferedWriter(new OutputStreamWriter(modalfos));
            
           
            // step6 输出文件
            modaltemplate.process(dataMap, out);
            System.out.println(entityName+"Modal.vue"+"已经生成完成  位置 在 "+dir+"\\modules");
            
            
            
            Template dmodaltemplate = configuration.getTemplate("DrawerModal.ftl");
            File dmodaldocFile = new File( dir+"\\modules\\" + entityName+"DrawerModal.vue");
            FileOutputStream dmodalfos =FileUtils.openOutputStream(dmodaldocFile); 
            out = new BufferedWriter(new OutputStreamWriter(dmodalfos));
            dmodaltemplate.process(dataMap, out);
            System.out.println(entityName+"DrawerModal.vue"+"已经生成完成  位置 在 "+dir+"\\modules");
          //############form页面
			/*
			 * Template formtemplate = configuration.getTemplate("Form.ftl"); File
			 * formdocFile = new File( dir+"\\modules\\" + entityName+"Form.vue");
			 * FileOutputStream formfos =FileUtils.openOutputStream(formdocFile); out = new
			 * BufferedWriter(new OutputStreamWriter(formfos)); // step6 输出文件
			 * formtemplate.process(dataMap, out);
			 * System.out.println(entityName+"Form.vue"+"已经生成完成  位置 在 "+dir+"\\modules");
			 */
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		return;
	}
	
	
	/**
	 * 生成 entity repository service
	 * @param tableName 表的名字
	 * @param entityPackageName
	 * @param repositoryPackageName
	 * @param servicePackageName
	 * @param dataSource
	 * @throws Exception 
	 */
	public static void generatorAll(String tableName ,
									String queryTimeFiled,
									String entityPackageName,
									String repositoryPackageName,
									String servicePackageName,
									String controllerPackageName,
									DataSource dataSource) throws Exception {
		Map<String, Object> map = generatorEntity(tableName ,entityPackageName,dataSource);
		map.put("queryTimeFiled", queryTimeFiled);
		generatorMapper(repositoryPackageName, entityPackageName, (String)map.get("bean_name"), (String)map.get("key_type"));
		generatorService(queryTimeFiled,servicePackageName, repositoryPackageName, entityPackageName, (String)map.get("bean_name"), (String)map.get("key_type"),(String)map.get("key_name"),(boolean) map.get("isTree"),(boolean) map.get("hasDelFlag"));
		generatorController(controllerPackageName, servicePackageName, entityPackageName, (String)map.get("bean_name"), (String)map.get("key_type"),(boolean) map.get("isTree"));
		//generatorHtml(controllerPackageName, servicePackageName, entityPackageName, (String)map.get("bean_name"), (String)map.get("key_type"));
		//generatorAsyncService(asyncServicePackageName, servicePackageName, entityPackageName, (String)map.get("bean_name"), (String)map.get("key_type"));
	}
	
	
}
