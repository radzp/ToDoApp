document.addEventListener("DOMContentLoaded", function () {
    const tasksList = document.getElementById('tasksList');
    const completedTabBtn = document.getElementById('completedTabBtn');
    const activeTabBtn = document.getElementById('activeTabBtn');
    const allTabBtn = document.getElementById('allTabBtn');
    const addButton = document.getElementById('addButton'); // New line
    const newTaskInput = document.getElementById('newTask'); // New line
    let showCompletedTasks = null;

    async function fetchAndDisplayTasks() {
        try {
            const response = await fetch('http://localhost:8080/tasks');

            if (!response.ok) {
                console.error('Wystąpił błąd podczas pobierania danych.');
                return;
            }

            const tasks = await response.json();

            tasksList.innerHTML = '';

            tasks.forEach(task => {
                const taskItem = createTaskElement(task);
                if (showCompletedTasks === null || (showCompletedTasks && task.is_completed) || (!showCompletedTasks && !task.is_completed)) {
                    tasksList.appendChild(taskItem);
                }
            });
        } catch (error) {
            console.error('Wystąpił błąd:', error);
        }
    }


    async function deleteTask(taskId) {
        try {
            const response = await fetch(`http://localhost:8080/tasks/${taskId}`, {
                method: 'DELETE',
            });

            if (!response.ok) {
                console.error('Wystąpił błąd podczas usuwania zadania.');
                return;
            }

            await fetchAndDisplayTasks(); // Aktualizacja listy po usunięciu zadania
        } catch (error) {
            console.error('Wystąpił błąd:', error);
        }
    }


    async function statusTask(task){
        const taskId = task.id;
        const taskDescription = task.description;
        const isCompleted = task.is_completed;
        try{
            const response = await fetch(`http://localhost:8080/tasks/${taskId}`, {
                method: 'PUT',
                headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                description: taskDescription,
                is_completed: !isCompleted
            })
            });

            if (!response.ok) {
                console.error('Wystąpił błąd.');
                return;
            }

            await fetchAndDisplayTasks();

        } catch(error){
            console.error('Wystąpił błąd:', error);
        }

    }

    function createTaskElement(task) {
        const taskItem = document.createElement('li');
        taskItem.classList.add('todo-item');

        const statusBtn = document.createElement('span');
        statusBtn.classList.add('status-btn');
        if (task.is_completed) {
            statusBtn.innerHTML = '<i class="far fa-check-square me-2"></i>';
        } else {
            statusBtn.innerHTML = '<i class="far fa-square me-2"></i>';
        }

        const taskText = document.createElement('span');
        taskText.classList.add('task-text');
        taskText.textContent = task.description;
        if (task.is_completed) {
            taskText.classList.add('completed');
        }

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

        statusBtn.addEventListener('click', () => {
            statusTask(task);
            toggleTaskCompletion(taskItem, task);
        });

        deleteBtn.addEventListener('click', () => {
            const shouldDelete = confirm("Are you sure? This will delete this task.");
            if (shouldDelete) {
                deleteTask(task.id);
            }
        });

        return taskItem;
    }

    function toggleTaskCompletion(taskItem, task) {
        task.completed = !task.completed;

        if (showCompletedTasks) {
            if (task.completed) {
                taskItem.querySelector('.status-btn i').classList.remove('fa-square');
                taskItem.querySelector('.status-btn i').classList.add('fa-check-square');
                taskItem.querySelector('.task-text').classList.add('completed');
            } else {
                taskItem.querySelector('.status-btn i').classList.remove('fa-check-square');
                taskItem.querySelector('.status-btn i').classList.add('fa-square');
                taskItem.querySelector('.task-text').classList.remove('completed');
                tasksList.appendChild(taskItem);
            }
        } else {
            if (task.completed) {
                taskItem.querySelector('.status-btn i').classList.remove('fa-square');
                taskItem.querySelector('.status-btn i').classList.add('fa-check-square');
                taskItem.querySelector('.task-text').classList.add('completed');
            } else {
                taskItem.querySelector('.status-btn i').classList.remove('fa-check-square');
                taskItem.querySelector('.status-btn i').classList.add('fa-square');
                taskItem.querySelector('.task-text').classList.remove('completed');
            }
        }
    }


    completedTabBtn.addEventListener('click', () => {
        showCompletedTasks = true;
        fetchAndDisplayTasks();
    });

    activeTabBtn.addEventListener('click', () => {
        showCompletedTasks = false;
        fetchAndDisplayTasks();
    });

    allTabBtn.addEventListener('click', () => {
        showCompletedTasks = null;
        fetchAndDisplayTasks();
    });

    addButton.addEventListener('click', async () => {
        const newTaskText = newTaskInput.value.trim();
        if (newTaskText !== '') {
            try {
                const response = await fetch('http://localhost:8080/tasks', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        description: newTaskText,
                        is_completed: false
                    })
                });

                if (!response.ok) {
                    console.error('Wystąpił błąd podczas dodawania zadania.');
                    return;
                }

                newTaskInput.value = '';
                await fetchAndDisplayTasks();
            } catch (error) {
                console.error('Wystąpił błąd:', error);
            }
        }
    });

    fetchAndDisplayTasks();
});

