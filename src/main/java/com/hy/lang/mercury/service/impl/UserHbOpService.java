package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.dao.UserHeartbeatMapper;
import com.hy.lang.mercury.pojo.UserHeartbeat;
import com.hy.lang.mercury.service.UserHbOpAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserHbOpService implements UserHbOpAble {

    @Autowired
    private UserHeartbeatMapper heartbeatMapper;

    @Override
    public int insert(UserHeartbeat userHeartbeat) {
        return heartbeatMapper.insert(userHeartbeat);
    }

    @Override
    public int update(UserHeartbeat userHeartbeat) {
        return 0;
    }

    @Override
    public int delete(UserHeartbeat userHeartbeat) {
        return 0;
    }

    @Override
    public UserHeartbeat query(Long hbId) {
        return null;
    }

    @Override
    public UserHeartbeat queryLastRd(Long userId) {
        return heartbeatMapper.queryLastRd(userId);
    }

    @Override
    public List<UserHeartbeat> list(Map<String, Object> map) {
        return null;
    }
}
