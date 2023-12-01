package com.murphy.constants;

/**
 * 语言文本常量
 */
public class ContentConstant {
	/**
	 * 验证码不能为空
	 */
	public static final String VCODE_ERROR="{vcode.error}";
	/**
	 * 请先登录
	 */
	public static final String PLEASE_LOGIN="{please.login}";
	public static final String LOGIN_ERROR="{login.error}";
	public static final String USERNAME_AREADY_BINDED="{username.already.binded}";
	public static final String SOCIAL_USER_AREADY_BINDED="{social.user.aready.binded}";
	public static final String MISS_PARAM="{miss.parameter}";
	/**
	 * 主键不能为空
	 */
	public static final String ID_NULL="{id.null}";
	/**
	 * 主键必须为空
	 */
	public static final String ID_NOT_NULL="{id.not.null}";
	/**
	 *     未知错误
	 */
	public static final String UNKNOWN_ERROR = "{unknown.error}";
	/**
	 *   操作成功
	 */
	public static final String OPERATE_SUCCESS = "{operate.success}";
	
	/**
	 *   异常操作
	 */
	public static final String OPERATE_ERROR = "{operate.error}";
	
	/**
	 *   添加成功
	 */
	public static final String ADD_SUCCESS = "{add.success}";
	
	/**
	 *   query成功
	 */
	public static final String QUERY_SUCCESS = "{query.success}";
	
	/**
	 *   更新成功
	 */
	public static final String UPDATE_SUCCESS = "{update.success}";
	
	
	/**
	 *  删除成功
	 */
	public static final String DELETE_SUCCESS = "{delete.success}";
	/**
	 * 父id不能为空
	 */
	public static final String PID_NULL="{pid.null}";
	/**
	 * 父对象不存在
	 */
	public static final String P_OBJECT_NOT_EXIST="{p.object.not.exist}";
	/**
	 * 对象不存在
	 */
	public static final String OBJECT_NOT_EXIST="{object.not.exist}";
	
	/**
	 * 不能删除
	 */
	public static final String CAN_NOT_DELETE="{can.not.delete}";
	/**
	 * 部门不存在
	 */
	public static final String DEPT_NOT_EXIST = "{dept.not.exist}";

	/**
	 * 部门id不能为空
	 */
	public static final String DEPT_ID_NULL = "{dept.id.null}";
	/**
	 * 角色id不能为空
	 */
	public static final String ROLE_ID_NULL = "{role.id.null}";
	/**
	 *  角色不存在
	 */
	public static final String ROLE_NOT_EXIST = "{role.not.exist}";

	public static final String PASSWORD_NULL = "{password.null}";
	/**
	 * 旧密码错误
	 */
	public static final String ERROR_OLD_PASSWORD = "{error.old.password}";
	/**
	 *  区域名字不能为空
	 */
	public static final String REGION_NAME_NULL = "{region.name.null}";
	/**
	 * 客户类型不存在
	 */
	public static final String CUSTOMERTYPE_NOT_EXIST = "{customertype.not.exist}";
	/**
	 * 没有找到原卡 please.first.add.card
	 */
	public static final String PLEASE_FIRST_ADD_CARD = "{please.first.add.card}";
	/**
	 * 客户不存在
	 */
	public static final String CUSTOMER_NOT_EXIST = "{customer.not.exist}";
	/**
	 * 客户卡不存在
	 */
	public static final String CUSTOMER_CARD_NOT_EXIST = "{customer.card.not.exist}";
	/**
	 * 机器不存在
	 */
	public static final String MACHINE_NOT_EXIST = "{machine.not.exist}";
	/**
	 * 机器已经存在未完成维修任务
	 */
	public static final String MACHINE_ALLREADY_HAS_TASK = "{machine.already.has.task}";
	/**
	 * 任务不存在
	 */
	public static final String TASK_NOT_EXIST = "{task.not.exist}";
	/**
	 * 任务已经完成
	 */
	public static final String TASK_ALREADY_FINISHED = "{task.already.finished}";
	/**
	 * 只有当前处理者能够操作
	 */
	public static final String ONLY_HANDLER_CAN_OPERATE = "{only.handler.can.operate}";
	/**
	 * 该卡无法挂失
	 */
	public static final String CAN_NOT_SUSPEND = "{can.not.suspend}";
	/**
	 * 该卡无法解挂
	 */
	public static final String CAN_NOT_UNSUSPEND = "{can.not.unsuspend}";
	
