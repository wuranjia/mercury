package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(@Param("userId") Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User queryByNameAndPwd(@Param("userName") String userName, @Param("loginPwd") String passWord);

    List<User> selectByParams(Map<String, Object> map);

    int countByParams(Map<String, Object> map);

    List<User> selectByParentId(Long userId);
}