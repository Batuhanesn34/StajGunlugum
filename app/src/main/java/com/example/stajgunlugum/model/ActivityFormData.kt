package com.example.stajgunlugum.model

data class ActivityFormData(
    val id : Int = 0,
    val title : String,
    val description : String,
    val category : String,
    val technologies : String,
    val learnedConcepts: String,
    val durationMinutes: Int,
    val notes: String,
    val date: String
)
