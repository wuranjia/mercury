package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.dao.ProductMapper;
import com.hy.lang.mercury.pojo.Product;
import com.hy.lang.mercury.pojo.SimBase;
import com.hy.lang.mercury.resource.req.ProductReq;
import com.hy.lang.mercury.resource.req.SimBaseReq;
import com.hy.lang.mercury.service.ProductAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements ProductAble {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageList<Product> list(ProductReq req) {
        int total = productMapper.countByParams(req);
        List<Product> list = productMapper.selectByParams(req);
        PageList<Product> pageList = new PageList<Product>();
        pageList.setCurrent(req.getPage());
        pageList.setPageSize(req.getLimit());
        pageList.setDraw(req.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

    @Override
    public Product detail(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return product;
    }

    @Override
    public void importProduct(String path) {
        File file = new File(path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            List<Product> inserts = new ArrayList<Product>();
            while ((line = reader.readLine()) != null) {

                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String productName = item[0];
                String type = item[1];
                String flow = item[2];
                String price = item[3];
                Product product = new Product(productName,type,flow,price);
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                inserts.add(product);
/*                List<SimBase> list = simBaseMapper.selectByParams(req);
                if (list != null && !list.isEmpty()) {
                    for (SimBase base : list) {
                        simBaseMapper.deleteByPrimaryKey(base.getId());
                    }
                }*/
                //simBaseMapper.insert(simBase);
                if (inserts.size() >= 1000) {
                    productMapper.batchInsert(inserts);
                    inserts.clear();
                }
            }
            if (inserts.size() != 0) {
                productMapper.batchInsert(inserts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
