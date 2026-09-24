package com.example.stajgunlugum.data.repository

import com.example.stajgunlugum.data.local.ReportDao
import com.example.stajgunlugum.data.local.ReportEntity
import kotlinx.coroutines.flow.Flow

class ReportRepository(
    private val reportDao: ReportDao
) {

    val allReports: Flow<List<ReportEntity>> =
        reportDao.getAllReports()

    suspend fun insertReport(report: ReportEntity): Long {
        return reportDao.insertReport(report)
    }

    suspend fun deleteReport(report: ReportEntity) {
        reportDao.deleteReport(report)
    }

    suspend fun getReportById(reportId: Int): ReportEntity? {
        return reportDao.getReportById(reportId)
    }

    fun getReportsByActivityId(
        activityId: Int
    ): Flow<List<ReportEntity>> {
        return reportDao.getReportsByActivityId(activityId)
    }
}