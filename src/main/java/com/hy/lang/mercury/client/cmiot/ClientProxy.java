package com.hy.lang.mercury.client.cmiot;

import cn.iot.api.sdk.CmiotClient;
import cn.iot.api.sdk.DefaultCmiotClient;
import cn.iot.api.sdk.RequestMethod;
import cn.iot.api.sdk.exception.ApiException;
import cn.iot.api.sdk.request.*;
import cn.iot.api.sdk.response.*;
import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.common.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ClientProxy {

    private static final Logger logger = LoggerFactory.getLogger(ClientProxy.class);


    private static final String url = "http://183.230.96.66:8087";

    private static final String APPID = "G18X844L";

    private static final String ECID = "250212667250250000";

    private static final String PASSWORD = "YYDZ37";


    public List<Object> test2(String msisdn, String queryDate) {
        List<Object> list = new ArrayList<Object>();

        list.add(集团用户数查询(queryDate));

        list.add(短信批量查询(msisdn, queryDate));
        list.add(流量信息批量查询(msisdn, queryDate));
        //list.add(用户当月短信查询(msisdn));
        //list.add(用户短信使用查询(msisdn));
        //list.add(套餐内GPRS流量使用情况实时查询(msisdn));
        list.add(物联卡单日GPRS使用量查询(msisdn, queryDate));
        return list;
    }


    public List<Object> test(String msisdn) throws IOException, ApiException {
        //初始化一个CmiotClient实现类
        List<Object> list = new ArrayList<Object>();
        list.add(在线信息实时查询(msisdn));
        list.add(用户状态信息实时查询(msisdn));
        list.add(码号信息查询(msisdn));
        list.add(开关机信息实时查询(msisdn));
        list.add(物联卡短信服务开通查询(msisdn));
        list.add(物联卡GPRS服务开通查询(msisdn));
        list.add(物联卡APN服务开通查询(msisdn));
        list.add(物联卡生命周期查询(msisdn));
        String queryDate = DateTimeUtils.dateToString(new Date(), DateTimeUtils.yyyyMMdd);
        list.add(集团用户数查询(queryDate));
        list.add(物联卡生命周期查询(msisdn));
        list.add(欠费停机用户批量查询(1, 10));
        //list.add(集团各生命周期物联卡数量查询(msisdn));
        //list.add(集团异常状态物联卡数据量查询(msisdn));
        //list.add(集团GPRS在线物联卡数量查询(msisdn));

        list.add(用户余额信息实时查询(msisdn));
        list.add(用户当月GPRS查询(msisdn));
        list.add(短信批量查询(msisdn, queryDate));
        list.add(流量信息批量查询(msisdn, queryDate));
        //list.add(用户当月短信查询(msisdn));
        //list.add(用户短信使用查询(msisdn));
        //list.add(套餐内GPRS流量使用情况实时查询(msisdn));
        list.add(物联卡单日GPRS使用量查询(msisdn, queryDate));
        list.add(物联卡资费套餐查询(msisdn));
        return list;
    }


    public GprsRealSingleResponse 在线信息实时查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.在线信息实时查询.getUrl());

        GprsRealSingleRequest request = new GprsRealSingleRequest();
        request.setMsisdn(msisdn);//卡号
        request.setAppid(APPID);
        request.setEbid(Command.在线信息实时查询.getCode());
        request.setPassword(PASSWORD);
        //调用CmiotClient实现类中的excute方法，返回一个具体的Response对象，该对象封装了接口返回的数据。
        GprsRealSingleResponse gprsRealTimeInfoResponse = null;
        try {
            gprsRealTimeInfoResponse = client.excute(request, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("ClientProxy" + JSON.toJSONString(gprsRealTimeInfoResponse));
        logger.info("在线信息实时查询" + JSON.toJSONString(gprsRealTimeInfoResponse));
        return gprsRealTimeInfoResponse;
    }

    public UserStatusRealSingleResponse 用户状态信息实时查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.用户状态信息实时查询.getUrl());
        UserStatusRealSingleRequest userStatusRealSingleRequest = new UserStatusRealSingleRequest();
        userStatusRealSingleRequest.setMsisdn(msisdn);
        userStatusRealSingleRequest.setAppid(APPID);
        userStatusRealSingleRequest.setEbid(Command.用户状态信息实时查询.getCode());
        userStatusRealSingleRequest.setPassword(PASSWORD);
        UserStatusRealSingleResponse userStatusRealSingleResponse = null;
        try {
            userStatusRealSingleResponse = client.excute(userStatusRealSingleRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("用户状态信息实时查询" + JSON.toJSONString(userStatusRealSingleResponse));
        return userStatusRealSingleResponse;
    }

    public CardInfoResponse 码号信息查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.码号信息查询.getUrl());
        CardInfoRequest cardInfoRequest = new CardInfoRequest();
        cardInfoRequest.setCard_info(msisdn);
        cardInfoRequest.setType("0");
        cardInfoRequest.setAppid(APPID);
        cardInfoRequest.setEbid(Command.码号信息查询.getCode());
        cardInfoRequest.setPassword(PASSWORD);
        CardInfoResponse cardInfoResponse = null;
        try {
            cardInfoResponse = client.excute(cardInfoRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("码号信息查询" + JSON.toJSONString(cardInfoResponse));
        return cardInfoResponse;
    }

    public OnAndOffRealSingleResponse 开关机信息实时查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.开关机信息实时查询.getUrl());
        OnAndOffRealSingleRequest onAndOffRealSingleRequest = new OnAndOffRealSingleRequest();
        onAndOffRealSingleRequest.setMsisdn(msisdn);
        onAndOffRealSingleRequest.setAppid(APPID);
        onAndOffRealSingleRequest.setEbid(Command.开关机信息实时查询.getCode());
        onAndOffRealSingleRequest.setPassword(PASSWORD);
        OnAndOffRealSingleResponse onAndOffRealSingleResponse = null;
        try {
            onAndOffRealSingleResponse = client.excute(onAndOffRealSingleRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("开关机信息实时查询" + JSON.toJSONString(onAndOffRealSingleResponse));

        return onAndOffRealSingleResponse;
    }

    public QuerySmsOpenStatusResponse 物联卡短信服务开通查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.物联卡短信服务开通查询.getUrl());
        QuerySmsOpenStatusRequest querySmsOpenStatusRequest = new QuerySmsOpenStatusRequest();
        querySmsOpenStatusRequest.setMsisdn(msisdn);
        querySmsOpenStatusRequest.setAppid(APPID);
        querySmsOpenStatusRequest.setEbid(Command.物联卡短信服务开通查询.getCode());
        querySmsOpenStatusRequest.setPassword(PASSWORD);
        QuerySmsOpenStatusResponse querySmsOpenStatusResponse = null;
        try {
            querySmsOpenStatusResponse = client.excute(querySmsOpenStatusRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("物联卡短信服务开通查询" + JSON.toJSONString(querySmsOpenStatusResponse));

        return querySmsOpenStatusResponse;
    }

    public QueryGprsOpenStatusResponse 物联卡GPRS服务开通查询(String msisdn) {

        CmiotClient client = new DefaultCmiotClient(url, Command.物联卡GPRS服务开通查询.getUrl());
        QueryGprsOpenStatusRequest queryGprsOpenStatusRequest = new QueryGprsOpenStatusRequest();
        queryGprsOpenStatusRequest.setMsisdn(msisdn);
        queryGprsOpenStatusRequest.setAppid(APPID);
        queryGprsOpenStatusRequest.setEbid(Command.物联卡GPRS服务开通查询.getCode());
        queryGprsOpenStatusRequest.setPassword(PASSWORD);
        QueryGprsOpenStatusResponse queryGprsOpenStatusResponse = null;
        try {
            queryGprsOpenStatusResponse = client.excute(queryGprsOpenStatusRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("物联卡GPRS服务开通查询" + JSON.toJSONString(queryGprsOpenStatusResponse));

        return queryGprsOpenStatusResponse;
    }

    public QueryApnOpenStatusResponse 物联卡APN服务开通查询(String msisdn) {

        CmiotClient client = new DefaultCmiotClient(url, Command.物联卡APN服务开通查询.getUrl());
        QueryApnOpenStatusRequest queryApnOpenStatusRequest = new QueryApnOpenStatusRequest();
        queryApnOpenStatusRequest.setMsisdn(msisdn);
        queryApnOpenStatusRequest.setAppid(APPID);
        queryApnOpenStatusRequest.setEbid(Command.物联卡APN服务开通查询.getCode());
        queryApnOpenStatusRequest.setPassword(PASSWORD);
        QueryApnOpenStatusResponse queryApnOpenStatusResponse = null;
        try {
            queryApnOpenStatusResponse = client.excute(queryApnOpenStatusRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("物联卡APN服务开通查询" + JSON.toJSONString(queryApnOpenStatusResponse));
        return queryApnOpenStatusResponse;
    }

    public QueryCardLifecycleResponse 物联卡生命周期查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.物联卡生命周期查询.getUrl());
        QueryCardLifecycleRequest queryCardLifecycleRequest = new QueryCardLifecycleRequest();
        queryCardLifecycleRequest.setMsisdn(msisdn);
        queryCardLifecycleRequest.setAppid(APPID);
        queryCardLifecycleRequest.setEbid(Command.物联卡生命周期查询.getCode());
        queryCardLifecycleRequest.setPassword(PASSWORD);
        QueryCardLifecycleResponse queryCardLifecycleResponse = null;
        try {
            queryCardLifecycleResponse = client.excute(queryCardLifecycleRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("物联卡生命周期查询" + JSON.toJSONString(queryCardLifecycleResponse));
        return queryCardLifecycleResponse;
    }

    public GroupUserInfoResponse 集团用户数查询(String yyyyMMdd) {
        CmiotClient client = new DefaultCmiotClient(url, Command.集团用户数查询.getUrl());
        GroupUserInfoRequest groupUserInfoRequest = new GroupUserInfoRequest();
        groupUserInfoRequest.setQuery_date(yyyyMMdd);
        groupUserInfoRequest.setAppid(APPID);
        groupUserInfoRequest.setEbid(Command.集团用户数查询.getCode());
        groupUserInfoRequest.setPassword(PASSWORD);
        GroupUserInfoResponse groupUserInfoResponse = null;
        try {
            groupUserInfoResponse = client.excute(groupUserInfoRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("集团用户数查询" + JSON.toJSONString(groupUserInfoResponse));
        return groupUserInfoResponse;
    }

    public ArrearageUserInfoResponse 欠费停机用户批量查询(int pageNum, int pageSize) {
        CmiotClient client = new DefaultCmiotClient(url, Command.欠费停机用户批量查询.getUrl());
        ArrearageUserInfoRequest arrearageUserInfoRequest = new ArrearageUserInfoRequest();
        arrearageUserInfoRequest.setPageNum(pageNum);
        arrearageUserInfoRequest.setPageSize(pageSize);
        arrearageUserInfoRequest.setAppid(APPID);
        arrearageUserInfoRequest.setEbid(Command.欠费停机用户批量查询.getCode());
        arrearageUserInfoRequest.setPassword(PASSWORD);
        ArrearageUserInfoResponse arrearageUserInfoResponse = null;
        try {
            arrearageUserInfoResponse = client.excute(arrearageUserInfoRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("欠费停机用户批量查询" + JSON.toJSONString(arrearageUserInfoResponse));
        return arrearageUserInfoResponse;

    }

    public void 集团各生命周期物联卡数量查询() {

    }

    public void 集团异常状态物联卡数据量查询() {

    }

    public void 集团GPRS在线物联卡数量查询() {

    }

    public BalanceRealSingleResponse 用户余额信息实时查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.用户余额信息实时查询.getUrl());
        BalanceRealSingleRequest balanceRealSingleRequest = new BalanceRealSingleRequest();
        balanceRealSingleRequest.setMsisdn(msisdn);
        balanceRealSingleRequest.setAppid(APPID);
        balanceRealSingleRequest.setEbid(Command.用户余额信息实时查询.getCode());
        balanceRealSingleRequest.setPassword(PASSWORD);
        BalanceRealSingleResponse balanceRealSingleResponse = null;
        try {
            balanceRealSingleResponse = client.excute(balanceRealSingleRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("用户余额信息实时查询" + JSON.toJSONString(balanceRealSingleResponse));
        return balanceRealSingleResponse;
    }

    public GprsUsedInfoSingleResponse 用户当月GPRS查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.用户当月GPRS查询.getUrl());
        GprsUsedInfoSingleRequest gprsUsedInfoSingleRequest = new GprsUsedInfoSingleRequest();
        gprsUsedInfoSingleRequest.setMsisdn(msisdn);
        gprsUsedInfoSingleRequest.setAppid(APPID);
        gprsUsedInfoSingleRequest.setEbid(Command.用户当月GPRS查询.getCode());
        gprsUsedInfoSingleRequest.setPassword(PASSWORD);
        GprsUsedInfoSingleResponse gprsUsedInfoSingleResponse = null;
        try {
            gprsUsedInfoSingleResponse = client.excute(gprsUsedInfoSingleRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("用户当月GPRS查询" + JSON.toJSONString(gprsUsedInfoSingleResponse));
        return gprsUsedInfoSingleResponse;
    }

    public BatchSmsUsedByDateResponse 短信批量查询(String msisdns, String yyyyMMdd) {
        CmiotClient client = new DefaultCmiotClient(url, Command.短信使用信息批量查询.getUrl());
        BatchSmsUsedByDateRequest batchSmsUsedByDateRequest = new BatchSmsUsedByDateRequest();
        batchSmsUsedByDateRequest.setMsisdns(msisdns);
        batchSmsUsedByDateRequest.setQuery_date(yyyyMMdd);
        batchSmsUsedByDateRequest.setAppid(APPID);
        batchSmsUsedByDateRequest.setEbid(Command.短信使用信息批量查询.getCode());
        batchSmsUsedByDateRequest.setPassword(PASSWORD);
        BatchSmsUsedByDateResponse batchSmsUsedByDateResponse = null;
        try {
            batchSmsUsedByDateResponse = client.excute(batchSmsUsedByDateRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("短信批量查询" + JSON.toJSONString(batchSmsUsedByDateResponse));
        return batchSmsUsedByDateResponse;
    }

    public BatchGprsUsedByDateResponse 流量信息批量查询(String msisdns, String yyyyMMdd) {
        CmiotClient client = new DefaultCmiotClient(url, Command.流量使用信息批量查询.getUrl());
        BatchGprsUsedByDateRequest batchGprsUsedByDateRequest = new BatchGprsUsedByDateRequest();
        batchGprsUsedByDateRequest.setMsisdns(msisdns);
        batchGprsUsedByDateRequest.setQuery_date(yyyyMMdd);
        batchGprsUsedByDateRequest.setAppid(APPID);
        batchGprsUsedByDateRequest.setEbid(Command.流量使用信息批量查询.getCode());
        batchGprsUsedByDateRequest.setPassword(PASSWORD);
        BatchGprsUsedByDateResponse batchGprsUsedByDateResponse = null;
        try {
            batchGprsUsedByDateResponse = client.excute(batchGprsUsedByDateRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("流量信息批量查询" + JSON.toJSONString(batchGprsUsedByDateResponse));
        return batchGprsUsedByDateResponse;
    }

    public SmsUsedInfoSingleResponse 用户当月短信查询(String msisdn) {

        CmiotClient client = new DefaultCmiotClient(url, Command.用户当月短信查询.getUrl());
        SmsUsedInfoSingleRequest gprsRealTimeInfoRequest = new SmsUsedInfoSingleRequest();
        gprsRealTimeInfoRequest.setMsisdn(msisdn);
        gprsRealTimeInfoRequest.setAppid(APPID);
        gprsRealTimeInfoRequest.setEbid(Command.用户当月短信查询.getCode());
        gprsRealTimeInfoRequest.setPassword(PASSWORD);
        SmsUsedInfoSingleResponse gprsRealTimeInfoResponse = null;
        try {
            gprsRealTimeInfoResponse = client.excute(gprsRealTimeInfoRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("用户当月短信查询" + JSON.toJSONString(gprsRealTimeInfoResponse));
        return gprsRealTimeInfoResponse;
    }

    public SmsUsedByDateResponse 用户短信使用查询(String msisdn, String yyyyMMdd) {

        CmiotClient client = new DefaultCmiotClient(url, Command.用户短信使用查询.getUrl());
        SmsUsedByDateRequest gprsRealTimeInfoRequest = new SmsUsedByDateRequest();
        gprsRealTimeInfoRequest.setMsisdn(msisdn);
        gprsRealTimeInfoRequest.setQuery_date(yyyyMMdd);
        gprsRealTimeInfoRequest.setAppid(APPID);
        gprsRealTimeInfoRequest.setEbid(Command.用户短信使用查询.getCode());
        gprsRealTimeInfoRequest.setPassword(PASSWORD);
        SmsUsedByDateResponse gprsRealTimeInfoResponse = null;
        try {
            gprsRealTimeInfoResponse = client.excute(gprsRealTimeInfoRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("用户短信使用查询" + JSON.toJSONString(gprsRealTimeInfoResponse));
        return gprsRealTimeInfoResponse;
    }

    //集团客户
    public GprsRealTimeInfoResponse 套餐内GPRS流量使用情况实时查询(String msisdn) {

        CmiotClient client = new DefaultCmiotClient(url, Command.套餐内GPRS流量使用情况实时查询.getUrl());
        GprsRealTimeInfoRequest gprsRealTimeInfoRequest = new GprsRealTimeInfoRequest();
        gprsRealTimeInfoRequest.setMsisdn(msisdn);
        gprsRealTimeInfoRequest.setAppid(APPID);
        gprsRealTimeInfoRequest.setEbid(Command.套餐内GPRS流量使用情况实时查询.getCode());
        gprsRealTimeInfoRequest.setPassword(PASSWORD);
        GprsRealTimeInfoResponse gprsRealTimeInfoResponse = null;
        try {
            gprsRealTimeInfoResponse = client.excute(gprsRealTimeInfoRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("套餐内GPRS流量使用情况实时查询" + JSON.toJSONString(gprsRealTimeInfoResponse));
        return gprsRealTimeInfoResponse;
    }

    public GprsUsedInfoSingleByDateResponse 物联卡单日GPRS使用量查询(String msisdn, String yyyyMMdd) {

        CmiotClient client = new DefaultCmiotClient(url, Command.物联卡单日GPRS使用量查询.getUrl());
        GprsUsedInfoSingleByDateRequest gprsUsedInfoSingleByDateRequest = new GprsUsedInfoSingleByDateRequest();
        gprsUsedInfoSingleByDateRequest.setMsisdn(msisdn);
        gprsUsedInfoSingleByDateRequest.setQueryDate(yyyyMMdd);
        gprsUsedInfoSingleByDateRequest.setAppid(APPID);
        gprsUsedInfoSingleByDateRequest.setEbid(Command.物联卡单日GPRS使用量查询.getCode());
        gprsUsedInfoSingleByDateRequest.setPassword(PASSWORD);
        GprsUsedInfoSingleByDateResponse gprsUsedInfoSingleByDateResponse = null;
        try {
            gprsUsedInfoSingleByDateResponse = client.excute(gprsUsedInfoSingleByDateRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("物联卡单日GPRS使用量查询" + JSON.toJSONString(gprsUsedInfoSingleByDateResponse));
        return gprsUsedInfoSingleByDateResponse;
    }

    public QueryCardProdInfoResponse 物联卡资费套餐查询(String msisdn) {
        CmiotClient client = new DefaultCmiotClient(url, Command.物联卡资费套餐查询接口.getUrl());
        QueryCardProdInfoRequest queryCardProdInfoRequest = new QueryCardProdInfoRequest();
        queryCardProdInfoRequest.setMsisdn(msisdn);
        queryCardProdInfoRequest.setAppid(APPID);
        queryCardProdInfoRequest.setEbid(Command.物联卡资费套餐查询接口.getCode());
        queryCardProdInfoRequest.setPassword(PASSWORD);
        QueryCardProdInfoResponse queryCardProdInfoResponse = null;
        try {
            queryCardProdInfoResponse = client.excute(queryCardProdInfoRequest, RequestMethod.GET);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("物联卡资费套餐查询" + JSON.toJSONString(queryCardProdInfoResponse));
        return queryCardProdInfoResponse;
    }

    public void 物联卡区域位置查询() {

    }

    public void 短信状态重置() {

    }
}
