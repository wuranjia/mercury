package com.hy.lang.mercury.service;

import com.hy.lang.mercury.pojo.Menu;

import java.util.List;

public interface MenuAble {

    List<Menu> getShowMenuByUserId(Long userId);

}
