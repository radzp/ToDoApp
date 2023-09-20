document.addEventListener("DOMContentLoaded", function () {

    const usernameField = document.getElementById('usernameField');
    const authoritiesField = document.getElementById('authoritiesField');
    const avatarImage = document.getElementsByClassName('avatarImage');
    let username = null;

    async function getUsername(){
        try{
            const response = await fetch('http://localhost:8080/user/logged/username');
            if (!response.ok) {
                console.error('Wystąpił błąd podczas pobierania danych.');
                return;
            }
            username = await response.text();
            if (username == null) usernameField.innerText = "usernameNull"
            else usernameField.innerText = username;
        }catch (error) {
            console.error('Wystąpił błąd:', error);
        }

    }
    async function getUserAvatar(){
        try{
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

        }catch (error) {
            console.error('Wystąpił błąd:', error);
        }
    }
    async function getAuthorities(){
        try{
            const response = await fetch('http://localhost:8080/user/logged/authorities');
            if (!response.ok) {
                console.error('Wystąpił błąd podczas pobierania danych.');
                return;
            }
            const authorities = await response.json();
            authoritiesField.innerText = authorities[0].authority;
        }catch (error) {
            console.error('Wystąpił błąd:', error);
        }
    }

    getUsername();
    getAuthorities();
    getUserAvatar();


});