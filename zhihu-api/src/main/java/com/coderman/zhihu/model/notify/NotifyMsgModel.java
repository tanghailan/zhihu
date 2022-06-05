package com.coderman.zhihu.model.notify;

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
@ApiModel(value="NotifyMsgModel", description = "")
public class NotifyMsgModel extends BaseModel {
    
    @ApiModelProperty(value = "通知id")
    private Integer notifyMsgId;

    @ApiModelProperty(value = "通知消息标题")
    private String msgTitle;

    @ApiModelProperty(value = "消息类型")
    private Integer msgType;

    @ApiModelProperty(value = "发送方用户id")
    private Integer fromUserId;

    @ApiModelProperty(value = "是否已读")
    private Boolean isRead;

    @ApiModelProperty(value = "关联id")
    private Integer relationId;

    @ApiModelProperty(value = "接受方用户id")
    private Integer toUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}