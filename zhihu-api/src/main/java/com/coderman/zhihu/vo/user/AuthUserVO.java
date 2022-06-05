package com.coderman.zhihu.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author coderman
 * @Title: 当前登入用户信息
 * @Description: TOD
 * @date 2022/5/2811:39
 */
@Data
@Accessors(chain = true)
public class AuthUserVO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @ApiModelProperty(value = "性别")
    private String gender;
}
