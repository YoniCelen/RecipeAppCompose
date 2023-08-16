package com.example.recipeappcompose.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.recipeappcompose.RecipeApplication
import com.example.recipeappcompose.ui.home.HomeViewModel
import com.example.recipeappcompose.ui.recipe.RecipeDetailsViewModel
import com.example.recipeappcompose.ui.recipe.RecipeEditViewModel
import com.example.recipeappcompose.ui.recipe.RecipeEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Recipe app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for RecipeEditViewModel
        initializer {
            RecipeEditViewModel(
                this.createSavedStateHandle(),
                recipeApplication().container.recipesRepository
            )
        }
        // Initializer for RecipeEntryViewModel
        initializer {
            RecipeEntryViewModel(recipeApplication().container.recipesRepository)
        }

        // Initializer for RecipeDetailsViewModel
        initializer {
            RecipeDetailsViewModel(
                this.createSavedStateHandle(),
                recipeApplication().container.recipesRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(recipeApplication().container.recipesRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [RecipeApplication].
 */
fun CreationExtras.recipeApplication(): RecipeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as RecipeApplication)
