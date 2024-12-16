document.addEventListener('DOMContentLoaded', function () {
    const user = JSON.parse(localStorage.getItem('user')); // Получаем пользователя из localStorage

    if (!user) {
        alert('Пользователь не авторизован!');
        window.location.href = 'login.html'; // Перенаправление на страницу входа
    } else {
        loadAllParts();

        // Проверка на корректность данных
        console.log(user); // Для дебага

        if (user.role === 'admin') {
            // Показываем кнопку добавления новой детали только для admin
            document.getElementById('add-part-button').style.display = 'block';

            // Добавляем обработчик для кнопки добавления новой детали
            document.getElementById('add-part-button').addEventListener('click', function() {
                // Показываем форму для добавления детали
                document.getElementById('add-part-form').style.display = 'block';
            });

            // Добавляем обработчик для отправки формы добавления новой детали
            document.getElementById('submit-part-button').addEventListener('click', function(event) {
                event.preventDefault(); // Предотвращаем стандартное поведение формы

                const newPart = {
                    id: 0,
                    name: document.getElementById('part-name').value,
                    price: document.getElementById('part-price').value
                };

                addNewPart(newPart); // Отправляем данные на сервер
            });
        }
    }
});

// Функция для загрузки всех запчастей
function loadAllParts() {
    fetch('http://localhost:8080/api/v1/detail') // Используем эндпоинт для получения всех запчастей
        .then(response => response.json())
        .then(parts => {
            const uniqueParts = getUniquePartsByName(parts);

            const partsDiv = document.getElementById('all-parts');
            partsDiv.innerHTML = ''; // Очищаем контейнер перед добавлением новых данных

            uniqueParts.forEach(part => {
                const card = document.createElement('div');
                card.classList.add('part-card');
                card.innerHTML = `
                    <h3>Деталь: ${part.name}</h3>
                    <p><strong>Цена:</strong> ${part.price} руб.</p>
                    <hr>
                `;
                partsDiv.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Ошибка при загрузке запчастей:', error);
        });
}

// Функция для получения уникальных запчастей по имени
function getUniquePartsByName(parts) {
    const uniqueNames = new Set();
    return parts.filter(part => {
        if (!uniqueNames.has(part.name)) {
            uniqueNames.add(part.name);
            return true;
        }
        return false;
    });
}

// Функция для добавления новой детали
function addNewPart(newPart) {
    fetch('http://localhost:8080/api/v1/detail/save_detail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newPart)
    })
    .then(response => response.json())
    .then(part => {
        alert('Деталь успешно добавлена!');
        loadAllParts(); // Перезагружаем список запчастей
        document.getElementById('add-part-form').style.display = 'none'; // Скрываем форму
    })
    .catch(error => {
        console.error('Ошибка при добавлении новой детали:', error);
        alert('Не удалось добавить деталь.');
    });
}
