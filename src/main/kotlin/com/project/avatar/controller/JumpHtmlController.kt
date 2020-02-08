package com.project.avatar.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class JumpHtmlController
{

    @RequestMapping("/jump")
    fun jumpIndex():String
    {
        return "/index.html"
    }
}