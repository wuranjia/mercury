package com.hy.lang.mercury.service.impl;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.dao.*;
import com.hy.lang.mercury.pojo.Product;
import com.hy.lang.mercury.pojo.SimBase;
import com.hy.lang.mercury.pojo.Store;
import com.hy.lang.mercury.pojo.StoreDetail;
import com.hy.lang.mercury.pojo.enums.TransStatus;
import com.hy.lang.mercury.resource.req.MatrixReq;
import com.hy.lang.mercury.resource.req.StoreReq;
import com.hy.lang.mercury.service.StoreAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

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

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SimBaseMapper simBaseMapper;

    @Override
    public PageList<Store> inList(StoreReq req) {
        System.out.println(JSON.toJSONString(req));
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
    public PageList<StoreDetail> detailList(StoreReq req) {
        List<StoreDetail> details = storeDetailMapper.selectByStoreId(req.getStoreId());
        PageList<StoreDetail> result = new PageList<StoreDetail>();
        result.setItems(details);
        result.setDraw(req.getDraw());
        result.setTotal(details.size());
        result.setCurrent(1);
        result.setPageSize(details.size());
        result.setPageTotal(1L);
        return result;
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

    @Override
    public void insertDetail(String path, String storeId) {
        //先清理数据
        storeDetailMapper.deleteByStoreId(Long.valueOf(storeId));
        Store store = storeMapper.selectByPrimaryKey(Long.valueOf(storeId));
        Long orderId = store.getOrderId();
        readFromFile(path, store.getId(), orderId);
    }

    //同步到my-sim-card中
    @Override
    public void sync(StoreReq req) {
        Long storeId = req.getStoreId();
        Store store = storeMapper.selectByPrimaryKey(storeId);
//        Order order = orderMapper.selectByPrimaryKey(store.getOrderId());
        Product product = productMapper.selectByPrimaryKey(store.getProductId());
        List<StoreDetail> list = storeDetailMapper.selectByStoreId(storeId);
        List<SimBase> inserts = new ArrayList<SimBase>();
        for (StoreDetail detail : list) {
            SimBase simBase = new SimBase(Long.valueOf(detail.getSimId()),
                    detail.getIccid(), detail.getImsi(),
                    Constants.中国移动, product.getName(), req.getBuyer()
            );
            simBaseMapper.deleteBySimId(Long.valueOf(detail.getSimId()));
            inserts.add(simBase);
        }
        simBaseMapper.batchInsert(inserts);
    }

    @Override
    public Map<String, String> matrix(MatrixReq req) {
        Long userId = req.getUserId();
        int in_count = storeMapper.countIn(userId);
        int out_count = storeMapper.countOut(userId);
        Map<String,String> result = new HashMap<String,String>();
        result.put("in",in_count+"");
        result.put("out",out_count+"");
        return result;
    }

    /**
     * 文件格式
     * sim+iccid+imsi
     *
     * @param path
     * @param storeId
     * @param orderId
     * @return
     */
    public void readFromFile(String path, Long storeId, Long orderId) {
        File file = new File(path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            List<StoreDetail> inserts = new ArrayList<StoreDetail>();
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String simStr = item[0];
                Long sim = Long.valueOf(simStr);
                String iccid = item[1];
                String imsi = item[2];
                StoreDetail detail = new StoreDetail(sim, iccid, imsi, orderId, storeId);
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值

                inserts.add(detail);
                if (inserts.size() >= 1000) {
                    storeDetailMapper.batchInsert(inserts);
                    inserts.clear();
                }
            }
            if (inserts.size() != 0) {
                storeDetailMapper.batchInsert(inserts);
            }
            System.out.println("insert orderId = " + orderId + " total records = " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
