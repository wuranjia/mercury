package com.hy.lang.mercury.resource.fe;

import com.hy.lang.mercury.service.MenuAble;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterStoreResource extends AbstractResourceMenu {
    private final MenuAble menuService;

    public RouterStoreResource(MenuAble menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/fe/store/in_list")
    public String buyList(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        //return "form-store-in-list";
        return "v2-store-in-list";
    }

    @GetMapping("/fe/store/out_list")
    public String sellList(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        //return "form-store-out-list";
        return "v2-store-out-list";
    }
}
