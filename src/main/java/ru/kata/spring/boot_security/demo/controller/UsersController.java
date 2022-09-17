package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class UsersController {

    @Autowired
    private RoleRepository roleRepository;

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    private String getStringOfRoles(User user) {
        StringBuilder str = new StringBuilder();
        Set<Role> roles = user.getRoles();
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            str.append(iterator.next().getRole().substring(5));
            if (iterator.hasNext()){
                str.append(", ");
            }
        }
        return str.toString();
    }

    @GetMapping("/")
    public String getUsers(Model model) {
        model.addAttribute("user", userService.getUserById(2));
        return "index";
    }

    @GetMapping("/user")
    public String showUserPage(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        user.setStringOfAllUserRoles(getStringOfRoles(user));
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
//        List<String> listOfRolesAllUsers = userService.getAllUsers().stream().map(this::getStringOfRoles).collect(Collectors.toList());
        List<User> users = userService.getAllUsers();
        users.stream().forEach(u -> u.setStringOfAllUserRoles(getStringOfRoles(u)));
        model.addAttribute("users", users);
//        model.addAttribute("roles", listOfRolesAllUsers);
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "new";
    }

    @PostMapping("/admin")
    public String addNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "new";
        }
        System.out.println(user);
        System.out.println(user.getRoles());
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        user.setStringOfAllUserRoles(getStringOfRoles(user));
        model.addAttribute("user", user);
        return "delete";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        user.setStringOfAllUserRoles(getStringOfRoles(user));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("roles", roleRepository.findAll());
            return "/edit";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
