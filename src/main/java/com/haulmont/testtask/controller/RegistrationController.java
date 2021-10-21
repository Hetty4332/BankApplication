package com.haulmont.testtask.controller;


import com.haulmont.testtask.model.User;
import com.haulmont.testtask.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class RegistrationController {

    @Autowired
    private UserDetailsServiceImpl userService;


    @GetMapping("/login")
    public String login() {
        return "login";

    }

    @GetMapping("/")
    public String getMain(Model model) {
        return "main";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            bindingResult.addError(new FieldError("userForm", "passwordConfirm", "Пароли не совпадают"));
            return "registration";
        }
        if (!userService.saveUser(userForm)) {
            bindingResult.addError(new FieldError("userForm", "username", "Пользователь с таким именем уже существует"));
            return "registration";
        }
        return "redirect:/login";
    }
}