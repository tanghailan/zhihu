package com.coderman.zhihu.model.question;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="QuestionFollowModel", description = "")
public class QuestionFollowModel extends BaseModel {
    
    @ApiModelProperty(value = "主键")
    private Integer questionFollowId;

    @ApiModelProperty(value = "关注状态")
    private Boolean followStatus;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "问题id")
    private Integer questionId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}