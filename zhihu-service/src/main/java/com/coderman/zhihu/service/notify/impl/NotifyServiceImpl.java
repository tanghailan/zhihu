package com.coderman.zhihu.service.notify.impl;

import com.coderman.zhihu.constant.NotifyConstant;
import com.coderman.zhihu.dao.notify.NotifyMsgDAO;
import com.coderman.zhihu.model.notify.NotifyMsgModel;
import com.coderman.zhihu.service.notify.NotifyMsgService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author coderman
 * @Title: TODO
 * @Description: TOD
 * @date 2022/6/511:03
 */
@Service
public class NotifyServiceImpl implements NotifyMsgService {

    @Resource
    private NotifyMsgDAO notifyMsgDAO;

    @Override
    public void insertNotifyMsg(Integer fromUserId, Integer toUserId, Integer notifyType, String msgTitle, Integer relationId) {

        Assert.notNull(fromUserId,"发送id不能为空");
        Assert.notNull(toUserId,"目标用户id不能为空");
        Assert.notNull(notifyType,"发送类型不能为空");
        Assert.notNull(msgTitle,"消息标题不能为空");
        Assert.notNull(relationId,"关联id不能为空");

        NotifyMsgModel notifyMsgModel = new NotifyMsgModel();
        notifyMsgModel.setCreateTime(new Date());
        notifyMsgModel.setFromUserId(fromUserId);
        notifyMsgModel.setToUserId(toUserId);
        notifyMsgModel.setIsRead(NotifyConstant.NOTIFY_STATUS_UNREAD);
        notifyMsgModel.setMsgTitle(msgTitle);
        notifyMsgModel.setMsgType(notifyType);
        notifyMsgModel.setRelationId(relationId);
        this.notifyMsgDAO.insertSelective(notifyMsgModel);
    }
}
