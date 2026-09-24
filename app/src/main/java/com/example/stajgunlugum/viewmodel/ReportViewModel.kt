package com.example.stajgunlugum.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stajgunlugum.data.local.ReportEntity
import com.example.stajgunlugum.data.repository.ReportRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReportViewModel(
    private val repository: ReportRepository
) : ViewModel() {

    val allReports: StateFlow<List<ReportEntity>> =
        repository.allReports.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun insertReport(
        report: ReportEntity,
        onReportInserted: (Int) -> Unit = {}
    ) {

        viewModelScope.launch {

            val insertedId =
                repository.insertReport(report).toInt()

            onReportInserted(insertedId)
        }
    }


    fun deleteReport(
        report: ReportEntity
    ) {

        viewModelScope.launch {

            repository.deleteReport(report)
        }
    }


    suspend fun getReportById(
        reportId: Int
    ): ReportEntity? {

        return repository.getReportById(reportId)
    }
}