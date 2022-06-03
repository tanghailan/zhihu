package com.coderman.zhihu.vo.question;

import com.coderman.zhihu.model.question.QuestionModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author coderman
 * @Title: 创建问题
 * @Description: TOD
 * @date 2022/5/2723:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateParamVO extends QuestionModel {

    @ApiModelProperty(value = "话题id")
    private List<Integer> topicIdList;
}
