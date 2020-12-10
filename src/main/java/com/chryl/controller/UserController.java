package com.chryl.controller;

import com.chryl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Chr.yl on 2020/12/10.
 *
 * @author Chr.yl
 */
@RestController
@RequestMapping("/enc")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/query")
    public Object show() {

        return userService.list();

    }
}
