package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String getUsers(Model model) {
        model.addAttribute("user", userService.getUserById(2));
        return "index";
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        user.setStringOfAllUserRoles(user.getStringOfRoles(user));
        model.addAttribute("user", user);
        return "user";
    }


}
