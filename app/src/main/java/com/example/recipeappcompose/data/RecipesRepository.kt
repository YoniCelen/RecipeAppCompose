package com.example.recipeappcompose.data

import com.example.recipeappcompose.data.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Recipe] from a given data source.
 */
interface RecipesRepository {
    /**
     * Retrieve all the Recipes from the the given data source.
     */
    fun getAllRecipesStream(): Flow<List<Recipe>>

    /**
     * Retrieve a Recipe from the given data source that matches with the [id].
     */
    fun getRecipeStream(id: Int): Flow<Recipe?>

    /**
     * Insert recipe in the data source
     */
    suspend fun insertRecipe(recipe: Recipe)

    /**
     * Delete recipe from the data source
     */
    suspend fun deleteRecipe(recipe: Recipe)

    /**
     * Update recipe in the data source
     */
    suspend fun updateRecipe(recipe: Recipe)
}