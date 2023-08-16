package com.example.recipeappcompose.ui.recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.recipeappcompose.data.RecipesRepository
import com.example.recipeappcompose.data.model.Recipe

/**
 * ViewModel to validate and insert recipes in the Room database.
 */
class RecipeEntryViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    /**
     * Holds current recipe ui state
     */
    var recipeUiState by mutableStateOf(RecipeUiState())
        private set

    /**
     * Updates the [recipeUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(recipeDetails: RecipeDetails) {
        recipeUiState =
            RecipeUiState(recipeDetails = recipeDetails, isEntryValid = validateInput(recipeDetails))
    }

    suspend fun saveRecipe() {
        if (validateInput()) {
            recipesRepository.insertRecipe(recipeUiState.recipeDetails.toRecipe())
        }
    }

    private fun validateInput(uiState: RecipeDetails = recipeUiState.recipeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && ingredients.isNotBlank() && steps.isNotBlank() && author.isNotBlank()
        }
    }
}

/**
 * Represents Ui State for an Recipe.
 */
data class RecipeUiState(
    val recipeDetails: RecipeDetails = RecipeDetails(),
    val isEntryValid: Boolean = false
)

data class RecipeDetails(
    val id: Int = 0,
    val name: String = "",
    val ingredients: String = "",
    val steps: String = "",
    val author: String = ""
)

/**
 * Extension function to convert [RecipeDetails] to [Recipe].
 * No real functionality right now, but might be a nice to have when I bump into an issue
 */
fun RecipeDetails.toRecipe(): Recipe = Recipe(
    id = id,
    name = name,
    ingredients = ingredients,
    steps = steps,
    author = author
)

/**
 * Extension function to convert [Recipe] to [RecipeUiState]
 */
fun Recipe.toRecipeUiState(isEntryValid: Boolean = false): RecipeUiState = RecipeUiState(
    recipeDetails = this.toRecipeDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Recipe] to [RecipeDetails]
 * Same as above, not needed yet, but may be a nice to have
 */
fun Recipe.toRecipeDetails(): RecipeDetails = RecipeDetails(
    id = id,
    name = name,
    ingredients = ingredients,
    steps = steps,
    author = author
)