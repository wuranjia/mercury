package com.hy.lang.mercury.client.cmiot;

public enum Command {

    在线信息实时查询("0001000000008","在线信息实时查询","/v2/gprsrealsingle"),
    用户状态信息实时查询("0001000000009","用户状态信息实时查询","/v2/userstatusrealsingle"),
    码号信息查询("0001000000010","码号信息查询","/v2/cardinfo"),
    用户当月GPRS查询("0001000000012","用户当月GPRS查询","/v2/gprsusedinfosingle"),
    开关机信息实时查询("0001000000025","开关机信息实时查询","/v2/onandoffrealsingle"),
    短信使用信息批量查询("0001000000026","短信使用信息批量查询","/v2/batchsmsusedbydate"),
    流量使用信息批量查询("0001000000027","流量使用信息批量查询","/v2/batchgprsusedbydate"),
    用户余额信息实时查询("0001000000035","用户余额信息实时查询","/v2/balancerealsingle"),
    用户当月短信查询("0001000000036","用户当月短信查询","/v2/smsusedinfosingle"),
    短信状态重置("0001000000034","短信状态重置","/v2/smsstatusreset"),
    集团用户数查询("0001000000039","集团用户数查询","/v2/groupuserinfo"),
    用户短信使用查询("0001000000040","用户短信使用查询","/v2/smsusedbydate"),
    套餐内GPRS流量使用情况实时查询("0001000000083","套餐内GPRS流量使用情况实时查询","/v2/gprsrealtimeinfo"),
    欠费停机用户批量查询("0001000000328","欠费停机用户批量查询","/v2/arrearageuserinfo"),
    物联卡资费套餐查询接口("0001000000264","物联卡资费套餐查询接口","/v2/querycardprodinfo"),
    物联卡单日GPRS使用量查询("0001000000407","物联卡单日GPRS使用量查询","/v2/gprsusedinfosinglebydate"),
    集团各生命周期物联卡数量查询("0001000000417","集团各生命周期物联卡数量查询","/v2/querycardcount"),
    集团异常状态物联卡数据量查询("0001000000427","集团异常状态物联卡数据量查询","/v2/queryabnormalcardcount"),
    集团GPRS在线物联卡数量查询("0001000000428","集团GPRS在线物联卡数量查询","/v2/querygprsonlinecardcount"),
    物联卡短信服务开通查询("0001000000429","物联卡短信服务开通查询","/v2/querysmsopenstatus"),
    物联卡GPRS服务开通查询("0001000000430","物联卡GPRS服务开通查询","/v2/querygprsopenstatus"),
    物联卡APN服务开通查询("0001000000431","物联卡APN服务开通查询","/v2/queryapnopenstatus"),
    物联卡生命周期查询("0001000000432","物联卡生命周期查询","/v2/querycardlifecycle");

    private String code;
    private String msg;
    private String url;

    private Command(String code, String msg, String url) {
        this.code = code;
        this.msg = msg;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
