package com.hy.lang.mercury.resource.fe;

import com.hy.lang.mercury.service.MenuAble;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterSimResource extends AbstractResourceMenu {

    private final MenuAble menuService;

    public RouterSimResource(MenuAble menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/fe/card/my")
    public String add(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        return "form-card-my";
    }

    @GetMapping("/fe/card/my_assign")
    public String assign(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        return "form-card-my-assign";
    }

}
