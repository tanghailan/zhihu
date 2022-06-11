package com.coderman.zhihu.service.notify.impl;

import com.coderman.api.exception.BusinessException;
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

    /**
     *
     * @param fromUserId 发送方id
     * @param toUserId   目标用户id
     * @param notifyType 通知类型
     * @param msgTitle   消息内容
     * @param relationId 关联id
     */
    @Override
    @SuppressWarnings("all")
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

    /**
     *
     * @param fromUserId 发送方id
     * @param toUserId   目标用户id
     * @param notifyType 通知类型
     * @param msgTitle   消息内容
     */
    @Override
    @SuppressWarnings("all")
    public void insertNotifyMsg(Integer fromUserId, Integer toUserId, Integer notifyType, String msgTitle) {
        Assert.notNull(fromUserId,"发送id不能为空");
        Assert.notNull(toUserId,"目标用户id不能为空");
        Assert.notNull(notifyType,"发送类型不能为空");
        Assert.notNull(msgTitle,"消息标题不能为空");


        NotifyMsgModel notifyMsgModel = new NotifyMsgModel();
        notifyMsgModel.setCreateTime(new Date());
        notifyMsgModel.setFromUserId(fromUserId);
        notifyMsgModel.setToUserId(toUserId);
        notifyMsgModel.setIsRead(NotifyConstant.NOTIFY_STATUS_UNREAD);
        notifyMsgModel.setMsgTitle(msgTitle);
        notifyMsgModel.setMsgType(notifyType);
        this.notifyMsgDAO.insertSelective(notifyMsgModel);
    }

    /**
     *
     * @param notifyMsgId 消息通知id
     */
    @Override
    public void updateReadStatus(Integer notifyMsgId) {

        Assert.notNull(notifyMsgId,"消息通知id不能为空!");

        NotifyMsgModel notifyMsgModel = this.notifyMsgDAO.selectByPrimaryKey(notifyMsgId);
        if(notifyMsgModel == null){
            return;
        }

        NotifyMsgModel update = new NotifyMsgModel();
        update.setNotifyMsgId(notifyMsgModel.getNotifyMsgId());
        update.setIsRead(NotifyConstant.NOTIFY_STATUS_READ);
        this.notifyMsgDAO.updateByPrimaryKeySelective(update);
    }
}
