document.addEventListener('DOMContentLoaded', () => {
    const wrapper = document.querySelector('.wrapper');
    const loginLink = document.querySelector('.login-link');
    const registerLink = document.querySelector('.register-link');
    const inputFields = document.querySelectorAll('.input-field');

    registerLink.addEventListener('click', () => {
        wrapper.classList.add('active');
    });

    loginLink.addEventListener('click', () => {
        wrapper.classList.remove('active');
    });


    inputFields.forEach(inputField => {
        inputField.addEventListener('input', () => {
            if (inputField.value.trim() !== '') {
                inputField.classList.add('filled');
            } else {
                inputField.classList.remove('filled');
            }
        });

        inputField.addEventListener('focus', () => {
            inputField.classList.add('active');
        });

        inputField.addEventListener('blur', () => {
            inputField.classList.remove('active');
        });
    });

});
