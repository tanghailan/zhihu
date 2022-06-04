package com.coderman.zhihu.dao.topic;

import com.coderman.api.vo.ResultVO;
import com.coderman.mybatis.dao.BaseDAO;
import com.coderman.zhihu.model.topic.TopicExample;
import com.coderman.zhihu.model.topic.TopicModel;
import com.coderman.zhihu.vo.topic.TopicVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicDAO extends BaseDAO<TopicModel, TopicExample> {

    /**
     * 话题分页
     *
     * @return
     */
    List<TopicVO> page();

    /**
     * 根据话题名称查询话题
     *
     * @param keyword 话题名称
     * @return
     */
    ResultVO<List<TopicVO>> selectByTopicName(@Param(value = "topicName") String keyword);
}