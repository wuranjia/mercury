package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.dao.OrderMapper;
import com.hy.lang.mercury.dao.ProductMapper;
import com.hy.lang.mercury.dao.StoreDetailMapper;
import com.hy.lang.mercury.dao.StoreMapper;
import com.hy.lang.mercury.pojo.Order;
import com.hy.lang.mercury.pojo.Product;
import com.hy.lang.mercury.pojo.Store;
import com.hy.lang.mercury.pojo.StoreDetail;
import com.hy.lang.mercury.pojo.enums.OrderStatus;
import com.hy.lang.mercury.pojo.enums.StoreType;
import com.hy.lang.mercury.pojo.enums.TransStatus;
import com.hy.lang.mercury.resource.req.OrderReq;
import com.hy.lang.mercury.resource.req.PayReq;
import com.hy.lang.mercury.resource.req.TransReq;
import com.hy.lang.mercury.service.OrderAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderAble {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreDetailMapper storeDetailMapper;

    //我的买入订单列表
    @Override
    public PageList<Order> buyList(OrderReq req) {
        int total = orderMapper.countByParams(req);
        List<Order> list = orderMapper.selectByParams(req);
        List<Order> r = new ArrayList<Order>();
        for (Order o : list) {
            Long productId = o.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            o.setProductName(product.getName());
            r.add(o);
        }
        PageList<Order> pageList = new PageList<Order>();
        pageList.setCurrent(req.getPage());
        pageList.setPageSize(req.getLimit());
        pageList.setDraw(req.getDraw());
        pageList.setTotal(total);
        pageList.setItems(r);
        return pageList;
    }

    //我的卖出订单列表
    @Override
    public PageList<Order> sellList(OrderReq req) {
        req.setStatus(OrderStatus.已下单.getCode());
        return buyList(req);//参数不同而已
    }

    @Override
    public Order detail(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    //下单接口
    @Override
    public Order generate(Order order) {
        orderMapper.insertOrder(order);
        return order;
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
        order.setId(req.getOrderId());
        order.setTransNum(req.getTransNum());
        order.setTransStatus(TransStatus.已发货_待收货.name());
        order.setStatus(OrderStatus.已发货.getCode());
        order.setTransAddress(req.getTransAddress());
        orderMapper.updateTrans(order);

        //生成出库单
        Product product = productMapper.selectByPrimaryKey(order.getProductId());
        Store store = new Store(order, product, StoreType.出库.name());
        Store q = storeMapper.selectByOrderId(order.getId(), StoreType.出库.name());
        if (q == null) {
            storeMapper.insert(store);
        } else {
            q.setTransNum(req.getTransNum());
            storeMapper.updateByPrimaryKey(q);
        }

        return order;
    }

    @Override
    public int payConfirm(OrderReq req) {
        Long orderId = req.getOrderId();
        Long status = req.getStatus();
        if (status.equals(OrderStatus.已下单.getCode())) {
            int i = orderMapper.updateOrderStatus(orderId, OrderStatus.已支付.getCode());
            return i;
        }
        return 0;
    }

    @Override
    public int transConfirm(OrderReq req) {
        Long orderId = req.getOrderId();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        Long status = req.getStatus();
        if (status.equals(OrderStatus.已发货.getCode())) {
            storeInGenerate(order);
            return 1;
        }
        return 0;
    }


    public Order storeInGenerate(Order order) {
        order.setTransStatus(TransStatus.已收货.name());
        order.setStatus(OrderStatus.已收货.getCode());
        orderMapper.updateTrans(order);

        //生成出库单
        Product product = productMapper.selectByPrimaryKey(order.getProductId());
        Store store = new Store(order, product, StoreType.入库.name());
        Store q = storeMapper.selectByOrderId(order.getId(), StoreType.入库.name());
        if (q == null) {
            storeMapper.insert(store);
        } else {
            q.setTransNum(order.getTransNum());
            storeMapper.updateByPrimaryKey(q);
        }

        //detail 导入
        Store q2 = storeMapper.selectByOrderId(order.getId(), StoreType.入库.name());
        Long storeId = q2.getId();
        storeDetailMapper.deleteByStoreId(storeId);
        Store out = storeMapper.selectByOrderId(order.getId(), StoreType.出库.name());
        List<StoreDetail> outs = storeDetailMapper.selectByStoreId(out.getId());
        List<StoreDetail> ins = new ArrayList<StoreDetail>();
        for (StoreDetail detail : outs) {
            StoreDetail in = detail.clone();
            in.setId(null);
            in.setStoreId(storeId);
            ins.add(in);
        }
        storeDetailMapper.batchInsert(ins);
        return order;
    }
}
