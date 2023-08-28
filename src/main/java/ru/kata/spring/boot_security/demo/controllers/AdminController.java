package ru.kata.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getListUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("list", userService.getListUsers());
        model.addAttribute("roles", roleService.getListRoles());
        return "admin";
    }

    @GetMapping("/addUser")
    public String addUser(@AuthenticationPrincipal User user, Model model, @ModelAttribute("userAdd") User userAdd) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getListRoles());
        return "addNewUser";
    }


    @PostMapping()
    public String addUser(@ModelAttribute("userAdd") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addNewUser";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/update")
    public String editUser(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getListRoles());
        return "admin";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("user") @Valid User user, @PathVariable("id") long id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
