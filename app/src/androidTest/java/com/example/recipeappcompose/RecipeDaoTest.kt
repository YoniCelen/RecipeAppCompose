package com.example.recipeappcompose

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipeappcompose.data.RecipesDatabase
import com.example.recipeappcompose.data.model.Recipe
import com.example.recipeappcompose.data.RecipeDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RecipeDaoTest {

    private lateinit var recipeDao: RecipeDao
    private lateinit var recipesDatabase: RecipesDatabase
    private val recipe1 = Recipe(1, "Test", "Test", "Test", "Test")
    private val recipe2 = Recipe(2, "Test2", "Test2", "Test2", "Test2")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        recipesDatabase = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        recipeDao = recipesDatabase.recipeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        recipesDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsRecipeIntoDB() = runBlocking {
        addOneRecipeToDb()
        val allRecipes = recipeDao.getAllRecipes().first()
        assertEquals(allRecipes[0], recipe1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllRecipes_returnsAllRecipesFromDB() = runBlocking {
        addTwoRecipesToDb()
        val allRecipes = recipeDao.getAllRecipes().first()
        assertEquals(allRecipes[0], recipe1)
        assertEquals(allRecipes[1], recipe2)
    }


    @Test
    @Throws(Exception::class)
    fun daoGetRecipe_returnsRecipeFromDB() = runBlocking {
        addOneRecipeToDb()
        val recipe = recipeDao.getRecipe(1)
        assertEquals(recipe.first(), recipe1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteRecipes_deletesAllRecipesFromDB() = runBlocking {
        addTwoRecipesToDb()
        recipeDao.delete(recipe1)
        recipeDao.delete(recipe2)
        val allRecipes = recipeDao.getAllRecipes().first()
        assertTrue(allRecipes.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateRecipes_updatesRecipesInDB() = runBlocking {
        addTwoRecipesToDb()
        recipeDao.update(Recipe(1, "Test", "Test", "Test", "Test"))
        recipeDao.update(Recipe(2, "Test2", "Test2", "Test2", "Test2"))

        val allRecipes = recipeDao.getAllRecipes().first()
        assertEquals(allRecipes[0], Recipe(1, "Test", "Test", "Test", "Test"))
        assertEquals(allRecipes[1], Recipe(2, "Test2", "Test2", "Test2", "Test2"))
    }

    private suspend fun addOneRecipeToDb() {
        recipeDao.insert(recipe1)
    }

    private suspend fun addTwoRecipesToDb() {
        recipeDao.insert(recipe1)
        recipeDao.insert(recipe2)
    }
}