package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.Order;
import com.hy.lang.mercury.resource.req.OrderReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int insertOrder(Order order);

    void updateTrans(Order order);

    int countByParams(OrderReq req);

    List<Order> selectByParams(OrderReq req);

    void updateTransStatus(@Param("id") Long orderId, @Param("transStatus") String transStatus);

    int updateOrderStatus(@Param("id") Long orderId, @Param("status") Long status);
}