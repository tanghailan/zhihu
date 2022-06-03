package com.coderman.zhihu.controller.topic;

import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.constant.SwaggerConstant;
import com.coderman.zhihu.service.topic.TopicService;
import com.coderman.zhihu.vo.topic.TopicParamVO;
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
 * @Title: 话题接口
 * @Description: TOD
 * @date 2022/5/2722:04
 */
@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    @Resource
    private TopicService topicService;


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "创建话题")
    @PostMapping(value = "/create")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topicName", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_STRING, value = "话题名称"),
            @ApiImplicitParam(name = "topicLogo", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_STRING, value = "话题封面"),
            @ApiImplicitParam(name = "topicDesc", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_STRING, value = "话题简述"),
    })
    public ResultVO<Void> create(@RequestBody @ApiIgnore TopicParamVO topicParamVO) {

        return this.topicService.create(topicParamVO);
    }




}
