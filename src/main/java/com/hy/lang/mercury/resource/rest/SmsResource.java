package com.hy.lang.mercury.resource.rest;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.entity.ResponseEntity;
import com.hy.lang.mercury.pojo.SmsDeliver;
import com.hy.lang.mercury.pojo.SmsInfo;
import com.hy.lang.mercury.pojo.SmsView;
import com.hy.lang.mercury.resource.req.MatrixReq;
import com.hy.lang.mercury.resource.req.SmsDeliverReq;
import com.hy.lang.mercury.resource.req.SmsInfoReq;
import com.hy.lang.mercury.resource.req.SmsViewReq;
import com.hy.lang.mercury.resource.resp.MatrixResp;
import com.hy.lang.mercury.service.SmsAble;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/sms")
public class SmsResource extends BaseResource {

    private final SmsAble smsService;

    public SmsResource(SmsAble smsService) {
        this.smsService = smsService;
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public String send(
            @RequestParam @NotNull String receiveIds,
            @RequestParam @NotNull String smsContent,
            @RequestParam @NotNull Long userId
    ) {
        try {
            smsService.sendSms(receiveIds, smsContent, userId);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(
            @RequestParam int limit,
            @RequestParam @NotNull Long userId,
            @RequestParam int page,
            @RequestParam String sim,
            @RequestParam int draw
    ) {
        try {
            SmsInfoReq smsInfoReq = new SmsInfoReq();
            smsInfoReq.setUserId(userId);
            smsInfoReq.setPage(page);
            smsInfoReq.setLimit(limit);
            smsInfoReq.setDraw(draw);
            smsInfoReq.setReceiveInfo(StringUtils.isBlank(sim) ? null : sim);
            PageList<SmsInfo> list = smsService.listSms(smsInfoReq, userId);
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/list/deliver", method = RequestMethod.GET)
    public String listDeliver(
            @RequestParam int limit,
            @RequestParam @NotNull Long userId,
            @RequestParam int page,
            @RequestParam String sim,
            @RequestParam int draw
    ) {
        try {
            SmsDeliverReq smsInfoReq = new SmsDeliverReq();
            smsInfoReq.setUserId(userId);
            smsInfoReq.setPage(page);
            smsInfoReq.setLimit(limit);
            smsInfoReq.setSrcTermid(StringUtils.isBlank(sim) ? null : sim);
            smsInfoReq.setDraw(draw);
            PageList<SmsDeliver> list = smsService.listSms(smsInfoReq, userId);
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/list/view", method = RequestMethod.GET)
    public String listView(
            @RequestParam int limit,
            @RequestParam @NotNull Long userId,
            @RequestParam int page,
            @RequestParam String sim,
            @RequestParam int draw) {
        try {
            SmsViewReq smsViewReq = new SmsViewReq();
            smsViewReq.setUserId(userId);
            smsViewReq.setDraw(draw);
            smsViewReq.setPage(page);
            smsViewReq.setLimit(limit);
            smsViewReq.setSim(sim);
            PageList<SmsView> list = smsService.listView(smsViewReq);
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/matrix", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String matrix(@RequestBody MatrixReq req) {
        try {
            Map<String,String[]> resp = smsService.matrix(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess(resp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }
}
