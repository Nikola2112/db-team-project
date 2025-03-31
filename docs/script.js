// 🌙 Перемикач теми
document.getElementById('theme-toggle').addEventListener('click', function() {
    document.body.classList.toggle('dark-theme');
});

// 🔍 Пошук (простий варіант)
document.getElementById('search').addEventListener('input', function() {
    let searchText = this.value.toLowerCase();
    let elements = document.querySelectorAll('.container h1, .container h3, .btn');

    elements.forEach(el => {
        if (el.textContent.toLowerCase().includes(searchText)) {
            el.style.display = 'block';
        } else {
            el.style.display = 'none';
        }
    });
});
