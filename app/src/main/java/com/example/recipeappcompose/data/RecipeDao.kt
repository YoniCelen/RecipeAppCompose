package com.example.recipeappcompose.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import com.example.recipeappcompose.data.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * from recipes WHERE id = :id")
    fun getRecipe(id: Int): Flow<Recipe>

    @Query("SELECT * from recipes ORDER BY name ASC")
    fun getAllRecipes(): Flow<List<Recipe>>
}