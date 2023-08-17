package com.example.recipeappcompose.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerialName("strMeal")
    val name: String,
    @SerialName("strIngredient1")
    val ingredients: String,
    @SerialName("strInstructions")
    val steps: String,
    @SerialName("strSource")
    @ColumnInfo(defaultValue = "unknown")
    val author: String
)