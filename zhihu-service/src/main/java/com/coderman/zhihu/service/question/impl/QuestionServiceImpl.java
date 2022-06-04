package com.coderman.zhihu.service.question.impl;

import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.constant.UserConstant;
import com.coderman.zhihu.dao.question.QuestionDAO;
import com.coderman.zhihu.dao.question.QuestionFollowDAO;
import com.coderman.zhihu.dao.question.QuestionTopicDAO;
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
import com.google.common.collect.Lists;
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

    @Resource
    private QuestionTopicDAO questionTopicDAO;


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
        Lists.partition(topicIdList, 10).forEach(subList -> {
            this.questionTopicDAO.insertBatch(createModel.getQuestionId(), subList);
        });



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

        AuthUserVO current = AuthUtil.getCurrent();

        // 校验一下关注状态
        QuestionFollowExample e = new QuestionFollowExample();
        e.createCriteria().andQuestionIdEqualTo(questionId).andUserIdEqualTo(current.getUserId());
        Optional<QuestionFollowModel> existOp = this.questionFollowDAO.selectByExample(e).stream().findFirst();


        if (!existOp.isPresent()) {
            return ResultUtil.getWarn("未关注不能取消关注");
        }


        QuestionFollowModel followModel = existOp.get();
        if (BooleanUtils.isNotTrue(followModel.getFollowStatus())) {
            return ResultUtil.getWarn("只有已关注的问题才能取消关注");
        }


        // 更新关注状态为false
        int count = this.questionFollowDAO.updateNotFollow(questionId, current.getUserId());
        if (count <= 0) {

            throw new BusinessException("取消关注问题失败！");
        }

        // 减少关注人数
        this.questionDAO.updateFollowCountDown(questionId);
        return ResultUtil.getSuccess();
    }

    @Override
    public ResultVO<PageVO<List<QuestionVO>>> selectFollowedPage(Integer currentPage, Integer pageSize, QuestionQueryVO queryVO) {


        AuthUserVO current = AuthUtil.getCurrent();
        if(current == null){
            return ResultUtil.getWarn("请先登入");
        }

        Integer userId = current.getUserId();
        List<QuestionVO> questionList = this.questionDAO.selectFollowedPage(userId,queryVO);
        return ResultUtil.getSuccessPage(QuestionVO.class,PageUtil.getPageVO(questionList));
    }

    @Override
    public ResultVO<PageVO<List<QuestionVO>>> page(Integer currentPage, Integer pageSize, QuestionQueryVO queryVO) {

        // 分页
        PageHelper.startPage(currentPage, pageSize);
        List<QuestionVO> questionList = this.questionDAO.page(queryVO);

        // 匿名问题清除用户信息
        this.dealWithAnonymous(questionList);

        // 其他属性
        this.loginStatusSetField(questionList);
        return ResultUtil.getSuccessPage(QuestionVO.class, PageUtil.getPageVO(questionList));
    }


    /**
     * 匿名问题处理
     *
     * @param questionList
     */
    private void dealWithAnonymous(List<QuestionVO> questionList) {

        questionList.stream().filter(e -> BooleanUtils.isTrue(e.getIsAnonymous()))
                .collect(Collectors.toList())
                .forEach(anonymousQuestion -> {
                    anonymousQuestion.setUserId(UserConstant.USER_ANONYMOUS_ID);
                    anonymousQuestion.setUsername(UserConstant.USER_ANONYMOUS_USERNAME);
                    anonymousQuestion.setNickname(UserConstant.USER_ANONYMOUS_NICKNAME);
                    anonymousQuestion.setDescription(UserConstant.USER_ANONYMOUS_DESCRIPTION);
                    anonymousQuestion.setAvatar(UserConstant.USER_ANONYMOUS_AVATAR);
                });
    }


    /**
     * 用户如果登入需要设置一些额外的属性
     *
     * @param questionList 问题对象
     */
    private void loginStatusSetField(List<QuestionVO> questionList) {

        // 问题id集合
        List<Integer> questionIds = questionList.stream().map(QuestionVO::getQuestionId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(questionIds)) {
            return;
        }

        AuthUserVO current = AuthUtil.getCurrent();
        if (current == null) {
            return;
        }

        // 当前用户已关注的问题id集合
        List<Integer> followedIdList = this.questionFollowDAO.selectUserFollowed(questionIds, current.getUserId());

        for (QuestionVO questionVO : questionList) {

            // 关注状态
            questionVO.setIsFollowed(followedIdList.contains(questionVO.getQuestionId()));
        }
    }
}
























