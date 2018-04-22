package com.hy.lang.mercury.client.cmpp.common.msg.util;

public class info {
    private long      msg_Id       =0           ;
    private byte      pk_total  =1              ;
    private byte      pk_number   =1            ;
    private byte      registered_Delivery=0     ;
    private byte      msg_level     =0          ;
    private String      service_Id   ="test"           ;
    private byte      fee_UserType    =0;//谁接收，计谁的费
    private String      fee_terminal_Id =""        ;
    private byte      fee_terminal_type    =0   ;
    private byte      tP_pId      =0            ;
    private byte      tP_udhi       =0          ;
    private byte      msg_Fmt         =15        ;
    private String      msg_src                 ;
    //01：对“计费用户号码”免费；
    // 02：对“计费用户号码”按条计信息费；
    // 03：对“计费用户号码”按包月收取信息费

    private String      feeType    ="02"   ;//默认为按条
    private String      feeCode   ="000000"              ;
    private String      valId_Time  =""  ;//暂不支持
    private String      at_Time  =""  ;//暂不支持
    //SP的服务代码或前缀为服务代码的长号码, 网关将该号码完整的填到SMPP协议Submit_SM消息相应的source_addr字段，该号码最终在用户手机上显示为短消息的主叫号码。
    private String      src_Id   ;
    private byte      destUsr_tl =1;//不支持群发
    private String      dest_terminal_Id ;//接收手机号码，
    private byte      dest_terminal_type =0  ;//真实号码
    private byte      msg_Length              ;
    private String      msg_Content             ;
    //点播业务使用的LinkID，非点播类业务的MT流程不使用该字段
    private String      linkID="linkid"+System.currentTimeMillis()               ;
    //以下为对应的getter/Setter方法
    private int sendResult=-1;//收到应答的结果
}
