package com.coderman.zhihu.util;

import com.coderman.api.vo.PageVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author coderman
 * @Title: 分页工具类
 * @Description: TOD
 * @date 2022/6/311:30
 */
public class PageUtil {


    /**
     * 获取分页
     *
     * @param dataList
     * @param <T>
     * @return
     */
    public static <T> PageVO<List<T>> getPageVO(List<T> dataList) {
        PageInfo<T> pageInfo = new PageInfo<>(dataList);
        return new PageVO<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
