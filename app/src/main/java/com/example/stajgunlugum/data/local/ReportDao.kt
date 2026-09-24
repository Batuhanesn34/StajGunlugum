package com.example.stajgunlugum.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {

    @Insert
    suspend fun insertReport(
        report: ReportEntity
    ): Long


    @Delete
    suspend fun deleteReport(
        report: ReportEntity
    )


    @Query(
        "SELECT * FROM reports ORDER BY id DESC"
    )
    fun getAllReports(): Flow<List<ReportEntity>>


    @Query(
        "SELECT * FROM reports WHERE id = :reportId"
    )
    suspend fun getReportById(
        reportId: Int
    ): ReportEntity?


    @Query(
        "SELECT * FROM reports WHERE activityId = :activityId ORDER BY id DESC"
    )
    fun getReportsByActivityId(
        activityId: Int
    ): Flow<List<ReportEntity>>
}