package com.marcin.ania.ToDoAPP.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    private Boolean is_completed;

    // Relacja wiele do jednego z klasą UserInfo - wiele zadań może należeć do jednego użytkownika
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // FetchType.LAZY - Ładuj użytkownika tylko wtedy, gdy jest potrzebny
    @JoinColumn(name = "userinfo_id") // Nazwa kolumny w bazie danych, która przechowuje klucz obcy do użytkownika
    @JsonBackReference // Oznacza, że ta strona relacji (Task) zostanie zignorowana podczas serializacji
    private UserInfo userinfo;

}
