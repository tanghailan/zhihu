package com.coderman.zhihu.dao.question;

import com.coderman.mybatis.dao.BaseDAO;
import com.coderman.zhihu.model.question.QuestionExample;
import com.coderman.zhihu.model.question.QuestionModel;
import com.coderman.zhihu.vo.question.QuestionQueryVO;
import com.coderman.zhihu.vo.question.QuestionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionDAO extends BaseDAO<QuestionModel, QuestionExample> {

    /**
     * 分页
     * @param queryVO 查询对象
     * @return
     */
    List<QuestionVO> page(QuestionQueryVO queryVO);


    /**
     * 增加问题的关注人数
     *
     * @param questionId 问题id
     */
    void updateFollowCountUp(@Param(value = "questionId") Integer questionId);



    /**
     * 增加问题的关注人数
     *
     * @param questionId 问题id
     */
    void updateFollowCountDown(@Param(value = "questionId") Integer questionId);
}