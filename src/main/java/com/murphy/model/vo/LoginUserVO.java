package com.murphy.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class LoginUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     */
    private String id;

    /**
     * 用户昵称
     */
    private String username;


    /**
     * 用户昵称
     */
    private String nickname;


    /**
     * 性别
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String headImgUrl;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;


    /**
     * 用于验证
     */
    private Boolean valid;


    public LoginUserVO() {
        this.valid = false;
    }

}