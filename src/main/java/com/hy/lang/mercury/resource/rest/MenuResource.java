package com.hy.lang.mercury.resource.rest;


import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.common.entity.ResponseEntity;
import com.hy.lang.mercury.common.entity.ZtreeNode;
import com.hy.lang.mercury.pojo.Menu;
import com.hy.lang.mercury.service.MenuAble;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/menu")
public class MenuResource extends BaseResource {

    private final MenuAble menuService;

    public MenuResource(MenuAble menuService) {
        this.menuService = menuService;
    }


    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(
            @RequestParam("userId") @NotNull Long userId,
            @RequestParam("limit") @NotNull Long pageSize,
            @RequestParam("page") @NotNull Long current
    ) {
        try {
            List<Menu> list = menuService.getShowMenuByUserId(userId);
            return JSON.toJSONString(ResponseEntity.createBySuccess(list));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByErrorMessage(e));
        }
    }

    @RequestMapping(path = "/tree", method = RequestMethod.GET)
    public String tree(@RequestParam("userId") @NotNull Long userId,
                       @RequestParam("preUserId") @NotNull Long preUserId) {
        try {
            List<ZtreeNode> ztreeNodes = menuService.getTreeStruct(preUserId);
            return JSON.toJSONString(ResponseEntity.createBySuccess(ztreeNodes));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByErrorMessage(e));
        }
    }

    @RequestMapping(path = "/ref/save", method = RequestMethod.POST)
    public String refAdd(@RequestParam("userId") @NotNull Long userId,
                         @RequestParam("preUserId") @NotNull Long preUserId,
                         @RequestParam("menuIds") @NotNull String menuIds) {
        try {
            menuService.refAdd(preUserId,menuIds);
            return JSON.toJSONString(ResponseEntity.createBySuccess());
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByErrorMessage(e));
        }
    }
}
