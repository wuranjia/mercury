package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.SmsDeliver;
import com.hy.lang.mercury.resource.req.SmsDeliverReq;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SmsDeliverMapper {
    int deleteByPrimaryKey(Long smsId);

    int insert(SmsDeliver record);

    int insertSelective(SmsDeliver record);

    SmsDeliver selectByPrimaryKey(Long smsId);

    int updateByPrimaryKeySelective(SmsDeliver record);

    int updateByPrimaryKey(SmsDeliver record);

    List<SmsDeliver> selectByParams(SmsDeliverReq smsInfoReq);

    int countByParams(SmsDeliverReq smsInfoReq);
}