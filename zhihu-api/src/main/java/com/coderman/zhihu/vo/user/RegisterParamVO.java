package com.coderman.zhihu.vo.user;

import com.coderman.zhihu.model.user.UserModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author coderman
 * @Title: 注册参数
 * @Description: TOD
 * @date 2022/5/2722:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterParamVO extends UserModel {

    @ApiModelProperty(value = "邮箱验证码")
    private String emailCode;
}
