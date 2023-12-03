package com.ssf.day13workshop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssf.day13workshop.Day13WorkshopApplication;
import com.ssf.day13workshop.model.User;
import com.ssf.day13workshop.service.Contacts;

import jakarta.validation.Valid;

@Controller
@RequestMapping
public class FormController {
    Contacts contacts = new Contacts();
    String addressBookDirPath = Day13WorkshopApplication.getAddressBookDirPath();

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "contact";
    }

    @PostMapping("/contact")
    public String processForm(@Valid @ModelAttribute User user, BindingResult binding) {
        if (binding.hasErrors()) {
            return "contact";
        }

        if (!user.isValidAge()) {
            FieldError err = new FieldError("user", "dob", "Age must be between 10 and 100 years old");
            binding.addError(err);
            return "contact";
        }
        try {
            contacts.save(user);
            System.out.println("Contact saved in: " + addressBookDirPath);
        } catch (IOException e) {
            return "error";
        }
        return "contact-created";
    }

    @GetMapping("/contact/{id}")
    public String loadID(@PathVariable String id, Model model) {
        try {
            List<String> userFields = contacts.loadUserById(id);
            model.addAttribute("userFields", userFields);
            return "contact-by-id";
        } catch (IOException e){
            return "not-found";
        }

    }

    @GetMapping("/contact/list")
    public String loadLinks(Model model) {
        try {
            List<User> users = contacts.loadUsers();
        model.addAttribute("users", users);
        return "contacts";
    } catch (IOException e){
        return "error";
    }

}
}