package com.hy.lang.mercury.common.entity;

import com.hy.lang.mercury.pojo.Menu;
import com.hy.lang.mercury.pojo.MenuRef;

import java.util.List;

public class ZtreeNode extends BaseSerial {
    private Long id;
    private Long pId;
    private String name;
    private boolean checked;
    private boolean open;

    public ZtreeNode() {

    }

    public ZtreeNode(Menu menu, List<MenuRef> refList, Long userId) {
        this.id = menu.getMenuId();
        this.pId = menu.getParentId();
        this.name = menu.getMenuName();
        this.checked = false;
        this.open = true;
        for (MenuRef menuRef : refList) {
            if (menuRef.getMenuId().longValue() == this.id.longValue()
                    && menuRef.getUserId().longValue() == userId.longValue()) {
                this.checked = true;
                break;
            }
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
