package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.SmsInfo;
import com.hy.lang.mercury.resource.req.SmsInfoReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SmsInfoMapper {
    int deleteByPrimaryKey(Long smsId);

    int insert(SmsInfo record);

    int insertSelective(SmsInfo record);

    SmsInfo selectByPrimaryKey(Long smsId);

    int updateByPrimaryKeySelective(SmsInfo record);

    int updateByPrimaryKey(SmsInfo record);

    List<SmsInfo> selectByParams(SmsInfoReq smsInfoReq);

    int countByParams(SmsInfoReq smsInfoReq);

    int updateSmsStatus(@Param("smsId") Long smsId, @Param("status") int status);

    List<SmsInfo> selectBySmsOtherNo(@Param("smsOther") String smsOther);

    int updateSmsStatusAndMemo(@Param("smsId") Long smsId,@Param("status") int status, @Param("memo") String memo);

    void updateSmsOtherNo(@Param("smsId") Long smsId,@Param("smsOther") String smsOther);
}