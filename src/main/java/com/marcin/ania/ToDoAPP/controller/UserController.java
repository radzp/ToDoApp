package com.marcin.ania.ToDoAPP.controller;

import com.marcin.ania.ToDoAPP.model.UserInfo;
import com.marcin.ania.ToDoAPP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return userService.addUser(userInfo);
    }
    @GetMapping("/all")
    public List<UserInfo> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<UserInfo> get(@PathVariable Long id) {
        Optional<UserInfo> userInfo = userService.getUserById(id);
        if (userInfo.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userInfo;
    }

    @GetMapping(value = "/username")
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
}
