package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.MenuRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MenuRefMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MenuRef record);

    int insertSelective(MenuRef record);

    MenuRef selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuRef record);

    int updateByPrimaryKey(MenuRef record);

    List<MenuRef> selectByUserId(@Param("userId") Long userId);
}