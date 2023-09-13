package com.marcin.ania.ToDoAPP.config;

import com.marcin.ania.ToDoAPP.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService();
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(mvc.pattern("/static/**")).permitAll()
                        .requestMatchers(mvc.pattern("/")).authenticated()
                        .requestMatchers(mvc.pattern("/tasks/**")).permitAll()
                        .requestMatchers(mvc.pattern("/tasks")).permitAll()
                        .requestMatchers(mvc.pattern("/**"), mvc.pattern("/user/new")).permitAll()
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
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll());

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

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
