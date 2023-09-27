document.addEventListener("DOMContentLoaded", function () {
    let body = document.getElementsByTagName('BODY')[0];
    const darkMode = document.getElementById('darkTheme');
    const lightTheme = document.getElementById('lightTheme');


    function changeTheme(){
        if(localStorage.getItem('theme') === 'dark') {
            body.classList.add('dark');
        } else {
            body.classList.remove('dark');
        }
    }
    
    
    changeTheme();
    
    darkMode.addEventListener( 'click', function() {
        localStorage.setItem('theme', 'dark');
        changeTheme();
    });

    
    lightTheme.addEventListener('click', function () {
        localStorage.setItem('theme', 'light');
        changeTheme();
    });
});


