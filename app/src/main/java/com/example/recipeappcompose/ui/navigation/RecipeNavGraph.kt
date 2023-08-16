package com.example.recipeappcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipeappcompose.ui.home.HomeDestination
import com.example.recipeappcompose.ui.home.HomeScreen
import com.example.recipeappcompose.ui.recipe.RecipeDetailsDestination
import com.example.recipeappcompose.ui.recipe.RecipeDetailsScreen
import com.example.recipeappcompose.ui.recipe.RecipeEditDestination
import com.example.recipeappcompose.ui.recipe.RecipeEditScreen
import com.example.recipeappcompose.ui.recipe.RecipeEntryDestination
import com.example.recipeappcompose.ui.recipe.RecipeEntryScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun RecipeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToRecipeEntry = { navController.navigate(RecipeEntryDestination.route) },
                navigateToRecipeUpdate = {
                    navController.navigate("${RecipeDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = RecipeEntryDestination.route) {
            RecipeEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = RecipeDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(RecipeDetailsDestination.recipeIdArg) {
                type = NavType.IntType
            })
        ) {
            RecipeDetailsScreen(
                navigateToEditRecipe = { navController.navigate("${RecipeEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = RecipeEditDestination.routeWithArgs,
            arguments = listOf(navArgument(RecipeEditDestination.recipeIdArg) {
                type = NavType.IntType
            })
        ) {
            RecipeEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
