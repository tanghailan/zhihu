package com.coderman.zhihu.service.topic.impl;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.dao.topic.TopicDAO;
import com.coderman.zhihu.model.topic.TopicModel;
import com.coderman.zhihu.service.topic.TopicService;
import com.coderman.zhihu.util.AuthUtil;
import com.coderman.zhihu.vo.topic.TopicParamVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author coderman
 * @Title: 话题实现
 * @Description: TOD
 * @date 2022/6/312:44
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicDAO topicDAO;


    @Override
    public ResultVO<Void> create(TopicParamVO topicParamVO) {

        String topicName = topicParamVO.getTopicName();
        String topicDesc = topicParamVO.getTopicDesc();
        String topicLogo = topicParamVO.getTopicLogo();

        if (StringUtils.isBlank(topicName) || StringUtils.length(topicName) > 15) {
            return ResultUtil.getWarn("话题名称不能为空,且不能超过15个字符");
        }

        if (StringUtils.isBlank(topicDesc) || StringUtils.length(topicDesc) > 25 || StringUtils.length(topicDesc) < 5) {
            return ResultUtil.getWarn("话题描述不能为空,5-25个字符");
        }

        if (StringUtils.isBlank(topicLogo)) {
            return ResultUtil.getWarn("话题logo图片不能为空");
        }


        TopicModel insert = new TopicModel();
        insert.setCreateTime(new Date());
        insert.setUserId(AuthUtil.getCurrent().getUserId());
        insert.setTopicLogo(topicLogo);
        insert.setTopicDesc(topicDesc);
        insert.setTopicName(topicName);
        this.topicDAO.insertSelective(insert);
        return ResultUtil.getSuccess();
    }



}
