package com.magic.aimai.video.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Eric Xie on 2017/8/30 0030.
 */

@Controller
@RequestMapping("/page")
public class PageController {



    @RequestMapping("/notFound")
    public String notFound(){
        return "404";
    }

}
