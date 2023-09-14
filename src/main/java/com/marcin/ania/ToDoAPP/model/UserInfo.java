package com.marcin.ania.ToDoAPP.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "userinfo")
@Data
@NoArgsConstructor

public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String roles;

    public UserInfo(String username, String email, String password, String roles){
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    @OneToMany(
            mappedBy = "userinfo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Task> taskList = new ArrayList<>();

}
