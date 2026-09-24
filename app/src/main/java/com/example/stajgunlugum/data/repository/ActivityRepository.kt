package com.example.stajgunlugum.data.repository

import com.example.stajgunlugum.data.local.ActivityDao
import com.example.stajgunlugum.data.local.ActivityEntity
import kotlinx.coroutines.flow.Flow

class ActivityRepository(
    private val activityDao: ActivityDao
) {

    val allActivities: Flow<List<ActivityEntity>> =
        activityDao.getAllActivities()

    suspend fun insertActivity(activity: ActivityEntity): Long {
        return activityDao.insertActivity(activity)
    }

    suspend fun updateActivity(activity: ActivityEntity) {
        activityDao.updateActivity(activity)
    }

    suspend fun deleteActivity(activity: ActivityEntity) {
        activityDao.deleteActivity(activity)
    }

    suspend fun getActivityById(activityId: Int): ActivityEntity? {
        return activityDao.getActivityById(activityId)
    }
}