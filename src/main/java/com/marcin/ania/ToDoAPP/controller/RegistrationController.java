package com.marcin.ania.ToDoAPP.controller;

import com.marcin.ania.ToDoAPP.model.ImageData;
import com.marcin.ania.ToDoAPP.model.UserInfo;
import com.marcin.ania.ToDoAPP.service.UserService;
import com.marcin.ania.ToDoAPP.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;


    // Metoda obsługująca GET request na endpoint /register
    @GetMapping("/register")
    public RedirectView showRegistrationForm(Model model){
        // Dodanie nowego obiektu UserInfo do modelu, który będzie wykorzystywany w formularzu rejestracji
        model.addAttribute("UserInfo", new UserInfo());
        // Zwrócenie widoku "login", który zawiera formularz rejestracji
        return new RedirectView("login");
    }

    // Metoda obsługująca POST request na endpoint /register
    @PostMapping("/register")
    public RedirectView registerUser(@ModelAttribute("UserInfo") UserInfo userInfo){
        // Ustawienie roli użytkownika jako "USER"
        userInfo.setRoles("USER");

        String defaultAvatarPath = "src/main/resources/static/images/default_avatar.png";

        try {
            byte[] defaultImageData = Files.readAllBytes(Paths.get(defaultAvatarPath));
            ImageData defaultAvatar = ImageData.builder().name("default_avatar.png")
                    .type("image/png").imageData(ImageUtils.compressImage(defaultImageData)).build();
            userInfo.setAvatarData(defaultAvatar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Wywołanie metody z serwisu do dodawania użytkownika
        userService.addUser(userInfo);
        // Przekierowanie użytkownika na stronę logowania po zarejestrowaniu
        return new RedirectView("login");
    }

}
