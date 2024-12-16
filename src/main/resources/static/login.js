document.getElementById('login-form')?.addEventListener('submit', function(event) {
    event.preventDefault(); // предотвращаем стандартное поведение формы

    // Получаем данные с формы
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // Создаем объект с данными для отправки на сервер
    const loginData = {
        email: email,
        password: password
    };

    // Отправляем POST запрос на сервер для авторизации
    fetch('/api/v1/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData) // сериализуем данные в формат JSON
    })
    .then(response => {
        if (response.ok) {
            // Если аутентификация успешна, получаем данные пользователя
            return response.json();
        } else {
            throw new Error('Неверный email или пароль');
        }
    })
    .then(user => {
        // Сохраняем информацию о пользователе в localStorage
        localStorage.setItem('user', JSON.stringify(user));

        // Перенаправляем на страницу аккаунта
        window.location.href = 'account.html';
    })
    .catch(error => {
        alert(error.message); // Показываем ошибку, если авторизация не удалась
    });
});
