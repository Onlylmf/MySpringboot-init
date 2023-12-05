package com.murphy.mapper.sys;
import com.murphy.model.entity.sys.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @date  2023-12-04 10:20:31
 */
@org.apache.ibatis.annotations.Mapper
public interface SysUserMapper extends Mapper<SysUser>{


    @Select("SELECT COUNT(*) from sys_user WHERE username = #{username}")
    int selectUserCountByUsername(@Param("username") String username);
	
}