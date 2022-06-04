package com.coderman.zhihu.controller.topic;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import com.coderman.zhihu.service.topic.TopicService;
import com.coderman.zhihu.vo.topic.TopicParamVO;
import com.coderman.zhihu.vo.topic.TopicVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

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
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> create(@RequestBody @ApiIgnore TopicParamVO topicParamVO) {

        return this.topicService.create(topicParamVO);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET,value = "搜索话题")
    @GetMapping(value = "/search")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",paramType = SwaggerConstant.PARAM_QUERY,dataType = SwaggerConstant.DATA_STRING,value = "关键字")
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<List<TopicVO>> search(String keyword){
        return this.topicService.search(keyword);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET,value = "查询列表")
    @GetMapping(value = "/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",paramType = SwaggerConstant.PARAM_FORM,value = "当前页",dataType = SwaggerConstant.DATA_INT,required = true),
            @ApiImplicitParam(name = "pageSize",paramType = SwaggerConstant.PARAM_FORM,value = "每页大小",dataType = SwaggerConstant.DATA_INT,required = true),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO",value = {"dataList", "total"}),
    })
    public ResultVO<PageVO<List<TopicVO>>> page(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                                @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        return this.topicService.page(currentPage, pageSize);
    }


}
