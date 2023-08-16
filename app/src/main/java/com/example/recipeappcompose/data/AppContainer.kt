package com.example.recipeappcompose.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val recipesRepository: RecipesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineRecipesRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [RecipesRepository]
     */
    override val recipesRepository: RecipesRepository by lazy {
        OfflineRecipesRepository(RecipesDatabase.getDatabase(context).recipeDao())
    }
}