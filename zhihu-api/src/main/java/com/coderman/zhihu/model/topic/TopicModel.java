package com.coderman.zhihu.model.topic;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@ApiModel(value="TopicModel", description = "")
public class TopicModel extends BaseModel {
    
    @ApiModelProperty(value = "话题id")
    private Integer topicId;

    @ApiModelProperty(value = "话题封面")
    private String topicLogo;

    @ApiModelProperty(value = "话题描述")
    private String topicDesc;

    @ApiModelProperty(value = "话题名称")
    private String topicName;

    @ApiModelProperty(value = "创建人")
    private Integer userId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}