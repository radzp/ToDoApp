document.addEventListener("DOMContentLoaded", function () {
    
    const usernameField = document.getElementById('usernameField');
    const authoritiesField = document.getElementById('authoritiesField');
    const avatarImage = document.getElementsByClassName('avatarImage');
    let username = null;

    const darkMode = document.getElementById('darkTheme');
    const lightTheme = document.getElementById('lightTheme');
    
    if (localStorage.getItem('theme') === 'dark'){
        darkMode.checked = true;
    }else {
        lightTheme.checked = true;
    }
    
    async function getUsername() {
        try {
            const response = await fetch('http://localhost:8080/user/logged/username');
            if (!response.ok) {
                console.error('Wystąpił błąd podczas pobierania danych.');
                return;
            }
            username = await response.text();
            if (username == null) usernameField.innerText = "usernameNull"
            else usernameField.innerText = username;
        } catch (error) {
            console.error('Wystąpił błąd:', error);
        }

    }

    async function getUserAvatar() {
        try {
            const response = await fetch('http://localhost:8080/user/logged/avatar');
            if (!response.ok) {
                console.error('Wystąpił błąd podczas pobierania danych.');
                return;
            }
            const blob = await response.blob();
            for (let i = 0; i < avatarImage.length; i++) {
                avatarImage.item(i).src = URL.createObjectURL(blob);
            }
            //The URL.createObjectURL() static method creates a string containing a URL
            // representing the object given in the parameter

        } catch (error) {
            console.error('Wystąpił błąd:', error);
        }
    }

    async function getAuthorities() {
        try {
            const response = await fetch('http://localhost:8080/user/logged/authorities');
            if (!response.ok) {
                console.error('Wystąpił błąd podczas pobierania danych.');
                return;
            }
            const authorities = await response.json();
            authoritiesField.innerText = authorities[0].authority;
        } catch (error) {
            console.error('Wystąpił błąd:', error);
        }
    }

    getUsername();
    getAuthorities();
    getUserAvatar();


    

});


async function checkPassword() {

    const oldPassword = document.getElementById("oldPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const repeatPassword = document.getElementById("repeatPassword").value;

    const errorMessage = document.getElementById("passwordMatchError");
    const successfulMessage = document.getElementById("passwordSuccessful");

    errorMessage.innerText = "";
    successfulMessage.innerText = "";


    if (newPassword !== repeatPassword) {
        errorMessage.innerText = "New password doesn't match the repeated password!";
    } else {
        try {
            const response = await fetch(`http://localhost:8080/user/logged/changePassword?oldPassword=${oldPassword}&newPassword=${newPassword}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
            });

            if (!response.ok) {
                errorMessage.innerText = "Old password doesn't match!";
                console.error('Wystąpił błąd podczas pobierania danych.');
            } else {
                successfulMessage.innerText = "Your password changed successfully! We will redirect you to a logging page now.";
            }

        } catch (e) {
            console.error('Wystąpił błąd:', e);
        }
        //logout after change
        try{
            await fetch(`http://localhost:8080/logout`);
        } catch (e){
            console.error('Wystąpił błąd:', e);
        }

        await new Promise(r => setTimeout(r,4500));
        window.location.href="/login";

    }
}

async function checkUsername() {

    const oldUsername = document.getElementById("oldUsername").value;
    const newUsername = document.getElementById("newUsername").value;

    const errorMessage = document.getElementById("usernameMatchError");
    const successfulMessage = document.getElementById("usernameSuccessful");


    errorMessage.innerText = "";
    successfulMessage.innerText = "";


    try {
        const response = await fetch(`http://localhost:8080/user/logged/changeUsername?oldUsername=${oldUsername}&newUsername=${newUsername}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
        });

        if (!response.ok) {
            errorMessage.innerText = "Old username doesn't match!";
            console.error('Wystąpił błąd podczas pobierania danych.');
        } else {
            successfulMessage.innerText = "Your username changed successfully! We will redirect you to a logging page now.";
        }

    } catch (e) {
        console.error('Wystąpił błąd:', e);
    }


    //logout after change
    try{
        await fetch(`http://localhost:8080/logout`);
    } catch (e){
        console.error('Wystąpił błąd:', e);
    }

    await new Promise(r => setTimeout(r,4500));
    window.location.href="/login";
}