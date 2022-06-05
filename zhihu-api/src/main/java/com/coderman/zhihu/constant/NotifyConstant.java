package com.coderman.zhihu.constant;

/**
 * @author coderman
 * @Title: 通知常量
 * @Description: TOD
 * @date 2022/6/510:56
 */
public interface NotifyConstant {

    /**
     * 系统用户
     */
    Integer FROM_SYSTEM_ID = -1;


    /**
     * 通知状态
     */
    Boolean NOTIFY_STATUS_READ = true;
    Boolean NOTIFY_STATUS_UNREAD = false;


    /**
     * 关注问题
     */
    Integer NOTIFY_FOLLOW_QUESTION_TYPE = 1;
    String NOTIFY_FOLLOW_QUESTION_MSG = "%s 关注了你的问题: %s";
}
