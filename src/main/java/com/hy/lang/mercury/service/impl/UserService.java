package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.McException;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.entity.SessionContext;
import com.hy.lang.mercury.common.enums.HbTypeEnum;
import com.hy.lang.mercury.common.enums.OpRespEnum;
import com.hy.lang.mercury.common.utils.DateTimeUtils;
import com.hy.lang.mercury.common.utils.MD5Utils;
import com.hy.lang.mercury.common.utils.MapUtils;
import com.hy.lang.mercury.dao.UserMapper;
import com.hy.lang.mercury.pojo.Menu;
import com.hy.lang.mercury.pojo.User;
import com.hy.lang.mercury.pojo.UserHeartbeat;
import com.hy.lang.mercury.resource.req.UserInfoReq;
import com.hy.lang.mercury.service.UserAble;
import com.hy.lang.mercury.service.UserOpAble;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserAble {

    @Autowired
    private UserOpAble userOpService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserHbOpService userHbOpService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public SessionContext getSessionContext(Long userId) throws McException {
        User user = userOpService.query(userId);
        List<Menu> menuList = menuService.getShowMenuByUserId(userId);
        return new SessionContext(user, menuList);
    }

    /**
     * 规则：
     * 最新一条类型为0，校验心跳时间距离当前时间
     * 大于30min，需要登录
     * 小于30min，继续访问
     * <p>
     * 最新一条类型为1，需要登录
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isOnline(Long userId) throws McException {

        UserHeartbeat userHeartbeat = userHbOpService.queryLastRd(userId);
        if (userHeartbeat == null || userHeartbeat.getHbType() == null) {
            return Constants.需要登录;
        }
        if (userHeartbeat.getHbType().intValue() == HbTypeEnum.登出.getCode()) {
            return Constants.需要登录;
        } else if (userHeartbeat.getHbType().intValue() == HbTypeEnum.登入.getCode()) {
            if (DateTimeUtils.inSessionTime(userHeartbeat.getHbTime())) {
                return Constants.已登录;
            } else {
                return Constants.需要登录;
            }
        }
        return Constants.需要登录;
    }

    @Override
    public boolean validate(String userName, String passWord) throws McException {
        return false;
    }

    @Override
    public SessionContext login(String userName, String passWord) throws McException {
        List<User> list = userOpService.list(MapUtils.buildKeyValueMap("userName", userName
                , "limit", 10
                , "start", 0
        ));
        if (list == null || list.size() != Constants.ONE_INT) {
            throw new McException(OpRespEnum.用户不存在);
        }
        User user = userOpService.queryByNameAndPwd(userName, passWord);
        if (user == null) {
            throw new McException(OpRespEnum.密码不正确);
        }

        //heartBeat
        userHbOpService.insert(UserHeartbeat.buildEntity("com.hy.lang.mercury.service.impl.UserService.login", HbTypeEnum.登入, user.getUserId()));

        user.setLoginPwd(Constants.NVL);
        user.setPayPwd(Constants.NVL);
        SessionContext sessionContext = new SessionContext(user, null);
        return sessionContext;
    }

    @Override
    public void loginOut(Long userId) throws McException {

    }

    @Override
    public SessionContext resetPwd(@NotNull String userName, @NotNull String passWord, @NotNull String newPassWord) {
        return null;
    }

    @Override
    public PageList<User> list(UserInfoReq user) {
        int total = userMapper.countByParams(MapUtils.buildKeyValueMap("userId", user.getUserId()
                , "address", StringUtils.isBlank(user.getAddress()) ? null : user.getAddress()
                , "phone", StringUtils.isBlank(user.getPhone()) ? null : user.getPhone()
                , "company", StringUtils.isBlank(user.getCompany()) ? null : user.getCompany()
                , "userName", StringUtils.isBlank(user.getUserName()) ? null : user.getUserName()
                , "limit", user.getLimit()
                , "start", user.getStart()
        ));
        //查询
        List<User> list = userMapper.selectByParams(MapUtils.buildKeyValueMap("userId", user.getUserId()
                , "address", StringUtils.isBlank(user.getAddress()) ? null : user.getAddress()
                , "phone", StringUtils.isBlank(user.getPhone()) ? null : user.getPhone()
                , "company", StringUtils.isBlank(user.getCompany()) ? null : user.getCompany()
                , "userName", StringUtils.isBlank(user.getUserName()) ? null : user.getUserName()
                , "limit", user.getLimit()
                , "start", user.getStart()
        ));
        PageList<User> pageList = new PageList<User>();
        pageList.setCurrent(user.getPage());
        pageList.setPageSize(user.getLimit());
        pageList.setDraw(user.getDraw());
        pageList.setTotal(total);
        pageList.setItems(list);
        return pageList;
    }

    @Override
    public User findOne(Long queryUserId) {
        return userMapper.selectByPrimaryKey(queryUserId);
    }

    @Override
    public List<User> getAllChildren(Long userId) {
        List<User> result = new ArrayList<User>();
        User user = userMapper.selectByPrimaryKey(userId);
        result.add(user);
        Long parentId = user.getUserId();
        recursionChildren(parentId, result);
        return result;
    }

    @Override
    public List<Long> getAllChildrenId(Long userId) {
        List<User> result = this.getAllChildren(userId);
        List<Long> rid = new ArrayList<Long>();
        for (User u : result) {
            rid.add(u.getUserId());
        }
        return rid;
    }

    public void recursionChildren(Long parentId, List<User> result) {

        List<User> list = userMapper.selectByParentId(parentId);
        if (list == null || (list != null && list.size() == 0)) {
            return;
        }
        for (User user : list) {
            Long userId = user.getUserId();
            result.add(user);
            recursionChildren(userId, result);
        }
        return;
    }
}
