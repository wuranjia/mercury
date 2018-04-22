package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.SmsView;
import com.hy.lang.mercury.resource.req.SmsViewReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SmsViewMapper {
    int deleteByPrimaryKey(Long smsId);

    int insert(SmsView record);

    int insertSelective(SmsView record);

    SmsView selectByPrimaryKey(Long smsId);

    int updateByPrimaryKeySelective(SmsView record);

    int updateByPrimaryKey(SmsView record);

    int countByParams(SmsViewReq smsViewReq);

    List<SmsView> selectByParams(SmsViewReq smsViewReq);

    void updateSmsStatus(@Param("status") String status, @Param("smsId") Long smsId);
}