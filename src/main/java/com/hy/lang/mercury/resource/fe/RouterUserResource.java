package com.hy.lang.mercury.resource.fe;

import com.hy.lang.mercury.service.MenuAble;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterUserResource extends AbstractResourceMenu {


    private final MenuAble menuService;

    public RouterUserResource(MenuAble menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/fe/user")
    public String add(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        return "form-user-add";
    }


    @GetMapping("/fe/user/edit")
    public String edit(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        return "form-user-edit";
    }

    @GetMapping("/fe/user/list")
    public String list(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        return "form-user-list";
    }

    @GetMapping("/fe/user/detail")
    public String detail(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        return "form-user-detail";
    }

    
}