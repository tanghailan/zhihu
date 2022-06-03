package com.coderman.zhihu.vo.user;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author coderman
 * @Title: 登入验证码
 * @Description: TOD
 * @date 2022/5/2911:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginCaptchaVO extends BaseModel {

    @ApiModelProperty(value = "验证码base64格式")
    private String captchaBase64;

    @ApiModelProperty(value = "验证码token")
    private String captchaToken;
}
