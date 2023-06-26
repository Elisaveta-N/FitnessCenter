package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("registration")
    public String showTestRegistry(Model model) {
        return "registration";
    }

    @PostMapping("registration")
    public String showTestRegistryPost(User user, Model model) {
        if(user.getLogin().isEmpty() || !userService.findByLogin(user.getLogin()).isEmpty()) {
            model.addAttribute("loginError", "Такой login уже используется");
            return "registration";
        }

        if(user.getFirstName().isEmpty() || user.getLastName().isEmpty())
        {
            model.addAttribute("emptyError", "Поле не должно быть пустым");
            return "registration";
        }

        if(user.getPassword().isEmpty() || !user.getPassword().equals(user.getPassword2()))
        {
            model.addAttribute("passwordError", "Пароли должны совпадать");
            return "registration";
        }

        userService.create(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}
