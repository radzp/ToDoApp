package com.marcin.ania.ToDoAPP.repository;

import com.marcin.ania.ToDoAPP.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task,Long> {
}
