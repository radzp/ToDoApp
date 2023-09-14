package com.marcin.ania.ToDoAPP.model;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private Boolean is_completed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userinfo_id")
    private UserInfo userinfo;

}
