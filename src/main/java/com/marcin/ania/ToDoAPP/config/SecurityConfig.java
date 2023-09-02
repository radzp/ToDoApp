package com.marcin.ania.ToDoAPP.config;

import com.marcin.ania.ToDoAPP.roles.UserRole;
//import com.marcin.ania.ToDoAPP.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new UserService();
//}


        // Test purpose, comment that and uncomment code above
        @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails admin = User.withUsername("Marcinek")
                .password(encoder.encode("Pwd1"))
                .roles(UserRole.ADMIN.toString(), UserRole.USER.toString())
                .build();
        UserDetails user = User.withUsername("Ania")
                .password(encoder.encode("Pwd2"))
                .roles(UserRole.ADMIN.toString(), UserRole.USER.toString())
                .build();
        return new InMemoryUserDetailsManager(admin, user);
//        return new UserService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(mvc.pattern("/static/**")).permitAll()
                        .requestMatchers(mvc.pattern("/")).authenticated()
                        .requestMatchers(mvc.pattern("/tasks/**")).permitAll()
                        .requestMatchers(mvc.pattern("/tasks")).permitAll()
                        .requestMatchers(mvc.pattern("/**"), mvc.pattern("user/new")).permitAll()
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/tasks/**")).authenticated()
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/login")).permitAll()
//                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/tasks/**")).authenticated()
//                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/tasks/**")).authenticated()
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector)
    {
        return new MvcRequestMatcher.Builder(introspector);
    }

}
