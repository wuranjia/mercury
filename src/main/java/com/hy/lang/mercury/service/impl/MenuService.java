package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.common.entity.ZtreeNode;
import com.hy.lang.mercury.dao.MenuMapper;
import com.hy.lang.mercury.dao.MenuRefMapper;
import com.hy.lang.mercury.pojo.Menu;
import com.hy.lang.mercury.pojo.MenuRef;
import com.hy.lang.mercury.service.MenuAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService implements MenuAble {

    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRefMapper menuRefMapper;

    @Override
    public List<Menu> getShowMenuByUserId(Long userId) {
        List<MenuRef> list = menuRefMapper.selectByUserId(userId);
        List<Long> idList = new ArrayList<Long>();
        for (MenuRef menuRef : list) {
            idList.add(menuRef.getMenuId());
        }
        List<Menu> menuList = menuMapper.queryByIdList(idList);
        return menuList;
    }

    @Override
    public List<ZtreeNode> getTreeStruct(@NotNull Long userId) {
        List<ZtreeNode> result = new ArrayList<ZtreeNode>();
        List<MenuRef> refList = menuRefMapper.selectByUserId(userId);
        List<Menu> menuList = menuMapper.selectAll();
        for (Menu menu : menuList) {
            ZtreeNode ztreeNode = new ZtreeNode(menu, refList, userId);
            result.add(ztreeNode);
        }
        return result;
    }

    @Override
    public void refAdd(@NotNull Long preUserId, @NotNull String menuIds) {
        String[] menuIdArray = menuIds.split(",");
        List<MenuRef> list = new ArrayList<MenuRef>();
        for (String mid : menuIdArray) {
            MenuRef menuRef = new MenuRef(preUserId,mid);
            list.add(menuRef);
        }
        menuRefMapper.deleteByUserId(preUserId);
        menuRefMapper.batchInsert(list);
    }
}
