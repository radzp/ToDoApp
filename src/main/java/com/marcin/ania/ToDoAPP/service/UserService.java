package com.marcin.ania.ToDoAPP.service;

import com.marcin.ania.ToDoAPP.config.UserUserDetails;
import com.marcin.ania.ToDoAPP.model.UserInfo;
import com.marcin.ania.ToDoAPP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Metoda wymagana do implementacji interfejsu UserDetailsService, używana do logowania użytkownika.
     *
     * @param username Nazwa użytkownika.
     * @return Obiekt UserDetails, który reprezentuje użytkownika w systemie.
     * @throws UsernameNotFoundException Jeśli użytkownik o podanej nazwie nie zostanie znaleziony.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user = userRepository.findByUsername(username);

        // Sprawdza, czy użytkownik o podanej nazwie istnieje
        return user.map(UserUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + user));
    }

    /**
     * Dodaje nowego użytkownika do bazy danych.
     *
     * @param userInfo Informacje o nowym użytkowniku.
     * @return Komunikat potwierdzający zapisanie użytkownika.
     */
    public String addUser(UserInfo userInfo){
        // Szyfruje hasło przed zapisaniem w bazie danych
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        // Zapisuje użytkownika w repozytorium
        userRepository.save(userInfo);
        return "User has been saved";
    }

    /**
     * Zwraca listę wszystkich użytkowników.
     *
     * @return Lista wszystkich użytkowników.
     */
    public List<UserInfo> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Zwraca użytkownika na podstawie jego ID.
     *
     * @param id Identyfikator użytkownika.
     * @return Obiekt Optional zawierający informacje o użytkowniku lub pusty, jeśli użytkownik o podanym ID nie istnieje.
     */
    public Optional<UserInfo> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Aktualizuje dane użytkownika.
     *
     * @param id       Identyfikator użytkownika do aktualizacji.
     * @param userInfo Nowe informacje o użytkowniku.
     * @return Zaktualizowane informacje o użytkowniku lub null, jeśli użytkownik o podanym ID nie istnieje.
     */
    public UserInfo updateUser(Long id, UserInfo userInfo) {
        Optional<UserInfo> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserInfo existingUser = user.get();
            existingUser.setUsername(userInfo.getUsername());
            existingUser.setPassword(userInfo.getPassword());
            existingUser.setRoles(userInfo.getRoles());
            existingUser.setEmail(userInfo.getEmail());
            return userRepository.save(existingUser);
        }
        return null;
    }

    /**
     * Usuwa wszystkich użytkowników z bazy danych.
     */
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    /**
     * Usuwa użytkownika na podstawie jego ID.
     *
     * @param id Identyfikator użytkownika do usunięcia.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
