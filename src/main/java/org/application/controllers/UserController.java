package org.application.controllers;

import jakarta.servlet.http.HttpSession;
import org.application.models.User;
import org.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/html/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.loginUser(login, password);
        if(user!=null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
    @PostMapping("/html/signup")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model,
                               HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        User user = userService.registerUser(userForm);
        if(user!=null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        model.addAttribute("error", "User with this login or email already exists");
        return "signup";
    }
    @RequestMapping("/html/users/{page}")
    public String listUsers(@PathVariable int page, Model model) {
        int pageSize = 2;

        Page<User> pageUsers = userService.getAllUsers(page, pageSize);
        List<User> listUsers = pageUsers.getContent();
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", pageUsers.getTotalPages());
        model.addAttribute("startPage", Math.max(1, page - 2));
        model.addAttribute("endPage", Math.min(pageUsers.getTotalPages(), page + 2));
        return "users";
    }
    @RequestMapping("/html/users/change_role/{id}")
    public String changeRole(@PathVariable int id, @RequestParam int roleId, @RequestParam int page) {
        userService.changeRole(id, roleId);
        return "redirect:/html/users/" + page;
    }
    @RequestMapping("/html/users/change_salary/{id}")
    public String changeSalary(@PathVariable int id, @RequestParam double salary, @RequestParam int page) {
        userService.changeSalary(id, salary);
        return "redirect:/html/users/" + page;
    }
    @RequestMapping("/html/users/delete/{id}")
    public String deleteUser(@PathVariable int id, @RequestParam int page) {
        userService.deleteUser(id);
        return "redirect:/html/users/" + page;
    }
    @RequestMapping("/html/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
