package com.hy.lang.mercury.service;

import com.hy.lang.mercury.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuOpAble {

    int insert(Menu menu);

    int update(Menu menu);

    int delete(Menu menu);

    Menu query(Long menuId);

    List<Menu> list(Map<String, Object> map);
}
