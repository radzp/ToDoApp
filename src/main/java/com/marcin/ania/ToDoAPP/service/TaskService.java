package com.marcin.ania.ToDoAPP.service;

import com.marcin.ania.ToDoAPP.model.Task;
import com.marcin.ania.ToDoAPP.model.UserInfo;
import com.marcin.ania.ToDoAPP.repository.TaskRepository;
import com.marcin.ania.ToDoAPP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TaskService {

    // Wstrzyknięcie zależności - Repozytorium zadań oraz Repozytorium użytkowników
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Pobiera wszystkie zadania z bazy danych.
     *
     * @return Lista wszystkich zadań.
     */
    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }

    /**
     * Pobiera zadanie o określonym identyfikatorze.
     *
     * @param id Identyfikator zadania.
     * @return Zadanie o podanym identyfikatorze lub null, jeśli nie zostanie znalezione.
     */
    public Task get(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    /**
     * Pobiera wszystkie zadania przypisane do danego użytkownika.
     *
     * @param username Nazwa użytkownika.
     * @return Lista zadań przypisanych do danego użytkownika.
     */
    public Iterable<Task> getAllByUser(String username) {
        return taskRepository.findByUserinfoUsername(username);
    }

    /**
     * Usuwa zadanie o określonym identyfikatorze.
     *
     * @param id Identyfikator zadania do usunięcia.
     */
    public void remove(Long id) {
        taskRepository.deleteById(id);
    }


    /**
     * 1.Tworzy nowy obiekt zadania Task.
     * 2.Ustawia opis i stan ukończenia tego zadania na podstawie przekazanych argumentów description i is_completed.
     * 3.Wywołuje metodę findByUsername z repozytorium użytkowników (UserRepository) w celu znalezienia użytkownika o określonej nazwie (username).
     * Jeśli użytkownik o podanej nazwie zostanie znaleziony, jego obiekt zostanie zwrócony i przypisany do obiektu zadania (task.setUserinfo(user)).
     * 4.Następnie, jeśli użytkownik został znaleziony, zadanie zostaje zapisane w bazie danych poprzez wywołanie taskRepository.save(task).
     * Funkcja save w JPA wykonuje operację wstawiania lub aktualizacji w zależności od tego, czy obiekt posiada już identyfikator.
     * 5. Ostatecznie, jeśli użytkownik nie zostanie znaleziony (lub username jest null), metoda zwraca null lub podejmujesz inny zdefiniowany przez Ciebie krok obsługi w tym przypadku.
     *
     * @param description  Opis nowego zadania.
     * @param is_completed Stan ukończenia nowego zadania.
     * @param username     Nazwa użytkownika, do którego przypisane będzie zadanie.
     * @return Zadanie zostało zapisane w bazie danych.
     */
    @Transactional
    public Task save(String description, Boolean is_completed, String username) {
        Task task = new Task();
        task.setDescription(description);
        task.setIs_completed(is_completed);

        // Pobranie użytkownika o określonej nazwie użytkownika
        UserInfo user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            task.setUserinfo(user);
            return taskRepository.save(task); // Zapisz zadanie w bazie danych
        }

        return null; // Obsłuż ten przypadek zgodnie z Twoimi wymaganiami.
    }

    /**
     * Metoda updateTask aktualizuje istniejące zadanie.
     * <p>
     * 1. Pobiera istniejące zadanie na podstawie przekazanego identyfikatora (id).
     *      Jeśli zadanie o podanym identyfikatorze nie zostanie znalezione, zostanie rzucony wyjątek ResponseStatusException z kodem HTTP 404 (NOT FOUND).
     * 2. Aktualizuje opis i stan ukończenia tego zadania na podstawie przekazanego obiektu updatedTask.
     * 3. Zapisuje zaktualizowane zadanie w bazie danych.
     * 4. Zwraca zaktualizowane zadanie.
     *
     * @param id          Identyfikator istniejącego zadania.
     * @param updatedTask Zaktualizowane informacje o zadaniu.
     * @return Zaktualizowane zadanie zostało zapisane w bazie danych.
     * @throws ResponseStatusException Jeśli zadanie o podanym identyfikatorze nie zostanie znalezione.
     */
    public Task updateTask(Long id, Task updatedTask) {
        // Pobranie istniejącego zadania lub rzuca wyjątek ResponseStatusException, jeśli nie zostanie znalezione
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Aktualizacja opisu i stanu ukończenia
        task.setDescription(updatedTask.getDescription());
        task.setIs_completed(updatedTask.getIs_completed());

        // Zapisanie i zwrócenie zaktualizowanego zadania
        return taskRepository.save(task);
    }
}

