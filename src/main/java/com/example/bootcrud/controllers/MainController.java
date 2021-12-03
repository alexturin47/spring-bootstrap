package com.example.bootcrud.controllers;

import com.example.bootcrud.dto.UserDto;
import com.example.bootcrud.entities.User;
import com.example.bootcrud.services.RoleService;
import com.example.bootcrud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.security.Principal;

@Controller
public class MainController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }


    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }



    // вызов страницы админа
    @GetMapping("/admin")
    public String adminPage(Model model, @ModelAttribute("user") User user, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.index());
        model.addAttribute("owner", userService.findByEmail(principal.getName()));
        return "admin";
    }


    @PatchMapping( "/admin/patch/{email}")
    public String updateAdmin(@ModelAttribute("user") UserDto userDto
            , @PathVariable("email") String email
            ,@RequestParam("roles") String[] roles) throws ValidationException {

        userDto.setId(userService.findByEmail(email).getId());
        userDto.setRoles(roleService.getRoleSet(roles));
        userService.updateUser(userDto);
        return "redirect:/admin";
    }



    // созадние нового юзера
    @GetMapping("admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("roles", roleService.index());
        return "/new";
    }

    @PostMapping("admin/new")
    public String create(Model model, @ModelAttribute("user") UserDto userDto
            , @RequestParam(name = "roles", required = false) String[] roles) throws ValidationException {

        userDto.setRoles(roleService.getRoleSet(roles));
        userService.saveUser(userDto);
        return "redirect:/admin";
    }



    @DeleteMapping( "/admin/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


    //  показ старницы профиля для юзера
    @GetMapping("/user")
    public String pageForReadProfile(Principal principal, Model model) {
        UserDto user = userService.findByEmail(principal.getName());
        model.addAttribute("roles", roleService.index());
        model.addAttribute("user", user);
        model.addAttribute("owner", userService.findByEmail(principal.getName()));
        return "user";
    }

}
