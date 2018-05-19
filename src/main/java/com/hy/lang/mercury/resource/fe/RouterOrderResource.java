package com.hy.lang.mercury.resource.fe;

import com.hy.lang.mercury.service.MenuAble;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterOrderResource extends AbstractResourceMenu {
    private final MenuAble menuService;

    public RouterOrderResource(MenuAble menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/fe/order/buy_list")
    public String buyList(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        //return "form-order-buy-list";
        return "v2-order-buy-list";
    }

    @GetMapping("/fe/order/sell_list")
    public String sellList(ModelMap modelMap) {
        super.setMenus(modelMap, menuService);
        //return "form-order-sell-list";
        return "v2-order-sell-list";
    }
}
