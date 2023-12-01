package ${entity_package_name};

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
 * @date  ${c_time}
 * 从 ${table_name} 表 自动生成的entity.
 */
@Table(name="${table_name}")
@JsonInclude(JsonInclude.Include.NON_NULL) 
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class ${bean_name} implements Serializable {
	private static final long serialVersionUID = 1L;
	
<#list col_List as col>
	/**
     *   ${col.comment}
     */
	<#if col.isId = "PRI">
	@Id
		<#if key_type = "Long">	
	@GeneratedValue(strategy=GenerationType.IDENTITY)//自增主键
		</#if>
		<#if key_type = "Integer">	
	@GeneratedValue(strategy=GenerationType.IDENTITY)//自增主键
		</#if>
		<#if key_type = "String">	
	@KeySql(genId = MyIDGenId.class)
		</#if>
	@Null(groups = {AddGroup.class }, message = ContentConstant.ID_NOT_NULL)
	@NotNull(groups = {UpdateGroup.class,FindGroup.class }, message = ContentConstant.ID_NULL)
	<#else>
	/** @NotNull(groups = {AddGroup.class }, message = "${col.comment} 不能为空") */ 
	</#if>
    @ApiModelProperty(value="${col.comment}  ")
	private ${col.bean_type} ${col.filed};
	
	<#if col.filed = "hasChild">
	@Transient
    private List<${bean_name}> children;
    /** children 发给前端的时候不能为null 要发一个空数组 */ 
    public List<${bean_name}> getChildren() {
    	if(this.hasChild !=null && this.hasChild &&this.children ==null) {
    		this.children=new ArrayList<>();
    	}
		return this.children;
	};
	
	</#if>
	
</#list>


	
}