document.addEventListener("DOMContentLoaded", function () {
    const tasksList = document.querySelector('.list-unstyled');
    async function fetchAndDisplayTasks() {
        try {
            const response = await fetch('http://localhost:8080/tasks');

            if (!response.ok) {
                console.error('Wystąpił błąd podczas pobierania danych.');
                return;
            }

            const tasks = await response.json();

            tasksList.innerHTML = ''; // Wyczyść listę przed dodaniem nowych zadań

            tasks.forEach(task => {
                const taskItem = createTaskElement(task);
                tasksList.appendChild(taskItem);
            });
        } catch (error) {
            console.error('Wystąpił błąd:', error);
        }
    }

    function createTaskElement(task) {
        const taskItem = document.createElement('li');
        taskItem.classList.add('todo-item');

        const statusBtn = document.createElement('span');
        statusBtn.classList.add('status-btn');
        statusBtn.innerHTML = '<i class="far fa-square me-2"></i>';

        const taskText = document.createElement('span');
        taskText.classList.add('task-text');
        taskText.textContent = task.description;

        const deleteBtn = document.createElement('button');
        deleteBtn.classList.add('delete-btn');
        deleteBtn.innerHTML = '<i class="far fa-trash-alt"></i>';

        const editBtn = document.createElement('button');
        editBtn.classList.add('edit-btn');
        editBtn.innerHTML = '<i class="far fa-edit"></i>';

        taskItem.appendChild(statusBtn);
        taskItem.appendChild(taskText);
        taskItem.appendChild(deleteBtn);
        taskItem.appendChild(editBtn);

        return taskItem;
    }

    fetchAndDisplayTasks();
});