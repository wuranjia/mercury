package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.Store;
import com.hy.lang.mercury.resource.req.StoreReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface StoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);

    int countByParams(StoreReq req);

    List<Store> selectByParams(StoreReq req);

    Store selectByOrderId(@Param("orderId") Long orderId,@Param("storeType") String storeType);

    int countIn(Long userId);

    int countOut(Long userId);
}