package com.example.stajgunlugum.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stajgunlugum.data.local.ActivityEntity
import com.example.stajgunlugum.data.repository.ActivityRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActivityViewModel(
    private val repository: ActivityRepository
) : ViewModel() {

    val allActivities: StateFlow<List<ActivityEntity>> =
        repository.allActivities.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun insertActivity(activity: ActivityEntity,
                       onActivityInserted: (Int) -> Unit
                       ) {
        viewModelScope.launch {
            val insertedId = repository.insertActivity(activity)
                .toInt()
            onActivityInserted(insertedId)
        }
    }
        fun deleteActivity(activity: ActivityEntity) {
            viewModelScope.launch {
                repository.deleteActivity(activity)
            }
        }
    fun updateActivity(activity: ActivityEntity) {
        viewModelScope.launch {
            repository.updateActivity(activity)
        }
    }


}
