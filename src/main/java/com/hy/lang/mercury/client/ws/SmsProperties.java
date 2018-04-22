package com.hy.lang.mercury.client.ws;

public class SmsProperties {


    //应用ID或插件的ID
    public static final String ApplicationID = "P201802071652070";//=P800000000000001
    //短消息要被发送到的地址。群发短消息的最大数量为254
    //public static final String Address = "";//tel:13680000432
    //指由该应用填写的内部扩展号码。MAS服务器需自动补充为此业务分配的长服务号码
    public static final String ExtendCode = "123456";//123456;
    /*消息编码类型:
        ASCII ASCII字符。
        UCS2 USC2格式的UniCode字符。
        GB18030 GB18030格式的中文字符。
        GB2312 GB2312格式的中文字符。
        Binary 二进制短信，用十六进制字符串。*/
    public static final String MessageFormat = "GB2312";//GB2312
    /*发送消息选项
        Normal 普通短信
        Instant 普通短信立即显示
        Long 长短信
        Structured 长度小于160字节，
        但UDHI需置为1*/
    public static final String SendMethod = "Long";//Normal
    /*指示是否需要网络侧返回递交状态报告。若无，则不返回。True表示需要网络侧返回递交状态报告，
        false表示不需要网络侧返回递交状态报告*/
    public static final boolean DeliveryResultRequest = true;//true

    public static final String PRE = "tel:";
}
