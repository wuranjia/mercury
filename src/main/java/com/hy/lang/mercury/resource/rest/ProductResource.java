package com.hy.lang.mercury.resource.rest;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.entity.ResponseEntity;
import com.hy.lang.mercury.pojo.Product;
import com.hy.lang.mercury.service.ProductAble;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * 产品相关
 */
@RestController
@RequestMapping(value = "/product")
public class ProductResource extends BaseResource {

    private final ProductAble productService;

    public ProductResource(ProductAble productService) {
        this.productService = productService;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String listDeliver(
            @RequestParam int limit,
            @RequestParam @NotNull Long userId,
            @RequestParam int page,
            @RequestParam int draw,
            @RequestParam String type
    ) {
        try {
            PageList<Product> list = new PageList<Product>();
            list.setItems(new ArrayList<Product>());
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }
}
