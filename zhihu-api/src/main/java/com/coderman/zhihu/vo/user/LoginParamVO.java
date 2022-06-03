package com.coderman.zhihu.vo.user;

import com.coderman.zhihu.model.user.UserModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author coderman
 * @Title: 登入参数
 * @Description: TOD
 * @date 2022/5/2722:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginParamVO extends UserModel {

    private String captchaToken;
    private String captchaCode;
}
