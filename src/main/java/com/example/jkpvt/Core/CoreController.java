package com.example.jkpvt.Core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("*")
public class CoreController {

    @RequestMapping
    public String index() {
        return "forward:/myApp/build/index.html";
    }
}
