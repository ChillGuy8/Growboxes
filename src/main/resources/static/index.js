document.addEventListener('DOMContentLoaded', function () {
    const user = JSON.parse(localStorage.getItem('user')); // Получаем пользователя из localStorage

    if (!user) {
        alert('Пользователь не авторизован!');
        window.location.href = 'login.html'; // Перенаправление на страницу входа
    } else {
        loadAllGrowboxes();
        if (user.role === 'admin') {
            // Показываем кнопку добавления нового гроубокса только для admin
            document.getElementById('add-growbox-button').style.display = 'block';

            // Добавляем обработчик для кнопки добавления нового гроубокса
            document.getElementById('add-growbox-button').addEventListener('click', function() {
                // Показываем форму для добавления нового гроубокса
                document.getElementById('add-growbox-form').style.display = 'block';
            });

            // Добавляем обработчик для отправки формы добавления нового гроубокса
            document.getElementById('new-growbox-form').addEventListener('submit', function(event) {
                event.preventDefault(); // Предотвращаем стандартное поведение формы

                const newGrowbox = {
                    name: document.getElementById('name').value,
                    size: document.getElementById('size').value,
                    price: document.getElementById('price').value,
                    mode: document.getElementById('mode').value,
                    userId: user.id // Используем ID пользователя из localStorage
                };

                addNewGrowbox(newGrowbox); // Отправляем данные на сервер
            });
        }
    }
});

// Функция для загрузки всех гроубоксов
function loadAllGrowboxes() {
    fetch('/api/v1/device') // Используем существующий эндпоинт
        .then(response => response.json())
        .then(devices => {
            const uniqueDevices = getUniqueDevicesByName(devices);

            const growboxesDiv = document.getElementById('all-growboxes');
            growboxesDiv.innerHTML = ''; // Очищаем контейнер перед добавлением новых данных

            uniqueDevices.forEach(device => {
                const requiredParts = getRequiredParts(device.name); // Получаем список нужных деталей для каждого устройства

                const card = document.createElement('div');
                card.classList.add('growbox-card');
                card.innerHTML = `
                    <h3>Гроубокс: ${device.name}</h3>
                    <p><strong>Размер:</strong> ${device.size}</p>
                    <p><strong>Цена:</strong> ${device.price} руб.</p>
                    <p><strong>Режим:</strong> ${device.mode}</p>
                    <p><strong>Детали:</strong></p>
                    <ul id="device-details-${device.id}">
                        <!-- Детали будут загружаться здесь -->
                    </ul>
                    <hr>
                    <button class="add-part-button" data-device-id="${device.id}" style="display:none;">Добавить запчасть</button>
                    <div class="add-part-form" style="display:none;">
                                                  <label for="part-id-${device.id}">ID детали:</label>
                                                  <input type="number" id="part-id-${device.id}" name="part-id-${device.id}" required><br><br>
                                                  <label for="part-quantity-${device.id}">Количество:</label>
                                                  <input type="number" id="part-quantity-${device.id}" name="part-quantity-${device.id}" required><br><br>
                                                  <button class="submit-part-button" data-device-id="${device.id}">Добавить деталь</button>
                                              </div>
                `;
                growboxesDiv.appendChild(card);

                // Загружаем детали для текущего гроубокса
                loadDeviceDetails(device.id);

                const user = JSON.parse(localStorage.getItem('user'));
                // Показываем кнопку "Добавить запчасть" для админа
                if (user.role === 'admin') {
                    const addPartButton = card.querySelector('.add-part-button');
                    addPartButton.style.display = 'block';

                    // Открываем форму для добавления детали
                    addPartButton.addEventListener('click', function() {
                        const form = card.querySelector('.add-part-form');
                        form.style.display = 'block';
                    });

                    // Обработчик для добавления детали
                    const submitPartButton = card.querySelector('.submit-part-button');
                    submitPartButton.addEventListener('click', function() {
                        const partId = card.querySelector(`#part-id-${device.id}`).value;
                        const quantity = card.querySelector(`#part-quantity-${device.id}`).value;

                        addPartToGrowbox(device.id, partId, quantity);
                    });
                }
            });
        })
        .catch(error => {
            console.error('Ошибка при загрузке всех гроубоксов:', error);
        });
}

// Функция для загрузки деталей для каждого гроубокса
function loadDeviceDetails(deviceId) {
    fetch(`/api/v1/device_detail/by_device/${deviceId}`)
        .then(response => response.json())
        .then(details => {
            const detailsList = document.getElementById(`device-details-${deviceId}`);
            detailsList.innerHTML = ''; // Очищаем список перед добавлением новых данных

            details.forEach(device_detail => {
                const listItem = document.createElement('li');
                listItem.textContent = `Деталь: ${device_detail.detail.name}, Количество: ${device_detail.count}`;
                detailsList.appendChild(listItem);
            });
        })
        .catch(error => {
            console.error('Ошибка при загрузке деталей для гроубокса:', error);
        });
}

// Функция для получения уникальных устройств по имени
function getUniqueDevicesByName(devices) {
    const uniqueNames = new Set();
    return devices.filter(device => {
        if (!uniqueNames.has(device.name)) {
            uniqueNames.add(device.name);
            return true;
        }
        return false;
    });
}

// Функция для получения нужных деталей для каждого гроубокса
function getRequiredParts(growboxName) {
    if (growboxName === "гроубокс-1") {
        return [
            '1 микроконтроллер',
            '1 датчик температуры',
            '1 датчик влажности'
        ];
    } else if (growboxName === "гроубокс-2") {
        return [
            '1 микроконтроллер',
            '2 датчика температуры',
            '2 датчика влажности'
        ];
    } else {
        return []; // Если название не подходит, то возвращаем пустой список
    }
}

// Функция для добавления нового гроубокса
function addNewGrowbox(newGrowbox) {
    fetch('/api/v1/device/save_device', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newGrowbox)
    })
    .then(response => response.json())
    .then(device => {
        alert('Гроубокс успешно добавлен!');
        loadAllGrowboxes(); // Перезагружаем список гроубоксов
        document.getElementById('add-growbox-form').style.display = 'none'; // Скрываем форму
    })
    .catch(error => {
        console.error('Ошибка при добавлении нового гроубокса:', error);
        alert('Не удалось добавить новый гроубокс.');
    });
}

// Функция для добавления детали к гроубоксу
function addPartToGrowbox(device_id, detail_id, count) {
    const partData = {
        id: 0,
        deviceId: device_id,
        detailId: parseInt(detail_id),
        count: parseInt(count)
    };

    fetch('/api/v1/device_detail/save_device_detail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(partData)
    })
    .then(response => response.json())
    .then(result => {
        alert('Запчасть успешно добавлена!');
        loadAllGrowboxes(); // Перезагружаем список гроубоксов
    })
    .catch(error => {
        console.error('Ошибка при добавлении запчасти:', error);
        alert('Не удалось добавить запчасть.');
    });
}
