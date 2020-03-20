package com.lincomb.dmp.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IconController {

    @RequestMapping("fontawesome")
    public String fontawesome(ModelMap modelMap, String id) {

        modelMap.put("id", id);
        return "/common/_fontawesome.html";
    }
}
