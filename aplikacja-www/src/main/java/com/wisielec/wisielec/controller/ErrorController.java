package com.wisielec.wisielec.controller;

import org.springframework.stereotype.Controller;

/**
 * Created by Emerex on 08/12/2017.
 */

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController{

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
