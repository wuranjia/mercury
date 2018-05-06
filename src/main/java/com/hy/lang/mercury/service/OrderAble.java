package com.hy.lang.mercury.service;

import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.pojo.Order;
import com.hy.lang.mercury.resource.req.OrderReq;
import com.hy.lang.mercury.resource.req.PayReq;
import com.hy.lang.mercury.resource.req.TransReq;

public interface OrderAble {
    //我的买入订单列表
    PageList<Order> buyList(OrderReq req);

    //我的卖出订单列表
    PageList<Order> sellList(OrderReq req);

    Order detail(Long orderId);

    //下单接口
    Order generate(Order order);

    //支付请求
    Order pay(PayReq req);

    //支付回调
    Order payCallBack(PayReq req);

    //填写发货信息
    Order transInfo(TransReq req);

    int payConfirm(OrderReq req);

    int transConfirm(OrderReq req);
}
