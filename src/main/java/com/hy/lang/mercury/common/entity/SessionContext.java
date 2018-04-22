package com.hy.lang.mercury.common.entity;

import com.hy.lang.mercury.pojo.Menu;
import com.hy.lang.mercury.pojo.User;

import java.util.List;

public class SessionContext extends BaseSerial {
    private User user;
    private List<Menu> menuList;

    public SessionContext() {

    }

    public SessionContext(User user, List<Menu> list) {
        this.user = user;
        this.menuList = list;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
