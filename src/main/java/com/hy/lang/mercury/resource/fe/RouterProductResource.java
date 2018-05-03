package com.hy.lang.mercury.resource.fe;

import com.hy.lang.mercury.service.MenuAble;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterProductResource extends AbstractResourceMenu {
    private final MenuAble menuService;

    public RouterProductResource(MenuAble menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/fe/product/list")
    public String list(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        return "form-product-list";
    }


}
