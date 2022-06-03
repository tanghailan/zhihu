package com.coderman.zhihu.service.question.impl;

import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.dao.question.QuestionDAO;
import com.coderman.zhihu.dao.question.QuestionFollowDAO;
import com.coderman.zhihu.model.question.QuestionFollowExample;
import com.coderman.zhihu.model.question.QuestionFollowModel;
import com.coderman.zhihu.model.question.QuestionModel;
import com.coderman.zhihu.service.question.QuestionService;
import com.coderman.zhihu.util.AuthUtil;
import com.coderman.zhihu.util.PageUtil;
import com.coderman.zhihu.vo.question.QuestionParamVO;
import com.coderman.zhihu.vo.question.QuestionQueryVO;
import com.coderman.zhihu.vo.question.QuestionVO;
import com.coderman.zhihu.vo.user.AuthUserVO;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author coderman
 * @Title: 问题服务实现
 * @date 2022/5/2818:25
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionDAO questionDAO;


    @Resource
    private QuestionFollowDAO questionFollowDAO;


    @Override
    public ResultVO<Void> create(QuestionParamVO questionParamVO) {

        String questionTitle = questionParamVO.getQuestionTitle();
        String questionContent = questionParamVO.getQuestionContent();
        List<Integer> topicIdList = questionParamVO.getTopicIdList();
        Boolean isAnonymous = questionParamVO.getIsAnonymous();

        if (StringUtils.isBlank(questionTitle) || StringUtils.length(questionTitle) > 50) {
            return ResultUtil.getWarn("问题标题不能为空，并且不不能超过50个字符");
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
    public ResultVO<Void> updateFollow(Integer questionId) {


        QuestionModel questionModel = this.questionDAO.selectByPrimaryKey(questionId);
        if (questionModel == null) {
            return ResultUtil.getFail("关注的问题不存在!");
        }

        AuthUserVO current = AuthUtil.getCurrent();

        // 校验一下是否重复关注
        QuestionFollowExample e = new QuestionFollowExample();
        e.createCriteria().andQuestionIdEqualTo(questionId).andUserIdEqualTo(current.getUserId());
        Optional<QuestionFollowModel> existOp = this.questionFollowDAO.selectByExample(e).stream().findFirst();


        // 重复关注
        if (existOp.isPresent() && BooleanUtils.isTrue(existOp.get().getFollowStatus())) {


            return ResultUtil.getWarn("请勿重复关注问题!");
        } else if (existOp.isPresent() && BooleanUtils.isFalse(existOp.get().getFollowStatus())) {


            // 存在之前关注的记录,但是后面取消了,这里改变状态即可.
            QuestionFollowModel update = existOp.get();
            update.setFollowStatus(Boolean.TRUE);
            int count = this.questionFollowDAO.updateByPrimaryKeySelective(update);

            if (count < 0) {
                throw new BusinessException("关注问题失败!");
            }

        } else {


            QuestionFollowModel insert = new QuestionFollowModel();
            insert.setCreateTime(new Date());
            insert.setUserId(current.getUserId());
            insert.setFollowStatus(Boolean.TRUE);
            insert.setQuestionId(questionId);
            int count = this.questionFollowDAO.insertSelective(insert);

            if (count <= 0) {
                throw new BusinessException("关注问题失败!");
            }
        }

        // 增加问题的关注人数
        this.questionDAO.updateFollowCountUp(questionId);
        return ResultUtil.getSuccess();
    }

    @Override
    public ResultVO<Void> updateNotFollow(Integer questionId) {

        QuestionModel questionModel = this.questionDAO.selectByPrimaryKey(questionId);
        if (questionModel == null) {
            return ResultUtil.getFail("取消关注的问题不存在!");
        }


        // 更新关注状态为false
        int count = this.questionFollowDAO.updateNotFollow(questionId, AuthUtil.getCurrent().getUserId());
        if (count <= 0) {

            throw new BusinessException("取消关注问题失败！");
        }

        // 减少关注人数
        this.questionDAO.updateFollowCountDown(questionId);
        return ResultUtil.getSuccess();
    }

    @Override
    public ResultVO<PageVO<List<QuestionVO>>> page(Integer currentPage, Integer pageSize, QuestionQueryVO queryVO) {

        // 分页
        PageHelper.startPage(currentPage, pageSize);
        List<QuestionVO> questionList = this.questionDAO.page(queryVO);

        // 问题id集合
        List<Integer> questionIds = questionList.stream().map(QuestionVO::getQuestionId).collect(Collectors.toList());

        // 如果登入了需要设置一些额外的属性
        AuthUserVO current = AuthUtil.getCurrent();
        if (current != null && !CollectionUtils.isEmpty(questionIds)) {

            // 当前用户已关注的问题id集合
            List<Integer> followedIdList = this.questionFollowDAO.selectUserFollowed(questionIds, current.getUserId());

            for (QuestionVO questionVO : questionList) {

                // 关注状态
                questionVO.setIsFollowed(followedIdList.contains(questionVO.getQuestionId()));
            }
        }


        return ResultUtil.getSuccessPage(QuestionVO.class, PageUtil.getPageVO(questionList));
    }
}
























