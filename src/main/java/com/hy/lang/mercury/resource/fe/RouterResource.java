package com.hy.lang.mercury.resource.fe;

import com.hy.lang.mercury.service.MenuAble;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterResource extends AbstractResourceMenu {


    private final MenuAble menuService;

    public RouterResource(MenuAble menuService) {
        this.menuService = menuService;
    }


    @GetMapping("/")
    public String index(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);

        return "login";
    }


}
