package com.hy.lang.mercury.service;

import com.hy.lang.mercury.common.entity.ZtreeNode;
import com.hy.lang.mercury.pojo.Menu;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MenuAble {

    List<Menu> getShowMenuByUserId(Long userId);

    List<ZtreeNode> getTreeStruct(@NotNull Long userId);

    void refAdd(@NotNull Long preUserId, @NotNull String menuIds);
}
