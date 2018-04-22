package com.hy.lang.mercury.dao;

import com.hy.lang.mercury.pojo.UserHeartbeat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserHeartbeatMapper {
    int deleteByPrimaryKey(Long hbId);

    int insert(UserHeartbeat record);

    int insertSelective(UserHeartbeat record);

    UserHeartbeat selectByPrimaryKey(Long hbId);

    int updateByPrimaryKeySelective(UserHeartbeat record);

    int updateByPrimaryKey(UserHeartbeat record);

    UserHeartbeat queryLastRd(Long userId);
}