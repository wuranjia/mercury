package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.dao.MenuMapper;
import com.hy.lang.mercury.dao.MenuRefMapper;
import com.hy.lang.mercury.pojo.Menu;
import com.hy.lang.mercury.pojo.MenuRef;
import com.hy.lang.mercury.service.MenuAble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
