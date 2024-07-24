let ingredientIndex = 1; // Inicijalni indeks za nove namirnice

function addIngredient() {
    const container = document.getElementById('ingredients-container');
    const newIngredient = document.createElement('div');
    newIngredient.className = 'form-group ingredient-item';
    newIngredient.innerHTML = `
            <label for="grocery-${ingredientIndex}">Namirnica:</label>
            <select id="grocery-${ingredientIndex}" name="ingredients[${ingredientIndex}].grocery" class="form-control" required>
                <option th:each="grocery : ${groceries}" th:value="${grocery.id}" th:text="${grocery.name}"></option>
            </select>
            <label for="quantity-${ingredientIndex}">Količina:</label>
            <input type="number" id="quantity-${ingredientIndex}" name="ingredients[${ingredientIndex}].quantity" class="form-control" required>
            <button type="button" class="btn btn-danger mt-2 remove-ingredient" onclick="removeIngredient(this)">Ukloni</button>
        `;
    container.appendChild(newIngredient);
    ingredientIndex++;
}

function removeIngredient(button) {
    const container = document.getElementById('ingredients-container');
    container.removeChild(button.parentElement);
}