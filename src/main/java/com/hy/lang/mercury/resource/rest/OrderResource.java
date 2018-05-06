package com.hy.lang.mercury.resource.rest;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.entity.ResponseEntity;
import com.hy.lang.mercury.pojo.Order;
import com.hy.lang.mercury.resource.req.OrderReq;
import com.hy.lang.mercury.resource.req.TransReq;
import com.hy.lang.mercury.service.OrderAble;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单相关
 */
@RequestMapping(value = "/order")
@RestController
public class OrderResource extends BaseResource {

    private final OrderAble orderService;

    public OrderResource(OrderAble orderService) {
        this.orderService = orderService;
    }


    @RequestMapping(path = "/generate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String generate(
            @RequestBody OrderReq req) {
        try {
            Order order = new Order(req);
            Order resp = orderService.generate(order);
            return JSON.toJSONString(ResponseEntity.createBySuccess(resp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/buy/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String buyList(
            @RequestBody OrderReq req) {
        try {
            PageList<Order> resp = orderService.buyList(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess(resp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/sell/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sellList(
            @RequestBody OrderReq req) {
        try {
            PageList<Order> resp = orderService.sellList(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess(resp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/pay/confirm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String payConfirm(
            @RequestBody OrderReq req
    ) {
        try {
            orderService.payConfirm(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/trans/confirm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String transConfirm(
            @RequestBody OrderReq req
    ) {
        try {
            orderService.transConfirm(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/trans/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String transEdit(
            @RequestBody TransReq req) {
        try {
            Order resp = orderService.transInfo(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess(resp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }
}
