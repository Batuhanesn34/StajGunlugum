package com.example.stajgunlugum.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stajgunlugum.data.repository.ActivityRepository

class ActivityViewModelFactory(
    private val repository: ActivityRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(ActivityViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return ActivityViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Bilinmeyen ViewModel sınıfı: ${modelClass.name}"
        )
    }
}