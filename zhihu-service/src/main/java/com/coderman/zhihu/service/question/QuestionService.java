package com.coderman.zhihu.service.question;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.vo.question.QuestionParamVO;
import com.coderman.zhihu.vo.question.QuestionQueryVO;
import com.coderman.zhihu.vo.question.QuestionVO;

import java.util.List;

/**
 * @author coderman
 * @Title: 问题服务
 * @date 2022/5/2818:24
 */
public interface QuestionService {



    /**
     * 查询列表
     *
     * @param currentPage 当前页面
     * @param pageSize 每页显示条数
     * @param queryVO 查询对象
     * @return
     */
    ResultVO<PageVO<List<QuestionVO>>> page(Integer currentPage, Integer pageSize, QuestionQueryVO queryVO);


    /**
     * 创建问题
     * @param questionParamVO 问题参数
     * @return
     */
    ResultVO<Void> create(QuestionParamVO questionParamVO);


    /**
     * 关注问题
     *
     * @param questionId 问题id
     * @return
     */
    ResultVO<Void> updateFollow(Integer questionId);


    /**
     * 取消关注问题
     *
     * @param questionId
     * @return
     */
    ResultVO<Void> updateNotFollow(Integer questionId);


    /**
     * 查询已关注问题列表
     *
     * @param currentPage 当前页面
     * @param pageSize 每页显示条数
     * @param queryVO 查询对象
     * @return
     */
    ResultVO<PageVO<List<QuestionVO>>> selectFollowedPage(Integer currentPage, Integer pageSize, QuestionQueryVO queryVO);
}
