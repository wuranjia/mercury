package com.hy.lang.mercury.service;

import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.pojo.Product;
import com.hy.lang.mercury.resource.req.ProductReq;

public interface ProductAble {
    PageList<Product> list(ProductReq productReq);

    Product detail(Long productId);

    void importProduct(String path);
}
