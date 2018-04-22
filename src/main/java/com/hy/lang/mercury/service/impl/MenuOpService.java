package com.hy.lang.mercury.service.impl;

import com.hy.lang.mercury.pojo.Menu;
import com.hy.lang.mercury.service.MenuOpAble;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuOpService implements MenuOpAble {
    @Override
    public int insert(Menu menu) {
        return 0;
    }

    @Override
    public int update(Menu menu) {
        return 0;
    }

    @Override
    public int delete(Menu menu) {
        return 0;
    }

    @Override
    public Menu query(Long menuId) {
        return null;
    }

    @Override
    public List<Menu> list(Map<String, Object> map) {
        return null;
    }
}
