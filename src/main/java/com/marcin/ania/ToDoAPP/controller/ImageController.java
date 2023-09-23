package com.marcin.ania.ToDoAPP.controller;

import com.marcin.ania.ToDoAPP.model.UserInfo;
import com.marcin.ania.ToDoAPP.service.UserService;
import com.marcin.ania.ToDoAPP.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@Transactional
public class ImageController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/logged/avatar")
    public ResponseEntity<byte[]> getCurrentUserAvatar(Authentication authentication){
        String username = authentication.getName();
        Optional<UserInfo> userInfo = userService.findByUsername(username);
        if (userInfo.isPresent()) {
            UserInfo presentUserInfo = userInfo.get();
            byte[] imageData = ImageUtils.decompressImage(presentUserInfo.getAvatarData().getImageData());
            if (imageData != null) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageData);
            }
            else return ResponseEntity.notFound().build();
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping("/user/logged/avatar")
    public RedirectView changeCurrentUserAvatar(Authentication authentication, @RequestParam("image") MultipartFile file){
        String username = authentication.getName();
        Optional<UserInfo> existingUserInfo = userService.findByUsername(username);
        if (existingUserInfo.isPresent()) {
            UserInfo presentUserInfo = existingUserInfo.get();
            userService.updateUserAvatar(presentUserInfo.getId(), file);
        }
        return new RedirectView("/settings");
    }
}
