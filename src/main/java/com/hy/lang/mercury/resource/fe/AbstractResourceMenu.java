package com.hy.lang.mercury.resource.fe;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.pojo.Menu;
import com.hy.lang.mercury.pojo.User;
import com.hy.lang.mercury.service.MenuAble;
import com.hy.lang.mercury.service.UserAble;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractResourceMenu {
    public void setMenus(ModelMap modelMap, MenuAble menuService) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long userId = Constants.getUserId(request);
        if (userId != null) {
            List<Menu> list = menuService.getShowMenuByUserId(Long.valueOf(userId));
            List<Menu> list1 = new ArrayList<Menu>();

            for (Menu m : list) {
                List<Menu> children = new ArrayList<Menu>();
                for (Menu c : list) {
                    if (c.getParentId().equals(m.getMenuId())) {
                        children.add(c);
                    }
                }
                m.setChildren(children);
                if (m.getParentId() == -1) {
                    list1.add(m);
                }

            }
            modelMap.addAttribute("menuList", list1);
        }
    }

    public void setUserInfo(ModelMap modelMap, UserAble userService) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long userId = Constants.getUserId(request);
        if (userId != null) {
            User user = userService.findOne(userId);
            if (user == null) {
                return;
            }
            modelMap.put("userName", user.getUserName());
        }
    }
}
