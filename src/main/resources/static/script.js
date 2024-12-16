//// Обработчик для кнопки "Выйти"
//document.getElementById('logout-button')?.addEventListener('click', function() {
//    // Логика для выхода (например, удаление токена из локального хранилища)
//    localStorage.removeItem('user'); // Удаляем информацию о пользователе
//    window.location.href = 'login.html'; // Перенаправляем на страницу входа
//});
//
//// Пример функции для загрузки гроубоксов
//function loadGrowboxes() {
//    // Логика для загрузки гроубоксов
//}
//
//// Пример функции для регистрации пользователя
//document.getElementById('register-form')?.addEventListener('submit', function(event) {
//    event.preventDefault();
//    // Логика для регистрации пользователя
//});
//// Пример функции для загрузки гроубоксов
//function loadGrowboxes() {
//    // Логика для загрузки гроубоксов
//}





// login page
// Обработчик формы входа
document.getElementById('login-form')?.addEventListener('submit', function(event) {
    event.preventDefault(); // Отменяем стандартное поведение формы

    // Получаем данные из формы
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // Отправляем данные на сервер
    fetch('/api/v1/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Ошибка входа');
        }
        return response.json();
    })
    .then(user => {
        // Сохраняем информацию о пользователе в локальное хранилище
        localStorage.setItem('user', JSON.stringify(user));
        // Перенаправляем на страницу аккаунта
        window.location.href = 'account.html';
    })
    .catch(error => {
        alert('Ошибка: ' + error.message);
    });
});
