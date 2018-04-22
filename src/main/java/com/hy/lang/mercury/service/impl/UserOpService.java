package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.utils.MD5Utils;
import com.hy.lang.mercury.dao.UserMapper;
import com.hy.lang.mercury.pojo.User;
import com.hy.lang.mercury.service.UserOpAble;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserOpService implements UserOpAble {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User user) {

        String passWord = user.getLoginPwd();
        String userName = user.getUserName();
        String loginPwd = MD5Utils.encodeUserInfo(userName, passWord);
        user.setLoginPwd(loginPwd);

        String trdPwd = user.getPayPwd();
        String tradePwd = MD5Utils.encodeUserInfo(userName, trdPwd);
        user.setPayPwd(tradePwd);

        user.setCreatedBy(user.getParentId() + "");
        user.setCreatedTime(new Date());
        user.setUpdatedBy(user.getParentId() + "");
        user.setUpdatedTime(new Date());

        user.setMemo(StringUtils.isBlank(user.getMemo()) ? Constants.NVL : user.getMemo());
        user.setAddress(StringUtils.isBlank(user.getAddress()) ? Constants.NVL : user.getAddress());
        user.setCompany(StringUtils.isBlank(user.getCompany()) ? Constants.NVL : user.getCompany());
        user.setPhone(StringUtils.isBlank(user.getPhone()) ? Constants.NVL : user.getPhone());

        user.setParentId(user.getParentId() == null ? Constants.L_负_1 : user.getParentId());

        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {

        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int delete(Long userId) {

        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public User query(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> list(Map<String, Object> map) {
        return userMapper.selectByParams(map);
    }

    @Override
    public User queryByNameAndPwd(String userName, String passWord) {
        return userMapper.queryByNameAndPwd(userName, passWord);
    }
}
