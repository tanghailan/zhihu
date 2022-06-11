package com.coderman.zhihu.service.notify;

import com.coderman.api.vo.ResultVO;

/**
 * @author coderman
 * @Title: 消息服务
 * @Description: TOD
 * @date 2022/6/510:54
 */
public interface NotifyMsgService {


    /**
     * 发送消息通知
     *
     * @param fromUserId 发送方id
     * @param toUserId   目标用户id
     * @param notifyType 通知类型
     * @param msgTitle   消息内容
     * @param relationId 关联id
     */
    void insertNotifyMsg(Integer fromUserId, Integer toUserId, Integer notifyType, String msgTitle, Integer relationId);


    /**
     * 已读消息
     *
     * @param notifyMsgId 消息通知id
     */
    void updateReadStatus(Integer notifyMsgId);


    /**
     * 发送消息通知
     *
     * @param fromUserId 发送方id
     * @param toUserId   目标用户id
     * @param notifyType 通知类型
     * @param msgTitle   消息内容
     */
    void insertNotifyMsg(Integer fromUserId, Integer toUserId, Integer notifyType, String msgTitle);
}
