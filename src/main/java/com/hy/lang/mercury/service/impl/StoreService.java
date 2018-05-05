package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.dao.OrderMapper;
import com.hy.lang.mercury.dao.StoreDetailMapper;
import com.hy.lang.mercury.dao.StoreMapper;
import com.hy.lang.mercury.pojo.Store;
import com.hy.lang.mercury.pojo.StoreDetail;
import com.hy.lang.mercury.pojo.enums.TransStatus;
import com.hy.lang.mercury.resource.req.StoreReq;
import com.hy.lang.mercury.service.StoreAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 入库，出库记录
 */
@Service
public class StoreService implements StoreAble {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreDetailMapper storeDetailMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageList<Store> inList(StoreReq req) {
        int total = storeMapper.countByParams(req);
        List<Store> list = storeMapper.selectByParams(req);
        PageList<Store> pageList = new PageList<Store>();
        pageList.setCurrent(req.getPage());
        pageList.setPageSize(req.getLimit());
        pageList.setDraw(req.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

    @Override
    public PageList<Store> outList(StoreReq req) {
        return inList(req);
    }

    @Override
    public List<StoreDetail> detailList(StoreReq req) {
        List<StoreDetail> details = storeDetailMapper.selectByStoreId(req.getStoreId());
        return details;
    }

    @Override
    public int generate(Store store) {
        store.setMemo(Constants.NVL);
        store.setCreatedBy(Constants.SYS);
        store.setCreatedTime(new Date());
        store.setUpdatedBy(Constants.SYS);
        store.setUpdatedTime(new Date());
        return storeMapper.insert(store);
    }

    @Override
    public int delete(StoreReq req) {
        Store store = storeMapper.selectByPrimaryKey(req.getStoreId());
        storeMapper.deleteByPrimaryKey(req.getStoreId());
        storeDetailMapper.deleteByStoreId(req.getStoreId());

        Long orderId = store.getOrderId();
        orderMapper.updateTransStatus(orderId,
                store.getStoreType().equals(TransStatus.已发货_待收货.name()) ? TransStatus.代发货.name() : TransStatus.代发货.name());
        return 0;
    }

    @Override
    public String export(StoreReq req) {
        return null;
    }
}
