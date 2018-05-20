package com.hy.lang.mercury.resource.fe;

import com.hy.lang.mercury.service.MenuAble;
import com.hy.lang.mercury.service.UserAble;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterIndexResource extends AbstractResourceMenu {

    private final MenuAble menuService;

    private final UserAble userService;

    public RouterIndexResource(MenuAble menuService, UserAble userService) {
        this.menuService = menuService;
        this.userService = userService;
    }

    @GetMapping("/fe/index")
    public String index(ModelMap modelMap
    ) {
        super.setMenus(modelMap, menuService);
        super.setUserInfo(modelMap,userService);
        //return "index";
        return "v2-index";
    }

    @GetMapping("/fe/dashboard")
    public String dashboard(ModelMap modelMap
    ) {
        super.setMenus(modelMap, menuService);
        //return "index";
        return "v2-dashboard";
    }

}