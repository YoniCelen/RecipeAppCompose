package com.example.recipeappcompose.data

import com.example.recipeappcompose.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class OfflineRecipesRepository(private val recipeDao: RecipeDao) : RecipesRepository {
    override fun getAllRecipesStream(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    override fun getRecipeStream(id: Int): Flow<Recipe?> =recipeDao.getRecipe(id)

    override suspend fun insertRecipe(recipe: Recipe) = recipeDao.insert(recipe)

    override suspend fun deleteRecipe(recipe: Recipe) = recipeDao.delete(recipe)

    override suspend fun updateRecipe(recipe: Recipe) = recipeDao.update(recipe)
}