package com.coderman.zhihu.service.topic;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.vo.topic.TopicParamVO;
import com.coderman.zhihu.vo.topic.TopicVO;

import java.util.List;

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


    /**
     * 话题分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    ResultVO<PageVO<List<TopicVO>>> page(Integer currentPage, Integer pageSize);
}
