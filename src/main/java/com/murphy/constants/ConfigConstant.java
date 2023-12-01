package com.murphy.constants;

import com.murphy.utils.YmlUtils;

/**
 * 配置常量
 *
 */
public class ConfigConstant {

	public static  String I18N=YmlUtils.getvalue("config.language");
	//public static  String TOPIC_PREFIX=YmlUtils.getvalue("config.topicPrefix")==null?"":YmlUtils.getvalue("config.topicPrefix");
	public static  String SEND_TOPIC_PREFIX=YmlUtils.getvalue("config.sendTopicPrefix");
	public static  String RECEIVE_TOPIC_PREFIX=YmlUtils.getvalue("config.receiveTopicPrefix");
	public static  String HEART_TOPIC_PREFIX=YmlUtils.getvalue("config.heartBeatTopicPrefix");
	public static  String FILE_PATH=YmlUtils.getvalue("config.filePath");
	/**
	 * 隔离等级
	 */
	public static  Integer ISOLATE_LEVEL=Integer.parseInt(YmlUtils.getvalue("config.isolate.level"));
	/**
	 * 隔离等级
	 */
	public static  Integer SYS_DB_VERSION=Integer.parseInt(YmlUtils.getvalue("config.sysDbVersion"));
	/**
	 * 是否开启隔离
	 */
	public static  Boolean ISOLATE_ENABLE=Boolean.parseBoolean(YmlUtils.getvalue("config.isolate.enable"));
	/**
	 * 挂失几天后可以注销
	 */
	public static  Integer WRITE_OFF_DAY=Integer.parseInt(YmlUtils.getvalue("config.writeoffday"));
	//public static  Integer TASK_FAILED_DAY=Integer.parseInt(YmlUtils.getvalue("config.taskfailedday"));

	public static  Integer MAX_TRY_TIMES=Integer.parseInt(YmlUtils.getvalue("config.maxtrytimes"));
	public static  Integer OFF_LINE_TIME=Integer.parseInt(YmlUtils.getvalue("config.offlinetime"));
	public static  Integer TASK_FAILED_TIME=Integer.parseInt(YmlUtils.getvalue("config.taskfailedtime"));
	public static  String MYSQL_URL=YmlUtils.getvalue("spring.datasource.url");
	public static Long[] DELAY_TIME_ARRAY = YmlUtils.getLongArray("config.timesarray");
	static {
		System.out.println("ISOLATE_LEVEL =" +ISOLATE_LEVEL);
		System.out.println("I18N =" +I18N);
		System.out.println("FILE_PATH =" +FILE_PATH);
	}
}
