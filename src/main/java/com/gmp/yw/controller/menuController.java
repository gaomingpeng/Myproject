package com.gmp.yw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/menu")
@Controller
public class menuController {



    @RequestMapping("/toBehavior.do")
    public  String toBehavior(){
        return "yw/behavior";
    }

    @RequestMapping("/togetLoginCountpage.do")
    public  String togetEveryUserLoginCountPage(){
        return "yw/behavioranalysis";
    }

    @RequestMapping("/toaddMenu.do")
    public String toaddMenu(){
        return "menu/addmenu";
    }
}
