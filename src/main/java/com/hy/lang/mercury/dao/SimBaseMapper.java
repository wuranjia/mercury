package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.SimBase;
import com.hy.lang.mercury.resource.req.SimBaseReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface SimBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SimBase record);

    int insertSelective(SimBase record);

    SimBase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SimBase record);

    int updateByPrimaryKey(SimBase record);

    int countByParams(SimBaseReq req);

    List<SimBase> selectByParams(SimBaseReq req);

    void batchInsert(List<SimBase> list);

    int count(Map map);

    List<SimBase> selectByParamsOrderByFlow(SimBaseReq req);

    int countByCal(@Param("calDay") String calDay);

    List<SimBase> selectByCal(@Param("calDay") String calDay);

    int updateCal(Map map);
}