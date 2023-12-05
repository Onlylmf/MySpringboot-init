package com.murphy.model.entity.sys;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.murphy.constants.ContentConstant;
import com.murphy.common.validatgroup.AddGroup;
import com.murphy.common.validatgroup.FindGroup;
import com.murphy.common.validatgroup.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import com.murphy.utils.MyIDGenId;
import tk.mybatis.mapper.annotation.KeySql;
/**
 * @date  2023-12-04 10:20:31
 * 从 sys_user 表 自动生成的entity.
 */
@Table(name="sys_user")
@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     *   id
     */
	@Id
	@KeySql(genId = MyIDGenId.class)
	@Null(groups = {AddGroup.class }, message = ContentConstant.ID_NOT_NULL)
	@NotNull(groups = {UpdateGroup.class,FindGroup.class }, message = ContentConstant.ID_NULL)
    @ApiModelProperty(value="id  ")
	private String id;
	
	
	/**
     *   用户名
     */
	/** @NotNull(groups = {AddGroup.class }, message = "用户名 不能为空") */ 
    @ApiModelProperty(value="用户名  ")
	private String username;
	
	
	/**
     *   密码
     */
	/** @NotNull(groups = {AddGroup.class }, message = "密码 不能为空") */ 
    @ApiModelProperty(value="密码  ")
	private String password;
	
	
	/**
     *   昵称
     */
	/** @NotNull(groups = {AddGroup.class }, message = "昵称 不能为空") */ 
    @ApiModelProperty(value="昵称  ")
	private String nickname;
	
	
	/**
     *   头像url
     */
	/** @NotNull(groups = {AddGroup.class }, message = "头像url 不能为空") */ 
    @ApiModelProperty(value="头像url  ")
	private String headImgUrl;
	
	
	/**
     *   手机号
     */
	/** @NotNull(groups = {AddGroup.class }, message = "手机号 不能为空") */ 
    @ApiModelProperty(value="手机号  ")
	private String phone;
	
	
	/**
     *   性别
     */
	/** @NotNull(groups = {AddGroup.class }, message = "性别 不能为空") */ 
    @ApiModelProperty(value="性别  ")
	private Integer sex;
	
	
	/**
     *   状态（1有效,0无效）
     */
	/** @NotNull(groups = {AddGroup.class }, message = "状态（1有效,0无效） 不能为空") */ 
    @ApiModelProperty(value="状态（1有效,0无效）  ")
	private Boolean enabled;
	
	
	/**
     *   备注
     */
	/** @NotNull(groups = {AddGroup.class }, message = "备注 不能为空") */ 
    @ApiModelProperty(value="备注  ")
	private String remarks;
	
	
	/**
     *   创建时间
     */
	/** @NotNull(groups = {AddGroup.class }, message = "创建时间 不能为空") */ 
    @ApiModelProperty(value="创建时间  ")
	private Date createtime;
	
	
	/**
     *   修改时间
     */
	/** @NotNull(groups = {AddGroup.class }, message = "修改时间 不能为空") */ 
    @ApiModelProperty(value="修改时间  ")
	private Date updatetime;
	
	
	/**
     *   用户角色：user/admin/ban
     */
	/** @NotNull(groups = {AddGroup.class }, message = "用户角色：user/admin/ban 不能为空") */ 
    @ApiModelProperty(value="用户角色：user/admin/ban  ")
	private String userRole;
	
	
	/**
     *   账户余额
     */
	/** @NotNull(groups = {AddGroup.class }, message = "账户余额 不能为空") */ 
    @ApiModelProperty(value="账户余额  ")
	private BigDecimal balance;
	

	/**
     *   是否删除(1删除  0未删除)
     */
	/** @NotNull(groups = {AddGroup.class }, message = "是否删除(1删除  0未删除) 不能为空") */ 
    @ApiModelProperty(value="是否删除(1删除  0未删除)  ")
	private Boolean delFlag;
	
	


	
}