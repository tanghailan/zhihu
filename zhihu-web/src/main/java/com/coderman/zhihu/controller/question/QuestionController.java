package com.coderman.zhihu.controller.question;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.constant.SwaggerConstant;
import com.coderman.zhihu.service.question.QuestionService;
import com.coderman.zhihu.vo.question.CreateParamVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author coderman
 * @Title: 问题接口
 * @Description: TOD
 * @date 2022/5/2722:04
 */
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "创建问题")
    @PostMapping(value = "/create")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionTitle", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_STRING, value = "问题标题"),
            @ApiImplicitParam(name = "questionDesc", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_STRING, value = "问题概述"),
            @ApiImplicitParam(name = "isAnonymous", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_STRING, value = "问题概述"),
            @ApiImplicitParam(name = "topicIdList", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_OBJECT, value = "话题id")
    })
    public ResultVO<Void> create(@RequestBody @ApiIgnore CreateParamVO createParamVO) {

        return this.questionService.create(createParamVO);
    }





}
