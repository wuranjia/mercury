package com.hy.lang.mercury.service;

import com.hy.lang.mercury.common.McException;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.entity.SessionContext;
import com.hy.lang.mercury.pojo.User;
import com.hy.lang.mercury.resource.req.UserInfoReq;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserAble {

    /**
     * 获取session信息
     *
     * @param userId
     * @return
     */
    SessionContext getSessionContext(Long userId) throws McException;

    /**
     * 用户是否在线
     *
     * @param userId
     * @return
     */
    boolean isOnline(Long userId) throws McException;

    boolean validate(String userName, String passWord) throws McException;

    SessionContext login(String userName, String passWord) throws McException;

    void loginOut(Long userId) throws McException;


    SessionContext resetPwd(@NotNull String userName, @NotNull String passWord, @NotNull String newPassWord);

    PageList<User> list(UserInfoReq user);

    User findOne(Long queryUserId);

    List<User> getAllChildren(Long userId);

    List<Long> getAllChildrenId(Long userId);

}
