package com.marcin.ania.ToDoAPP.config;

import com.marcin.ania.ToDoAPP.model.UserInfo;
import com.marcin.ania.ToDoAPP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void initializeData() {
        if (!userRepository.findByUsername("admin").isPresent()) {
            UserInfo admin = new UserInfo();
            admin.setUsername("admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.setRoles("ROLE_ADMIN");
            userRepository.save(admin);
        }
    }
}
