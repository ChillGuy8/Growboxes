// Получаем информацию о пользователе из localStorage
document.addEventListener('DOMContentLoaded', function () {
    const user = JSON.parse(localStorage.getItem('user')); // Получаем полную сущность пользователя

    if (!user) {
        alert('Пользователь не авторизован!');
        window.location.href = 'login.html'; // Перенаправление на страницу входа
    } else {
        loadUserInfo(user);
        loadGrowboxes(user.id);
    }
});

// Функция для загрузки информации о пользователе
function loadUserInfo(user) {
    const userInfoDiv = document.getElementById('user-info');
    userInfoDiv.innerHTML = `
        <p><strong>Имя:</strong> ${user.username}</p>
        <p><strong>Email:</strong> ${user.email}</p>
        <p><strong>Роль:</strong> ${user.role}</p>
    `;
}

// Функция для загрузки гроубоксов пользователя
function loadGrowboxes(userId) {
    fetch(`http://localhost:8080/api/v1/device/devices/${userId}`)
        .then(response => response.json())
        .then(devices => {
            const growboxesDiv = document.getElementById('user-growboxes');
            growboxesDiv.innerHTML = ''; // Очищаем контейнер перед добавлением новых данных

            devices.forEach(device => {
                const card = document.createElement('div');
                card.classList.add('growbox-card');
                card.innerHTML = `
                    <h3>Гроубокс: ${device.name}</h3>
                    <p><strong>Размер:</strong> ${device.size}</p>
                    <p><strong>Рег. код:</strong> ${device.regcode}</p>
                    <p><strong>Режим:</strong> ${device.mode}</p>
                    <button class="take-measurements-button" data-device-id="${device.id}">Снять измерения</button>
                    <button class="view-measurements-button" data-device-id="${device.id}">Посмотреть измерения</button>
                    <button class="view-parts-button" data-device-id="${device.id}">Посмотреть запчасти</button>
                    <hr>
                `;
                growboxesDiv.appendChild(card);
            });

            // Добавляем обработчики событий для кнопок "Снять измерения"
            document.querySelectorAll('.take-measurements-button').forEach(button => {
                button.addEventListener('click', function () {
                    const deviceId = button.getAttribute('data-device-id');
                    takeMeasurements(deviceId);
                });
            });

            // Добавляем обработчики событий для кнопок "Посмотреть измерения"
            document.querySelectorAll('.view-measurements-button').forEach(button => {
                button.addEventListener('click', function () {
                    const deviceId = button.getAttribute('data-device-id');
                    viewMeasurements(deviceId);
                });
            });

            // Добавляем обработчики событий для кнопок "Посмотреть запчасти"
            document.querySelectorAll('.view-parts-button').forEach(button => {
                button.addEventListener('click', function () {
                    const deviceId = button.getAttribute('data-device-id');
                    viewParts(deviceId);
                });
            });
        })
        .catch(error => {
            console.error('Ошибка при загрузке гроубоксов:', error);
        });
}

// Функция для снятия измерений с устройства
function takeMeasurements(deviceId) {
    fetch(`http://localhost:8080/api/v1/indications/devices/take-measurements/${deviceId}`, {
        method: 'POST',
    })
        .then(response => {
            if (response.ok) {
                alert(`Измерения для устройства ${deviceId} успешно сняты.`);
            } else {
                alert(`Не удалось снять измерения для устройства ${deviceId}.`);
            }
        })
        .catch(error => {
            console.error('Ошибка при снятии измерений:', error);
            alert('Произошла ошибка при снятии измерений.');
        });
}

// Функция для отображения измерений устройства
function viewMeasurements(deviceId) {
    fetch(`http://localhost:8080/api/v1/indications/findAllByDevice_indications/${deviceId}`)
        .then(response => response.json())
        .then(indications => {
            alert(`Измерения для устройства ${deviceId}:\n${indications.map(ind => `${ind.valueName}: ${ind.value}`).join('\n')}`);
        })
        .catch(error => {
            console.error('Ошибка при загрузке измерений:', error);
        });
}

// Функция для отображения запчастей устройства
function viewParts(deviceId) {
    fetch(`http://localhost:8080/api/v1/device_detail/by_device/${deviceId}`)
        .then(response => response.json())
        .then(parts => {
            alert(`Запчасти для устройства ${deviceId}:\n${parts.map(part => `${part.detail.name} x${part.count}`).join('\n')}`);
        })
        .catch(error => {
            console.error('Ошибка при загрузке запчастей:', error);
        });
}

// Обработчик для кнопки "Выйти"
document.getElementById('logout-button')?.addEventListener('click', function () {
    localStorage.removeItem('userId'); // Удаляем информацию о пользователе
    window.location.href = 'login.html'; // Перенаправляем на страницу входа
});


document.addEventListener('DOMContentLoaded', function () {
    const user = JSON.parse(localStorage.getItem('user')); // Получаем полную сущность пользователя

    if (!user) {
        alert('Пользователь не авторизован!');
        window.location.href = 'login.html'; // Перенаправление на страницу входа
    } else {
        loadUserInfo(user);
        loadGrowboxes(user.id);
    }

    // Добавляем обработчик для кнопки "Добавить устройство"
    document.getElementById('add-device-button').addEventListener('click', function () {
        const regcode = prompt('Введите регистрационный код устройства:');
        if (regcode) {
            addDevice(user.id, regcode);
        }
    });
});

// Функция для добавления устройства
function addDevice(userId, regcode) {
    fetch(`http://localhost:8080/api/v1/device/save_random_device/${userId}/${regcode}`, {
        method: 'POST',
    })
        .then(response => response.json())
        .then(device => {
            alert(`Устройство успешно добавлено:\nНазвание: ${device.name}\nРазмер: ${device.size}\nРег. код: ${device.regcode}`);
            loadGrowboxes(userId); // Обновляем список гроубоксов
        })
        .catch(error => {
            console.error('Ошибка при добавлении устройства:', error);
            alert('Произошла ошибка при добавлении устройства.');
        });
}