package com.marcin.ania.ToDoAPP.controller;

import com.marcin.ania.ToDoAPP.model.Task;
import com.marcin.ania.ToDoAPP.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TaskController {

    //dependency injection
    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public Iterable<Task> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/tasks/{id}")
    public Task get(@PathVariable Long id) {
        Task task = taskService.get(id);
        if (task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return task;
    }

    @DeleteMapping("/tasks/{id}")
    public void delete(@PathVariable Long id){
        taskService.remove(id);
    }

    @PostMapping("/tasks")
    public Task create(@RequestBody Task newTask){
        return taskService.save(newTask.getDescription());
    }

    @PutMapping("/tasks/{id}")
    public Task edit(@PathVariable Long id, @RequestBody Task newTask){
        return taskService.updateTask(id, newTask);
    }








}
