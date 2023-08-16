package com.example.recipeappcompose

import android.app.Application
import com.example.recipeappcompose.data.AppContainer
import com.example.recipeappcompose.data.AppDataContainer

class RecipeApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
