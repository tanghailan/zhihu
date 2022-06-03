package com.coderman.zhihu.dao.user;

import com.coderman.mybatis.dao.BaseDAO;
import com.coderman.zhihu.model.user.UserExample;
import com.coderman.zhihu.model.user.UserModel;
import org.apache.ibatis.annotations.Param;

public interface UserDAO extends BaseDAO<UserModel, UserExample> {


    /**
     * 根据邮箱查询用户信息
     *
     * @param email 邮箱
     * @return
     */
    UserModel selectByEmail(@Param(value = "email") String email);


    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    UserModel selectByUsername(@Param(value = "username") String username);
}