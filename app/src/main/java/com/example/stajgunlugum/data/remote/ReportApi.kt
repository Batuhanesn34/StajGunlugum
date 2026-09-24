package com.example.stajgunlugum.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface ReportApi {

    @POST("api/report/generate")
    suspend fun generateReport(
        @Body request: ReportRequestDto
    ): ReportResponseDto
}