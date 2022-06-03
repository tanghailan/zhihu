package com.coderman.zhihu.dao.question;

import com.coderman.mybatis.dao.BaseDAO;
import com.coderman.zhihu.model.question.QuestionFollowExample;
import com.coderman.zhihu.model.question.QuestionFollowModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionFollowDAO extends BaseDAO<QuestionFollowModel, QuestionFollowExample> {

    /**
     * 更新关注状态为未关注
     *
     * @param questionId 问题id
     * @param userId 用户id
     * @return
     */
    int updateNotFollow(@Param(value = "questionId") Integer questionId,@Param(value = "userId") Integer userId);

    /**
     * 查询已关注的问题id
     *
     * @param questionIdList 问题id集合
     * @param userId 用户id
     * @return
     */
    List<Integer> selectUserFollowed(@Param(value = "questionIdList") List<Integer> questionIdList,@Param(value = "userId") Integer userId);
}