package com.marcin.ania.ToDoAPP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
W tym przypadku konfigurujesz przekierowanie ścieżki /login na widok o nazwie "login".
Jest to przydatne w przypadku, gdy masz stronę logowania,
która jest dostarczana jako widok przez Twoją aplikację.
W ten sposób mówisz Spring MVC,
że żądanie pod ścieżką /login powinno być obsłużone przez widok "login".
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    // Konfiguracja przekierowania adresu /login na widok "login"
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login");
    }

}
