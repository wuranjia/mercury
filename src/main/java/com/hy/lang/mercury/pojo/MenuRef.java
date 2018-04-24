package com.hy.lang.mercury.pojo;

import javax.validation.constraints.NotNull;

public class MenuRef {
    private Long id;

    private Long menuId;

    private Long userId;

    public MenuRef() {
    }

    public MenuRef(@NotNull Long preUserId, String mid) {
        this.userId = preUserId;
        this.menuId = Long.valueOf(mid);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}