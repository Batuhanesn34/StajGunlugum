package com.example.stajgunlugum.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)

    val id : Int = 0,

    val title : String,

    val description : String,

    val category : String,

    val technologies : String,

    val learnedConcepts : String,

    val durationMinutes : Int,

    val notes : String,

    val date : String

)

