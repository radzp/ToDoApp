//package com.marcin.ania.ToDoAPP.config;
//
//
//import com.marcin.ania.ToDoAPP.model.UserInfo;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class UserUserDetails implements UserDetails {
//
//    //Convert everything from user to userDetails
//    private String username;
//    private String password;
//    private List<GrantedAuthority> authorities;
//
//    public UserUserDetails(UserInfo userInfo) {
//        username=userInfo.getUsername();
//        password=userInfo.getPassword();
//        authorities=Arrays.stream(userInfo.getRoles().toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}