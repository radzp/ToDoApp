package com.marcin.ania.ToDoAPP.controller;

import com.marcin.ania.ToDoAPP.model.Task;
import com.marcin.ania.ToDoAPP.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    // Wstrzyknięcie zależności - Serwis do obsługi zadań
    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Pobranie wszystkich zadań z bazy danych
    @GetMapping("/all")
    public Iterable<Task> getAllOfDB() {
        return taskService.getAll();
    }

    // Pobranie wszystkich zadań przypisanych do zalogowanego użytkownika
    @GetMapping("")
    public Iterable<Task> getAllForUser() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskService.getAllByUser(loggedInUsername);
    }

    // Pobranie zadania o określonym identyfikatorze
    @GetMapping("/{id}")
    public Task get(@PathVariable Long id) {
        Task task = taskService.get(id);
        if (task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return task;
    }

    // Usunięcie zadania o określonym identyfikatorze
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.remove(id);
    }

    // Dodanie nowego zadania przypisanego do zalogowanego użytkownika
    @PostMapping("")
    @Transactional
    public Task create(@RequestBody Task newTask) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskService.save(newTask.getDescription(), newTask.getIs_completed(), loggedInUsername);
    }

    // Aktualizacja istniejącego zadania
    @PutMapping("/{id}")
    public Task edit(@PathVariable Long id, @RequestBody Task newTask){
        return taskService.updateTask(id, newTask);
    }

}
