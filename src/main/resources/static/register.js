document.getElementById('register-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;

    if (!name || !email || !password) {
        alert('Пожалуйста, заполните все поля');
        return;
    }

    const userData = {
        username: name,
        email: email,
        password: password,
        role: role
    };

    fetch('/api/v1/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
    })
    .then(response => {
        if (response.ok) {
            alert('Пользователь успешно зарегистрирован');
            window.location.href = 'login.html'; // Перенаправляем на страницу входа
        } else {
            response.json().then(data => {
                alert(data.message || 'Произошла ошибка при регистрации');
            });
        }
    })
    .catch(error => {
        console.error('Ошибка при отправке данных:', error);
        alert('Ошибка при отправке данных');
    });
});
