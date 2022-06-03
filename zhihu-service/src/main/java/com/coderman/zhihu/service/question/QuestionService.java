package com.coderman.zhihu.service.question;

import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.vo.question.CreateParamVO;

/**
 * @author coderman
 * @Title: 问题服务
 * @date 2022/5/2818:24
 */
public interface QuestionService {

    /**
     * 创建问题
     * @param createParamVO 问题参数
     * @return
     */
    ResultVO<Void> create(CreateParamVO createParamVO);
}
