package com.hy.lang.mercury.service;

import com.hy.lang.mercury.pojo.UserHeartbeat;

import java.util.List;
import java.util.Map;

public interface UserHbOpAble {

    int insert(UserHeartbeat userHeartbeat);

    int update(UserHeartbeat userHeartbeat);

    int delete(UserHeartbeat userHeartbeat);

    UserHeartbeat query(Long hbId);

    UserHeartbeat queryLastRd(Long userId);

    List<UserHeartbeat> list(Map<String, Object> map);
}
