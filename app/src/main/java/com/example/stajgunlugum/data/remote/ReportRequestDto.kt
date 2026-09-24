package com.example.stajgunlugum.data.remote

data class ReportRequestDto(
    val title: String,
    val description: String,
    val category: String,
    val technologies: String,
    val learnedConcepts: String,
    val durationMinutes: Int,
    val notes: String,
    val language: String,
    val length: String
)