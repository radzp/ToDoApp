package com.marcin.ania.ToDoAPP.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Task {

    @Id
    private Long id;

    @NotEmpty
    private String description;

    @NotNull
    private Boolean is_completed;

    public Task() {
    }

    public Task(Long id, String description, Boolean is_completed) {
        this.id = id;
        this.description = description;
        this.is_completed = is_completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_completed() {
        return is_completed;
    }

    public void setIs_completed(Boolean is_completed) {
        this.is_completed = is_completed;
    }
}
