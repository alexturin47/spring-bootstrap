package com.example.bootcrud.controllers;

import com.example.bootcrud.dto.UserDto;
import com.example.bootcrud.entities.User;
import com.example.bootcrud.services.RoleService;
import com.example.bootcrud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    // перенаправление в зависимости от роли
//    @GetMapping("/authorized")
//    public String pageForAuthenticatedUsers(Principal principal) {
//        UserDto user = userService.findByEmail(principal.getName());
//        if(user.hasAuthorities("ADMIN")) {
//            return "redirect:admin";
//        } else {
//            return "redirect:user";
//        }
//    }


    // вызов страницы админа
    @GetMapping("/admin")
    public String adminPage(Model model, @ModelAttribute("user") User user, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.index());
        model.addAttribute("owner", userService.findByEmail(principal.getName()));
        return "admin";
    }


    // редактирование юзера
//    @GetMapping("/edit/{username}")
//    public String edit(Model model, @PathVariable("username") String username) {
//        model.addAttribute("user", userService.findByEmail(username));
//        model.addAttribute("roles", roleServce.index());
//        return "/edit";
//    }

    @PatchMapping( "/{email}")
    public String updateAdmin(@ModelAttribute("user") UserDto userDto
            , @PathVariable("email") String email
            ,@RequestParam("roles") String[] roles) throws ValidationException {

        userDto.setId(userService.findByEmail(email).getId());
        userDto.setRoles(roleService.getRoleSet(roles));
        userService.updateUser(userDto);
        return "redirect:/admin";
    }



    // созадние нового юзера
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("roles", roleService.index());
        return "/new";
    }

    @PostMapping("/new")
    public String create(Model model, @ModelAttribute("user") UserDto userDto
            , @RequestParam(name = "roles", required = false) String[] roles) throws ValidationException {

        userDto.setRoles(roleService.getRoleSet(roles));
        userService.saveUser(userDto);
        return "redirect:/admin";
    }



    @DeleteMapping( "/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


    //  показ старницы профиля для админа
//    @GetMapping("/admin/user/{username}")
//    public String showUser(Model model, Principal principal, @PathVariable("username") String name) {
//        model.addAttribute("user", userService.findByEmail(name));
//        model.addAttribute("roles", roleService.index());
//        return "/user";
//    }

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
