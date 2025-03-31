// Переключатель темы
document.getElementById('theme-toggle').addEventListener('click', function() {
    document.body.classList.toggle('dark-theme');
});

// Поиск по контенту
document.getElementById('search').addEventListener('input', function() {
    let searchText = this.value.toLowerCase();
    let elements = document.querySelectorAll('.maintext h2, .maintext p, .maintext button');

    elements.forEach(el => {
        if (el.textContent.toLowerCase().includes(searchText)) {
            el.style.display = 'block';
        } else {
            el.style.display = 'none';
        }
    });
});

// Функция для скрытия и показа подробностей
function toggleSection(sectionId) {
    var section = document.getElementById(sectionId);
    section.style.display = section.style.display === "block" ? "none" : "block";
}
