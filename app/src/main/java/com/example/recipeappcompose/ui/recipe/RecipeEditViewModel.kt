package com.example.recipeappcompose.ui.recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappcompose.data.RecipesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an recipe from the [RecipesRepository]'s data source.
 */
class RecipeEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    /**
     * Holds current recipe ui state
     */
    var recipeUiState by mutableStateOf(RecipeUiState())
        private set

    private val recipeId: Int = checkNotNull(savedStateHandle[RecipeEditDestination.recipeIdArg])

    init {
        viewModelScope.launch {
            recipeUiState = recipesRepository.getRecipeStream(recipeId)
                .filterNotNull()
                .first()
                .toRecipeUiState(true)
        }
    }

    /**
     * Update the recipe in the [RecipesRepository]'s data source
     */
    suspend fun updateRecipe() {
        if (validateInput(recipeUiState.recipeDetails)) {
            recipesRepository.updateRecipe(recipeUiState.recipeDetails.toRecipe())
        }
    }

    /**
     * Updates the [recipeUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(recipeDetails: RecipeDetails) {
        recipeUiState =
            RecipeUiState(recipeDetails = recipeDetails, isEntryValid = validateInput(recipeDetails))
    }

    private fun validateInput(uiState: RecipeDetails = recipeUiState.recipeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && ingredients.isNotBlank() && steps.isNotBlank() && author.isNotBlank()
        }
    }
}
