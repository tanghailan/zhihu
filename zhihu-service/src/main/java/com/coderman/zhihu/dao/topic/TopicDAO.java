package com.coderman.zhihu.dao.topic;

import com.coderman.mybatis.dao.BaseDAO;
import com.coderman.zhihu.model.topic.TopicExample;
import com.coderman.zhihu.model.topic.TopicModel;
import com.coderman.zhihu.vo.topic.TopicVO;

import java.util.List;

public interface TopicDAO extends BaseDAO<TopicModel, TopicExample> {

    /**
     * 话题分页
     *
     * @return
     */
    List<TopicVO> page();
}