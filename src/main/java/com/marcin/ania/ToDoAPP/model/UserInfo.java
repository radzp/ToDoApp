package com.marcin.ania.ToDoAPP.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private ImageData avatarData;

    // Relacja jeden do wielu z klasą Task - jeden użytkownik może mieć wiele zadań
    @OneToMany(
            mappedBy = "userinfo", // Nazwa pola w klasie Task, które wskazuje na użytkownika
            cascade = CascadeType.ALL, // Wszystkie operacje (np. usuwanie zadań), będą propagowane do powiązanych zadań
            orphanRemoval = true // Jeśli zadanie nie ma przypisanego użytkownika, zostanie usunięte
    )
    @JsonManagedReference // Oznacza, że ta strona relacji (UserInfo) jest właścicielem i zostanie zserializowana
    private List<Task> taskList = new ArrayList<>();



    public UserInfo(String username, String email, String password, String roles){
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
