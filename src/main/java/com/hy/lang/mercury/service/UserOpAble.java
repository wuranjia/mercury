package com.hy.lang.mercury.service;

import com.hy.lang.mercury.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserOpAble {

    int insert(User user);

    int update(User user);

    int delete(Long userId);

    User query(Long userId);

    List<User> list(Map<String, Object> map);

    User queryByNameAndPwd(String userName, String passWord);
}
