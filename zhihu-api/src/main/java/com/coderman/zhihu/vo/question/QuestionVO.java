package com.coderman.zhihu.vo.question;

import com.coderman.zhihu.model.question.QuestionModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author coderman
 * @Title: 问题
 * @Description: TOD
 * @date 2022/6/311:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionVO extends QuestionModel {


    @ApiModelProperty(value = "创建人账号")
    private String username;


    @ApiModelProperty(value = "创建人昵称")
    private String nickname;

}
