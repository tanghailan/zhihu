package com.coderman.zhihu.controller.question;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import com.coderman.zhihu.service.question.QuestionService;
import com.coderman.zhihu.vo.question.QuestionParamVO;
import com.coderman.zhihu.vo.question.QuestionQueryVO;
import com.coderman.zhihu.vo.question.QuestionVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

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
    public ResultVO<Void> create(@RequestBody @ApiIgnore QuestionParamVO questionParamVO) {

        return this.questionService.create(questionParamVO);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "查询列表")
    @GetMapping(value = "/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", paramType = SwaggerConstant.PARAM_FORM, value = "当前页", dataType = SwaggerConstant.DATA_INT, required = true),
            @ApiImplicitParam(name = "pageSize", paramType = SwaggerConstant.PARAM_FORM, value = "每页大小", dataType = SwaggerConstant.DATA_INT, required = true),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "total"}),
            @ApiReturnParam(name = "ResourceVO", value = {"resourceName", "resourceUrl", "resourceDomain", "createTime", "updateTime", "methodType"})
    })
    public ResultVO<PageVO<List<QuestionVO>>> page(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                                   @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize, QuestionQueryVO queryVO) {
        return this.questionService.page(currentPage, pageSize, queryVO);
    }


}
