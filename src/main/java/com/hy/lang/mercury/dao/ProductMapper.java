package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.Product;
import com.hy.lang.mercury.resource.req.ProductReq;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    int countByParams(ProductReq req);

    List<Product> selectByParams(ProductReq req);

    void batchInsert(List<Product> list);
}