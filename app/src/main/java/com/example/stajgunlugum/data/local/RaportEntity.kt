package com.example.stajgunlugum.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val activityId: Int,

    val title: String,

    val reportText: String,

    val language: String,

    val createdAt: String
)