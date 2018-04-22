package com.hy.lang.mercury.resource.fe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterSmsResource {

    @GetMapping("/fe/sms")
    public String index() {
        return "form-sms-add";
    }

    @GetMapping("/fe/sms/list")
    public String list() {
        return "form-sms-list";
    }

    @GetMapping("/fe/sms/deliver_list")
    public String deliverList() {
        return "form-sms-deliver-list";
    }
}
