package com.example.stajgunlugum.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao //database e erişim sağlayan yapı

interface ActivityDao{

    @Insert
    suspend fun insertActivity(activity: ActivityEntity): Long //id döndürür

    @Update
    suspend fun updateActivity(activity: ActivityEntity)

    @Delete
    suspend fun deleteActivity(activity: ActivityEntity)

    @Query("Select * From activities Order By id DESC")
    fun getAllActivities(): Flow<List<ActivityEntity>>

    @Query("Select * From activities Where id = :activityId")
    suspend fun getActivityById(activityId: Int): ActivityEntity?

}






