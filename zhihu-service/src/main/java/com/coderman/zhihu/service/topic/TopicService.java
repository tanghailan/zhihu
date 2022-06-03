package com.coderman.zhihu.service.topic;

import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.vo.topic.TopicParamVO;

/**
 * @author coderman
 * @Title: 话题
 * @Description: TOD
 * @date 2022/6/312:40
 */
public interface TopicService {

    /**
     * 话题创建
     * @param topicParamVO 参数
     * @return
     */
    ResultVO<Void> create(TopicParamVO topicParamVO);
}
