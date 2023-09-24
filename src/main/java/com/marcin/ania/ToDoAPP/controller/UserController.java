package com.marcin.ania.ToDoAPP.controller;


import com.marcin.ania.ToDoAPP.model.UserInfo;
import com.marcin.ania.ToDoAPP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint do dodawania nowego użytkownika
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        // Wywołanie metody z serwisu do dodawania użytkownika i zwrócenie wyniku
        return userService.addUser(userInfo);
    }

    // Endpoint do pobierania listy wszystkich użytkowników
    @GetMapping("/all")
    public List<UserInfo> getAll() {
        // Wywołanie metody z serwisu do pobierania wszystkich użytkowników
        return userService.getAllUsers();
    }

    // Endpoint do pobierania konkretnego użytkownika na podstawie ID
    @GetMapping("/{id}")
    public Optional<UserInfo> get(@PathVariable Long id) {
        // Wywołanie metody z serwisu do pobierania użytkownika na podstawie ID
        Optional<UserInfo> userInfo = userService.getUserById(id);
        // Jeśli użytkownik nie został znaleziony, zwróć błąd NOT FOUND (404)
        if (userInfo.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userInfo;
    }


    @PutMapping("/{id}")
    public UserInfo editUserById(@PathVariable Long id, @RequestBody UserInfo newUser) {
        return userService.updateUser(id, newUser);
    }


    // Endpoint do pobierania nazwy aktualnie zalogowanego użytkownika
    @GetMapping(value = "/logged/username")
    @ResponseBody
    public String getCurrentUserName(Authentication authentication) {
        // Pobranie nazwy użytkownika z obiektu Authentication
        return authentication.getName();
    }

    // Endpoint do pobierania nazwy aktualnie zalogowanego użytkownika
    @GetMapping(value = "/logged/authorities")
    @ResponseBody
    public Collection<? extends GrantedAuthority> getCurrentUserAuthorities(Authentication authentication) {
        // Pobranie nazwy użytkownika z obiektu Authentication
        return authentication.getAuthorities();
    }

    @PutMapping(value = "/logged/changePassword")
    public ResponseEntity<String> changeCurrentUserPassword(@RequestParam("oldPassword") String oldPassword,
                                                            @RequestParam("newPassword") String newPassword,
                                                            Authentication authentication) {
        String username = authentication.getName();
        Optional<UserInfo> userInfo = userService.findByUsername(username);

        if (userInfo.isPresent()) {
            Long userId = userInfo.get().getId();
            boolean isChanged = userService.updateUserPassword(userId, oldPassword, newPassword);

            if (isChanged) {
                return ResponseEntity.ok("Password changed successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to change password. Please check your old password.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PutMapping("/logged/changeUsername")
    public ResponseEntity<String> changeCurrentUserUsername(@RequestParam("oldUsername") String oldUsername,
                                                            @RequestParam("newUsername") String newUsername,
                                                            Authentication authentication) {
        String username = authentication.getName();
        Optional<UserInfo> userInfo = userService.findByUsername(username);

        if(userInfo.isPresent()){
            Long userId = userInfo.get().getId();
            boolean isChanged = userService.updateUserUsername(userId, oldUsername, newUsername);

            if (isChanged) {
                return ResponseEntity.ok("Username changed successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to change username. Please check your old username.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        }
}





