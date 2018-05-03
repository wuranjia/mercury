package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.dao.OrderMapper;
import com.hy.lang.mercury.pojo.Order;
import com.hy.lang.mercury.pojo.enums.OrderStatus;
import com.hy.lang.mercury.resource.req.OrderReq;
import com.hy.lang.mercury.resource.req.PayReq;
import com.hy.lang.mercury.resource.req.TransReq;
import com.hy.lang.mercury.service.OrderAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService implements OrderAble {
    @Autowired
    private OrderMapper orderMapper;

    //我的买入订单列表
    @Override
    public PageList<Order> buyList(OrderReq req) {
        int total = orderMapper.countByParams(req);
        List<Order> list = orderMapper.selectByParams(req);
        PageList<Order> pageList = new PageList<Order>();
        pageList.setCurrent(req.getPage());
        pageList.setPageSize(req.getLimit());
        pageList.setDraw(req.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

    //我的卖出订单列表
    @Override
    public PageList<Order> sellList(OrderReq req) {
        return buyList(req);//参数不同而已
    }

    @Override
    public Order detail(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    //下单接口
    @Override
    public Order generate(Order order) {
        order.setMemo(Constants.NVL);
        order.setCreatedBy(Constants.SYS);
        order.setCreatedTime(new Date());
        order.setUpdatedBy(Constants.SYS);
        order.setUpdatedTime(new Date());

        BigDecimal total = order.getPrice().multiply(new BigDecimal(order.getNum()));
        order.setTotal(total);

        order.setStatus(Long.valueOf(OrderStatus.已下单.getCode()));

        Order entity = orderMapper.insertOrder(order);
        return entity;
    }

    //支付请求
    @Override
    public Order pay(PayReq req) {
        return null;
    }

    //支付回调
    @Override
    public Order payCallBack(PayReq req) {
        return null;
    }

    //填写发货信息
    @Override
    public Order transInfo(TransReq req) {
        Order order = orderMapper.selectByPrimaryKey(req.getOrderId());
        order.setTransNum(req.getTransNum());
        order.setTransFee(req.getTransFee());
        order.setTransAddress(req.getTransAddress());
        order.setTransStatus(req.getTransStatus());

        orderMapper.updateTrans(order);

        return order;
    }


}
