document.addEventListener('DOMContentLoaded', function () {
    loadAllGrowboxes();
});

// Функция для загрузки всех гроубоксов
function loadAllGrowboxes() {
    fetch('http://localhost:8080/api/v1/device') // Используем существующий эндпоинт
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
                    <ul>
                        ${requiredParts.map(part => `<li>${part}</li>`).join('')}
                    </ul>
                    <hr>
                `;
                growboxesDiv.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Ошибка при загрузке всех гроубоксов:', error);
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