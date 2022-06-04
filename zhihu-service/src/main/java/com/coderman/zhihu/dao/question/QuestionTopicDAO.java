package com.coderman.zhihu.dao.question;

import com.coderman.mybatis.dao.BaseDAO;
import com.coderman.zhihu.model.question.QuestionTopicExample;
import com.coderman.zhihu.model.question.QuestionTopicModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionTopicDAO extends BaseDAO<QuestionTopicModel, QuestionTopicExample> {

    /**
     * 批量插入
     *
     * @param questionId 问题id
     * @param subList 话题id
     */
    void insertBatch(@Param(value = "questionId") Integer questionId,@Param(value = "topicIdList") List<Integer> subList);
}