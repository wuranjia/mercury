package com.hy.lang.mercury.resource.rest;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.McException;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.entity.ResponseEntity;
import com.hy.lang.mercury.common.entity.SessionContext;
import com.hy.lang.mercury.common.enums.OpRespEnum;
import com.hy.lang.mercury.pojo.User;
import com.hy.lang.mercury.resource.req.UserInfoReq;
import com.hy.lang.mercury.service.UserAble;
import com.hy.lang.mercury.service.UserOpAble;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserResource {


    private final UserAble userService;
    private final UserOpAble userOpService;

    public UserResource(UserAble userService, UserOpAble userOpService) {
        this.userService = userService;
        this.userOpService = userOpService;
    }

    @RequestMapping(path = "/find/one", method = RequestMethod.GET)
    public String findOne(@RequestParam Long queryUserId) {
        try {
            User user = userService.findOne(queryUserId);
            return JSON.toJSONString(ResponseEntity.createBySuccess(user));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/list/data", method = RequestMethod.GET)
    public String list(
            @RequestParam int limit,
            @RequestParam Long userId,
            @RequestParam int page,
            @RequestParam int draw,
            @RequestParam String userName,
            @RequestParam String company,
            @RequestParam String phone
    ) {
        try {
            UserInfoReq user = new UserInfoReq();
            user.setUserId(userId);
            user.setUserName(userName);
            user.setCompany(company);
            user.setPhone(phone);
            user.setPage(page);
            user.setLimit(limit);
            user.setDraw(draw);
            PageList<User> list = userService.list(user);
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    /**
     * 新增注册
     *
     * @return
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String add(
            @RequestParam @NotNull String userName,
            @RequestParam @NotNull String loginPwd,
            @RequestParam @NotNull String trdPwd,
            @RequestParam String phone,
            @RequestParam String company,
            @RequestParam String address,
            @RequestParam String memo,
            @RequestParam String parentId
    ) {
        try {
            User res = userOpService.queryByNameAndPwd(userName, loginPwd);
            if (res != null) {
                throw new McException(OpRespEnum.用户已存在);
            }
            User user = new User(userName, loginPwd, trdPwd, phone, company, address, memo, parentId);
            userOpService.insert(user);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }

    }

    /**
     * 编辑
     *
     * @return
     */
    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String edit(
            @RequestParam @NotNull Long userId,
            @RequestParam @NotNull String userName,
            @RequestParam @NotNull String loginPwd,
            @RequestParam @NotNull String trdPwd,
            @RequestParam String phone,
            @RequestParam String company,
            @RequestParam String address,
            @RequestParam String memo,
            @RequestParam String parentId
    ) {
        try {
            User res = userOpService.queryByNameAndPwd(userName, loginPwd);
            if (res == null) {
                return JSON.toJSONString(ResponseEntity.createByError());
            }
            User user = new User(userName, loginPwd, trdPwd, phone, company, address, memo, parentId);
            user.setUserId(userId);
            userOpService.update(user);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }

    }

    /**
     * 登录接口
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam @NotNull String userName,
            @RequestParam @NotNull String password
    ) {
        try {
            SessionContext sessionContext = userService.login(userName, password);
            return JSON.toJSONString(ResponseEntity.createBySuccess(sessionContext));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/login/valid", method = RequestMethod.GET)
    public String loginValid(
            @RequestParam String userId) {
        try {
            boolean flag = Constants.需要登录;
            if (StringUtils.isBlank(userId)) {
                return JSON.toJSONString(ResponseEntity.createBySuccess(flag));
            }
            flag = userService.isOnline(Long.valueOf(userId));
            return JSON.toJSONString(ResponseEntity.createBySuccess(flag));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/children", method = RequestMethod.GET)
    public String getChildren(
            @RequestParam String userId) {
        try {
            List<User> list = userService.getAllChildren(Long.valueOf(userId));
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }


    /**
     * 登出
     *
     * @param userId
     * @return
     */
    @RequestMapping(path = "/login/out", method = RequestMethod.POST)
    public String loginOut(
            @ModelAttribute @NotNull Long userId
    ) {
        try {
            userService.loginOut(userId);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    /**
     * 密码重置
     *
     * @param userName
     * @param passWord
     * @param newPassWord
     * @return
     */
    @RequestMapping(path = "/pwd/reset", method = RequestMethod.POST)
    public String resetPwd(
            @ModelAttribute @NotNull String userName,
            @ModelAttribute @NotNull String passWord,
            @ModelAttribute @NotNull String newPassWord
    ) {
        try {
            SessionContext sessionContext = userService.resetPwd(userName, passWord, newPassWord);
            return JSON.toJSONString(ResponseEntity.createBySuccess(sessionContext));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }


    public static void main(String[] args) {
        System.out.println(true || true || false);
    }
}