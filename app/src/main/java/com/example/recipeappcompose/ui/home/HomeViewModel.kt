package com.example.recipeappcompose.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappcompose.data.model.Recipe
import com.example.recipeappcompose.data.RecipesRepository
import com.example.recipeappcompose.data.network.RecipeApi
import com.example.recipeappcompose.data.network.RecipeApiService
import com.example.recipeappcompose.ui.recipe.RecipeDetailsUiState
import com.example.recipeappcompose.ui.recipe.RecipeDetailsViewModel
import com.example.recipeappcompose.ui.recipe.RecipeUiState
import com.example.recipeappcompose.ui.recipe.toRecipe
import com.example.recipeappcompose.ui.recipe.toRecipeDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all recipes in the Room database.
 */
class HomeViewModel(recipesRepository: RecipesRepository) : ViewModel() {

    /**
     * Holds home ui state. The list of recipes are retrieved from [RecipesRepository] and mapped to
     * [HomeUiState]
     */
    val homeUiState: StateFlow<HomeUiState> =
        recipesRepository.getAllRecipesStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    private val repository = recipesRepository

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    /**
     * Add random recipe to the [RecipesRepository]'s data source from the API.
     */
    suspend fun addRandomRecipeFromApi() {
        repository.insertRecipe(RecipeApi.retrofitService.getRandomRecipe().get("meals")!![0])
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val recipeList: List<Recipe> = listOf())
