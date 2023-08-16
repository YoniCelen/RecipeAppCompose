package com.example.recipeappcompose.ui.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappcompose.data.RecipesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve, update and delete an recipe from the [RecipesRepository]'s data source.
 */
class RecipeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipesRepository: RecipesRepository,
) : ViewModel() {

    private val recipeId: Int = checkNotNull(savedStateHandle[RecipeDetailsDestination.recipeIdArg])

    /**
     * Holds the recipe details ui state. The data is retrieved from [RecipesRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<RecipeDetailsUiState> =
        recipesRepository.getRecipeStream(recipeId)
            .filterNotNull()
            .map {
                RecipeDetailsUiState(recipeDetails = it.toRecipeDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RecipeDetailsUiState()
            )

    /**
     * Deletes the recipe from the [RecipesRepository]'s data source.
     */
    suspend fun deleteRecipe() {
        recipesRepository.deleteRecipe(uiState.value.recipeDetails.toRecipe())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for RecipeDetailsScreen
 */
data class RecipeDetailsUiState(
    val recipeDetails: RecipeDetails = RecipeDetails()
)
