package com.hy.lang.mercury.resource.rest;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.client.cmiot.ClientProxy;
import com.hy.lang.mercury.client.cmiot.ScheduleService;
import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.entity.ResponseEntity;
import com.hy.lang.mercury.common.utils.DateTimeUtils;
import com.hy.lang.mercury.pojo.SimBase;
import com.hy.lang.mercury.resource.req.SimBaseReq;
import com.hy.lang.mercury.resource.resp.SimBaseResp;
import com.hy.lang.mercury.resource.resp.SimFlowMonthResp;
import com.hy.lang.mercury.resource.resp.SimMatrixResp;
import com.hy.lang.mercury.service.SimAble;
import com.hy.lang.mercury.service.UserAble;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/sim")
public class SimResource extends BaseResource {

    private final SimAble simService;

    private final ClientProxy clientProxy;

    private final ScheduleService scheduleService;

    private final UserAble userService;

    public SimResource(SimAble simService, ClientProxy clientProxy, ScheduleService scheduleService, UserAble userService) {
        this.simService = simService;
        this.clientProxy = clientProxy;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(
            @RequestParam int limit,
            @RequestParam @NotNull Long userId,
            @RequestParam int page,
            @RequestParam String sim,
            @RequestParam String iccid,
            @RequestParam int draw,
            @RequestParam Long supplier
    ) {
        try {
            SimBaseReq simBaseReq = new SimBaseReq();
            //simBaseReq.setUserId(userId);
            if (supplier != null && supplier == -99L) {
                supplier = null;
            }
            simBaseReq.setSupplier(supplier);
            simBaseReq.setUserIds(userService.getAllChildrenId(userId));
            simBaseReq.setPage(page);
            simBaseReq.setLimit(limit);
            simBaseReq.setDraw(draw);
            simBaseReq.setSim(StringUtils.isBlank(sim) ? null : Long.valueOf(sim));
            simBaseReq.setIccid(StringUtils.isBlank(iccid) ? null : iccid);
            PageList<SimBase> list = simService.list(simBaseReq);
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }


    @RequestMapping(path = "/import", method = RequestMethod.GET)
    public String importCsv(@RequestParam Long userId) {
        try {
            String path = "/root/sim";
            simService.importCsv(path, userId);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test(@RequestParam String sim) {
        try {
            List list = clientProxy.test(sim);
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/test2", method = RequestMethod.GET)
    public String test(@RequestParam String sim, @RequestParam String queryDate) {
        try {
            List list = clientProxy.test2(sim, queryDate);
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/refresh", method = RequestMethod.GET)
    public String refresh(@RequestParam String sim, @RequestParam String queryDate) {
        try {
            scheduleService.doingBatch(sim, queryDate);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }

    }

    @RequestMapping(path = "/assign", method = RequestMethod.POST)
    public String assign(@RequestParam String assignUserId, @RequestParam String cardArray) {
        try {
            String[] cards = StringUtils.split(cardArray, Constants.逗号);
            simService.assign(cards, assignUserId);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/matrix", method = RequestMethod.GET)
    public String matrix(@RequestParam Long userId) {
        try {
            SimMatrixResp simMatrixResp = simService.matrix(userId);
            return JSON.toJSONString(ResponseEntity.createBySuccess(simMatrixResp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/index/flow/list", method = RequestMethod.GET)
    public String indexFlowUse(
            @RequestParam int limit,
            @RequestParam @NotNull Long userId,
            @RequestParam int page,
            @RequestParam int draw
    ) {
        try {
            SimBaseReq simBaseReq = new SimBaseReq();
            simBaseReq.setUserId(userId);
            simBaseReq.setPage(page);
            simBaseReq.setLimit(limit);
            simBaseReq.setDraw(draw);
            PageList<SimBase> pageList = simService.indexFlowUse(simBaseReq);
            return JSON.toJSONString(ResponseEntity.createBySuccess(pageList));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/index/flow/month", method = RequestMethod.GET)
    public String indexFlowMonth(
            @RequestParam @NotNull Long userId
    ) {
        try {
            String[] month = DateTimeUtils.getDaysOfMonth();
            SimFlowMonthResp simFlowMonthResp = new SimFlowMonthResp();
            int[] flow = new int[month.length];
            for (int i = 0; i < flow.length; i++) {
                flow[i] = 0;
            }
            simFlowMonthResp.setMonth(month);
            simFlowMonthResp.setFlow(flow);
            return JSON.toJSONString(ResponseEntity.createBySuccess(simFlowMonthResp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }

    }

    @RequestMapping(path = "/index/card", method = RequestMethod.GET)
    public String indexCardInfo(
            @RequestParam @NotNull Long userId
    ) {
        try {
            SimBaseResp simBaseResp = simService.indexCardInfo(userId);
            return JSON.toJSONString(ResponseEntity.createBySuccess(simBaseResp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }


}
