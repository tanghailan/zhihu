package com.coderman.zhihu.service.question.impl;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.dao.question.QuestionDAO;
import com.coderman.zhihu.model.question.QuestionModel;
import com.coderman.zhihu.service.question.QuestionService;
import com.coderman.zhihu.util.AuthUtil;
import com.coderman.zhihu.util.PageUtil;
import com.coderman.zhihu.vo.question.QuestionParamVO;
import com.coderman.zhihu.vo.question.QuestionQueryVO;
import com.coderman.zhihu.vo.question.QuestionVO;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author coderman
 * @Title: 问题服务实现
 * @date 2022/5/2818:25
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionDAO questionDAO;


    @Override
    public ResultVO<Void> create(QuestionParamVO questionParamVO) {

        String questionTitle = questionParamVO.getQuestionTitle();
        String questionContent = questionParamVO.getQuestionContent();
        List<Integer> topicIdList = questionParamVO.getTopicIdList();
        Boolean isAnonymous = questionParamVO.getIsAnonymous();

        if (StringUtils.isBlank(questionTitle) || StringUtils.length(questionTitle) > 21) {
            return ResultUtil.getWarn("问题标题不能为空，并且不不能超过20个字符");
        }

        if (StringUtils.isBlank(questionContent) || StringUtils.length(questionContent) > 5001) {
            return ResultUtil.getWarn("问题内容不能为空，并且不不能超过5000个字符");
        }

        if (CollectionUtils.isEmpty(topicIdList)) {
            return ResultUtil.getWarn("绑定话题（至少添加一个）");
        }

        if (null == isAnonymous) {
            return ResultUtil.getWarn("请选择是否匿名提问题");
        }

        // 插入问题
        QuestionModel createModel = new QuestionModel();
        createModel.setCreateTime(new Date());
        createModel.setQuestionTitle(questionTitle);
        createModel.setQuestionContent(questionContent);
        createModel.setIsAnonymous(isAnonymous);
        createModel.setFollowCount(0);
        createModel.setViewCount(0);
        createModel.setLikeCount(0);
        createModel.setUserId(AuthUtil.getCurrent().getUserId());

        this.questionDAO.insertSelective(createModel);

        // 插入问题话题


        return ResultUtil.getSuccess();
    }

    @Override
    public ResultVO<PageVO<List<QuestionVO>>> page(Integer currentPage, Integer pageSize, QuestionQueryVO queryVO) {

        // 分页
        PageHelper.startPage(currentPage, pageSize);
        List<QuestionVO> questionList = this.questionDAO.page(queryVO);


        return ResultUtil.getSuccessPage(QuestionVO.class, PageUtil.getPageVO(questionList));
    }
}
