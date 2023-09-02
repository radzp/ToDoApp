//package com.marcin.ania.ToDoAPP.service;
//
//import com.marcin.ania.ToDoAPP.config.UserUserDetails;
//import com.marcin.ania.ToDoAPP.model.UserInfo;
//import com.marcin.ania.ToDoAPP.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//       Optional<UserInfo> user =  userRepository.findByUsername(username);
//       return user.map(UserUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found "+ user));
//    }
//
//    // Add new user
//    public String addUser(UserInfo userInfo){
//        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//        userRepository.save(userInfo);
//        return "User has been saved";
//    }
//
//    // Get all users
//    public List<UserInfo> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    // Get user by ID
//    public Optional<UserInfo> getUserById(Long id) {
//        return userRepository.findById(id);
//    }
//
//    // Update user
//    public UserInfo updateUser(Long id, UserInfo userInfo) {
//        Optional<UserInfo> user = userRepository.findById(id);
//        if (user.isPresent()) {
//            UserInfo existingUser = user.get();
//            existingUser.setUsername(userInfo.getUsername());
//            existingUser.setPassword(userInfo.getPassword());
//            existingUser.setRoles(userInfo.getRoles());
//            existingUser.setEmail(userInfo.getEmail());
//            return userRepository.save(existingUser);
//        }
//        return null;
//    }
//
//    // Delete all users
//    public void deleteAllUsers() {
//        userRepository.deleteAll();
//    }
//
//    // Delete user
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//}
