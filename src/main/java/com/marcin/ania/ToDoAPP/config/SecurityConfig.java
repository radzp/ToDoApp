package com.marcin.ania.ToDoAPP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails admin = User.withUsername("Marcinek")
                .password(encoder.encode("Pwd1"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("Ania")
                .password(encoder.encode("Pwd2"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(mvc.pattern("/static/**")).permitAll()
                        .requestMatchers(mvc.pattern("/")).hasAnyRole("USER","ADMIN")
                        .requestMatchers(mvc.pattern("/tasks/**")).hasAnyRole("USER","ADMIN")
                        .requestMatchers(mvc.pattern("/tasks")).hasAnyRole("USER","ADMIN")
                        .requestMatchers(mvc.pattern("/**")).permitAll()
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/tasks/**")).authenticated()
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/login")).permitAll()
//                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/tasks/**")).authenticated()
//                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/tasks/**")).authenticated()
                        .anyRequest().authenticated()
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
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector)
    {
        return new MvcRequestMatcher.Builder(introspector);
    }


}
