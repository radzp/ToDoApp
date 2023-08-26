document.addEventListener("DOMContentLoaded", function () {
    const tasksList = document.getElementById('tasksList');
    const completedTabBtn = document.getElementById('completedTabBtn');
    const activeTabBtn = document.getElementById('activeTabBtn');
    const allTabBtn = document.getElementById('allTabBtn');
    const addButton = document.getElementById('addButton'); // New line
    const newTaskInput = document.getElementById('newTask'); // New line
    const progressBar = document.getElementById('progress');
    let showCompletedTasks = null;
    let allTasksCounter = 0;
    let completedTasksCounter = 0;

    allTabBtn.classList.add('tab-btn');
    completedTabBtn.classList.add('tab-btn');
    activeTabBtn.classList.add('tab-btn');


    async function fetchAndDisplayTasks() {
        try {
            const response = await fetch('http://localhost:8080/tasks');

            if (!response.ok) {
                console.error('Wystąpił błąd podczas pobierania danych.');
                return;
            }

            const tasks = await response.json();

            tasksList.innerHTML = '';

            allTasksCounter = 0;
            completedTasksCounter = 0;

            tasks.forEach(task => {
                allTasksCounter++;
                if(task.is_completed){
                    completedTasksCounter++;
                }
                const taskItem = createTaskElement(task);
                if (showCompletedTasks === null || (showCompletedTasks && task.is_completed) || (!showCompletedTasks && !task.is_completed)) {
                    tasksList.appendChild(taskItem);
                }
            });
            updateProgressBar();
              
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

    let previousResult = -1; // Inicjalizujemy poprzednią wartość result na wartość, która na pewno się nie pojawi

    function updateProgressBar() {
        const newResult = (completedTasksCounter / allTasksCounter) * 100;
        const roundedResult = Math.round(newResult); // Zaokrąglamy wynik w gore

        if (roundedResult !== previousResult) {
            previousResult = roundedResult; // Zapisujemy nową wartość result jako poprzednią
            progressBar.setAttribute("aria-valuenow", roundedResult.toString());
            progressBar.innerText = roundedResult.toString() + "%";
            progressBar.style.width = roundedResult.toString() + "%";
        }
    }
    
    function changeToInputBtns(taskItem, editInput, confirmBtn, abortBtn, statusBtn, taskText, deleteBtn, editBtn){
        taskItem.removeChild(editInput);
        taskItem.removeChild(confirmBtn);
        taskItem.removeChild(abortBtn);
        taskItem.appendChild(statusBtn);
        taskItem.appendChild(taskText);
        taskItem.appendChild(deleteBtn);
        taskItem.appendChild(editBtn);
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
        
        editBtn.addEventListener('click', () => {
            const textHolder = taskText.textContent;
            taskItem.removeChild(statusBtn);
            taskItem.removeChild(taskText);
            taskItem.removeChild(deleteBtn);
            taskItem.removeChild(editBtn);
            const editInput = document.createElement('input');
            editInput.classList.add('form-control', 'edit-input');
            editInput.value = textHolder;
            const confirmBtn = document.createElement('button');
            confirmBtn.classList.add('confirm-btn');
            confirmBtn.innerHTML = '<i class="fa-solid fa-check fa-beat" style="color: #48d433;"></i>';
            const abortBtn = document.createElement('button');
            abortBtn.classList.add('abort-btn');
            abortBtn.innerHTML = '<i class="fa-solid fa-x fa-beat" style="color: #e32400;"></i>';
            taskItem.appendChild(editInput);
            taskItem.appendChild(confirmBtn);
            taskItem.appendChild(abortBtn);

            abortBtn.addEventListener('click', () => {
                changeToInputBtns(taskItem, editInput, confirmBtn, abortBtn, statusBtn, taskText, deleteBtn, editBtn);
            });

            confirmBtn.addEventListener('click', async () =>{
                if(editInput.value.trim() === textHolder.trim()){
                    changeToInputBtns(taskItem, editInput, confirmBtn, abortBtn, statusBtn, taskText, deleteBtn, editBtn);
                    return;
                }
                const taskId = task.id;
                try{
                    const response = await fetch(`http://localhost:8080/tasks/${taskId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            description: editInput.value,
                            is_completed: task.is_completed
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
                changeToInputBtns(taskItem, editInput, confirmBtn, abortBtn, statusBtn, taskText, deleteBtn, editBtn);
            });
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