	/**
	 * 该卡无法注销
	 */
	public static final String CAN_NOT_WRITE_OFF_CARD = "{can.not.write.off.card}";
	/**
	 * 无法添加卡  
	 */
	public static final String CAN_NOT_ADD_CARD = "{can.not.add.card}";
	/**
	 * 该卡当前是挂失状态
	 */
	public static final String CARD_IS_SUSPEND_STATE = "{card.is.suspend.state}";
	/**
	 * 无法注销当前用户
	 */
	public static final String CAN_NOT_CANCEL_ACCOUNT = "{can.not.cancel.account}";
	/**
	 * 卡不匹配
	 */
	public static final String CARD_MISMATCH = "{card.mismatch}";
	/**
	 * Please put the card into the card reader
	 */
	public static final String PLEASE_READ_CARD = "{please.read.card}";//Please put the card into the card reader
	
	/**
	 * 请先取消任务
	 */
	public static final String CANCEL_TASK_FIRST = "{cancel.task.first}";
	public static final String ERROR_STATE = "{error.state}";
	/**
	 * 
	 */
	public static final String MACHINE_USED_BY_OTHER = "{machine.used.by.other}";
	public static final String CAN_NOT_CHANGE = "Cannot Change";
	public static final String RECHARE_TOO_LARGE = "The amount of recharge is too large";
	public static final String CAN_NOT_MOVE = "{can.not.move}";
	public static final String CAN_NOT_RECYCLE = "{can.not.recycle}";
	
	public static final String VENDOR_BALANCE_INSUFFICIENT = "{can.not.recharge}";
	
	public static final String RECHARGEMUST_MORETHAN_OVERDRAFT = "{recharge.mustmorethan.overdraft}";
	
	public static final String CARDID_CANNOT_BENULL = "{cardid.not.null}";
	
	//region 设备主动上报异常事件
	
	public static final String OPENMACHINE = "{machine.report.openmachine}";
	public static final String ERRORRECORD = "{machine.report.errorrecord}";
	public static final String LOWPRESSURE = "{machine.report.lowpressure}";
	public static final String NOWATER = "{machine.report.nowater}";
	public static final String POWERIN = "{machine.report.powerin}";
	public static final String POWEROUT = "{machine.report.powerout}";
	
	//通信完成
	public static final String COMMSUCCESS = "{machine.comm.success}";
	//endregion

	public static final String LOGIN_NAME_ERROR = "{login.name.error}";
	public static final String LOGIN_PASSWORD_ERROR = "{login.password.error}";
	
	public static final String VENDOR_RECHARGEAMOUNT_ERROR = "{vendor.rechargeamount.error}";
	public static final String MAXRECHARGEAMOUNT_MUSTSMALLTHAN_MAXBALANCE = "{rechargeamount.morethan.maxbalance}";
	
	public static final String RECHARGE_AMOUNT_TOOLARGE = "{rechargeamount.too.large}";
	public static final String RECHARGE_AMOUNT_CANNOTLESSZERO = "{rechargeamount.cannot.lesszero}";
	public static final String PRICE_AMOUNT_TOOLARGE = "{priceamount.too.large}";
	public static final String MAX_AMOUNT_TOOLARGE = "{maxamount.too.large}";
	
	public static final String CARD_NOUSERRECORD = "{card.no.userecord}";
	public static final String  USERNAME_IS_NULL= "{username.is.null}";
	public static final String  USERNAME_EXIST= "{user.name.exist}";
	public static final String  METER_NOT_EXIST= "{meter.not.exist}";
	public static final String  METER_TYPE_NOT_EXIST= "{meter.type.not.exist}";
}
