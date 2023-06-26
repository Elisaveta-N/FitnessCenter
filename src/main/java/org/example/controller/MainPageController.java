package org.example.controller;

import org.example.security.Role;
import org.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainPageController {
    private final UserService userService;
    private long idDbg = 2;

    public MainPageController(UserService userService) {
        this.userService = userService;
    }

    private String getUserRole()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var ouser =  userService.findByLogin(auth.getName());
        if(ouser.isEmpty())
            return "none";
        else if(ouser.get().getRole() == Role.ADMIN)
            return "admin";
        return "user";
    }

    @GetMapping
    public String showMainPage(Model model)
    {
        model.addAttribute("role", getUserRole());
        return "redirect:index";
    }

    @GetMapping("/index")
    public String showMainPage2(Model model)
    {
        model.addAttribute("role", getUserRole());
        return "index";
    }
}
