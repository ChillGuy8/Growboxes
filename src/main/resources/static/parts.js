document.addEventListener('DOMContentLoaded', function () {
    loadAllParts();
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
