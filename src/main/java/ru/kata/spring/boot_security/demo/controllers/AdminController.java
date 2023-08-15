package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getListUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("emailUser", user);
        model.addAttribute("list", userService.getListUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        List<Role> roles = roleService.getListRoles();
        model.addAttribute("roles", roles);
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String editUser(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "updateUser";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("user") User user, @RequestParam(value = "checkedRoles") String[] selectResult) {
        Set<Role> roles = new HashSet<>();
        for (String s : selectResult) {
            roles.add(roleService.getRole("ROLE_" + s));
            user.setRoles(roles);
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
