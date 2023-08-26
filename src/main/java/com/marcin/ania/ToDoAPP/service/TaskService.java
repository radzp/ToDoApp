package com.marcin.ania.ToDoAPP.service;

import com.marcin.ania.ToDoAPP.model.Task;
import com.marcin.ania.ToDoAPP.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TaskService {

    //dependency injection
    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task get(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void remove(Long id) {
        taskRepository.deleteById(id);
    }

    public Task save(String description, Boolean is_completed){
        Task task = new Task();
        task.setDescription(description);
        task.setIs_completed(is_completed);
        return taskRepository.save(task); // Save in database
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        task.setDescription(updatedTask.getDescription());
        task.setIs_completed(updatedTask.getIs_completed());

        return taskRepository.save(task);
    }


}
