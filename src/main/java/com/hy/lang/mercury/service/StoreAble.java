package com.hy.lang.mercury.service;

import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.pojo.Store;
import com.hy.lang.mercury.pojo.StoreDetail;
import com.hy.lang.mercury.resource.req.StoreReq;

import java.util.List;

public interface StoreAble {
    //入库列表
    PageList<Store> inList(StoreReq req);

    //出库列表
    PageList<Store> outList(StoreReq req);

    //导入卡详情信息，关联订单
    PageList<StoreDetail>  detailList(StoreReq req);

    int generate(Store store);

    //撤回
    int delete(StoreReq req);

    String export(StoreReq req);

    void insertDetail(String path,String storeId);
}
