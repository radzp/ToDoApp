package com.marcin.ania.ToDoAPP.config;

import com.marcin.ania.ToDoAPP.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Konfiguracja usługi do obsługi szczegółów użytkowników
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService();
    }

    // Konfiguracja łańcucha filtrów zabezpieczeń
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                // Ustalanie, które żądania są dozwolone, a które wymagają autoryzacji
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(mvc.pattern("/static/**")).permitAll()
                        .requestMatchers(mvc.pattern("/")).authenticated()
                        .requestMatchers(mvc.pattern("/settings")).authenticated()
                        .requestMatchers(mvc.pattern("/tasks/**")).permitAll()
                        .requestMatchers(mvc.pattern("/tasks")).permitAll()
                        .requestMatchers(mvc.pattern("/**"), mvc.pattern("/user/new")).permitAll()
                        .anyRequest().permitAll()
                )
                // Wyłączenie ochrony przed atakami CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Konfiguracja formularza logowania
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")
                        .permitAll()
                )
                // Konfiguracja wylogowania
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll());

        return http.build();
    }

    // Konfiguracja enkodera hasła
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Konfiguracja dostępu do obiektów ModelAndView
    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector)
    {
        return new MvcRequestMatcher.Builder(introspector);
    }

    // Konfiguracja dostawcy autoryzacji
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
}

