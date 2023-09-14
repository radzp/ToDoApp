package com.marcin.ania.ToDoAPP.config;


import com.marcin.ania.ToDoAPP.model.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserUserDetails implements UserDetails {

    // Przechowuje dane użytkownika w obiekcie UserDetails

    private String username;  // Przechowuje nazwę użytkownika
    private String password;  // Przechowuje hasło użytkownika
    private List<GrantedAuthority> authorities;  // Przechowuje role użytkownika

    // Konstruktor, który konwertuje obiekt UserInfo na obiekt UserDetails
    public UserUserDetails(UserInfo userInfo) {
        username = userInfo.getUsername();  // Ustawia nazwę użytkownika
        password = userInfo.getPassword();  // Ustawia hasło użytkownika

        // Konwertuje role zapisane jako string do obiektów GrantedAuthority i przechowuje w liście
        authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;  // Zwraca listę ról użytkownika
    }

    @Override
    public String getPassword() {
        return password;  // Zwraca hasło użytkownika
    }

    @Override
    public String getUsername() {
        return username;  // Zwraca nazwę użytkownika
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Zwraca true, jeśli konto użytkownika nie wygasło
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Zwraca true, jeśli konto użytkownika nie jest zablokowane
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Zwraca true, jeśli poświadczenia użytkownika nie wygasły
    }

    @Override
    public boolean isEnabled() {
        return true;  // Zwraca true, jeśli konto użytkownika jest aktywne
    }
}
