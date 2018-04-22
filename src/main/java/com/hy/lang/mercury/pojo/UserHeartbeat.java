package com.hy.lang.mercury.pojo;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.enums.HbTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class UserHeartbeat {
    private Long hbId;

    private Long userId;

    private Date hbTime;

    private String callUrl;

    private Integer hbType;

    public Long getHbId() {
        return hbId;
    }

    public void setHbId(Long hbId) {
        this.hbId = hbId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getHbTime() {
        return hbTime;
    }

    public void setHbTime(Date hbTime) {
        this.hbTime = hbTime;
    }

    public String getCallUrl() {
        return callUrl;
    }

    public void setCallUrl(String callUrl) {
        this.callUrl = callUrl == null ? null : callUrl.trim();
    }

    public Integer getHbType() {
        return hbType;
    }

    public void setHbType(Integer hbType) {
        this.hbType = hbType;
    }


    public static UserHeartbeat buildEntity(String url, HbTypeEnum hbTypeEnum, Object[] param, String[] names) {
        Long userId = formatUserId(param, names);
        UserHeartbeat userHeartbeat = new UserHeartbeat();
        userHeartbeat.setCallUrl(url);
        userHeartbeat.setHbTime(new Date());
        userHeartbeat.setHbType(hbTypeEnum.getCode());
        userHeartbeat.setUserId(userId == null ? -1 : userId);
        return userHeartbeat;
    }


    public static UserHeartbeat buildEntity(String url, HbTypeEnum hbTypeEnum, Long userId) {
        UserHeartbeat userHeartbeat = new UserHeartbeat();
        userHeartbeat.setCallUrl(url);
        userHeartbeat.setHbTime(new Date());
        userHeartbeat.setHbType(hbTypeEnum.getCode());
        userHeartbeat.setUserId(userId == null ? -1 : userId);
        return userHeartbeat;
    }

    public static Long formatUserId(Object[] param, String[] names) {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (StringUtils.isBlank(name)) {
                continue;
            }
            if (name.equals(Constants.USER_ID)) {
                if (param[i] instanceof java.lang.Long)
                    return (Long) param[i];
            }
        }
        return null;
    }
}