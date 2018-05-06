package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.StoreDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface StoreDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StoreDetail record);

    int insertSelective(StoreDetail record);

    StoreDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StoreDetail record);

    int updateByPrimaryKey(StoreDetail record);

    List<StoreDetail> selectByStoreId(Long storeId);

    void deleteByStoreId(Long storeId);

    void batchInsert(List<StoreDetail> inserts);
}