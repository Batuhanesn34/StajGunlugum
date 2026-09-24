package com.example.stajgunlugum.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.stajgunlugum.data.local.ActivityEntity
import com.example.stajgunlugum.data.local.AppDatabase
import com.example.stajgunlugum.data.local.ReportEntity

import com.example.stajgunlugum.data.repository.ActivityRepository
import com.example.stajgunlugum.data.repository.ReportRepository

import com.example.stajgunlugum.model.ActivityFormData

import com.example.stajgunlugum.userInterface.activitydetail.ActivityDetailScreen
import com.example.stajgunlugum.userInterface.addactivity.AddActivityScreen
import com.example.stajgunlugum.userInterface.allactivities.AllActivitiesScreen
import com.example.stajgunlugum.userInterface.calendar.CalendarScreen
import com.example.stajgunlugum.userInterface.dailyreport.DailyReportScreen
import com.example.stajgunlugum.userInterface.home.HomeScreen
import com.example.stajgunlugum.userInterface.reportdetail.ReportDetailScreen
import com.example.stajgunlugum.userInterface.reports.ReportsScreen
import com.example.stajgunlugum.userInterface.splash.SplashScreen

import com.example.stajgunlugum.viewmodel.ActivityViewModel
import com.example.stajgunlugum.viewmodel.ActivityViewModelFactory
import com.example.stajgunlugum.viewmodel.ReportViewModel
import com.example.stajgunlugum.viewmodel.ReportViewModelFactory

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object Routes {

    const val SPLASH = "splash"

    const val HOME = "home"

    const val ADD_ACTIVITY = "add_activity"

    const val ACTIVITY_DETAIL = "activity_detail"

    const val DAILY_REPORT = "daily_report"

    const val REPORTS = "reports"

    const val REPORT_DETAIL = "report_detail"

    const val CALENDAR = "calendar"

    const val ALL_ACTIVITIES = "all_activities"
}


@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    val context =
        LocalContext.current


    // =========================================================
    // ROOM DATABASE
    // =========================================================

    val database = remember {

        AppDatabase.getDatabase(
            context
        )
    }


    // =========================================================
    // ACTIVITY REPOSITORY + VIEWMODEL
    // =========================================================

    val activityRepository = remember {

        ActivityRepository(
            activityDao =
                database.activityDao()
        )
    }


    val activityViewModelFactory = remember {

        ActivityViewModelFactory(
            repository =
                activityRepository
        )
    }


    val activityViewModel: ActivityViewModel =
        viewModel(
            factory =
                activityViewModelFactory
        )


    val activities by
    activityViewModel
        .allActivities
        .collectAsState()


    // =========================================================
    // REPORT REPOSITORY + VIEWMODEL
    // =========================================================

    val reportRepository = remember {

        ReportRepository(
            reportDao =
                database.reportDao()
        )
    }


    val reportViewModelFactory = remember {

        ReportViewModelFactory(
            repository =
                reportRepository
        )
    }


    val reportViewModel: ReportViewModel =
        viewModel(
            factory =
                reportViewModelFactory
        )


    val reports by
    reportViewModel
        .allReports
        .collectAsState()


    // =========================================================
    // SEÇİLİ FAALİYET
    // =========================================================

    var selectedActivity by remember {

        mutableStateOf<ActivityFormData?>(
            null
        )
    }


    // =========================================================
    // SEÇİLİ RAPOR
    // =========================================================

    var selectedReport by remember {

        mutableStateOf<ReportEntity?>(
            null
        )
    }


    // =========================================================
    // NAV HOST
    // =========================================================

    NavHost(

        navController =
            navController,

        startDestination =
            Routes.SPLASH
    ) {


        // =====================================================
        // 0. SPLASH
        // =====================================================

        composable(
            route =
                Routes.SPLASH
        ) {

            SplashScreen(

                onFinished = {

                    navController.navigate(
                        Routes.HOME
                    ) {

                        popUpTo(
                            Routes.SPLASH
                        ) {

                            inclusive =
                                true
                        }
                    }
                }
            )
        }



        // 1. ANA SAYFA


        composable(
            route =
                Routes.HOME
        ) {

            HomeScreen(

                activities =
                    activities,


                // YENİ FAALİYET

                onAddActivityClick = {

                    selectedActivity =
                        null

                    navController.navigate(
                        Routes.ADD_ACTIVITY
                    )
                },


                // FAALİYET DETAY

                onActivityClick = { activity ->

                    selectedActivity =
                        activity.toFormData()


                    navController.navigate(
                        Routes.ACTIVITY_DETAIL
                    )
                },


                // TAKVİM

                onCalendarClick = {

                    navController.navigate(
                        Routes.CALENDAR
                    )
                },


                // RAPORLAR

                onReportsClick = {

                    navController.navigate(
                        Routes.REPORTS
                    )
                },


                // TÜM FAALİYETLER

                onAllActivitiesClick = {

                    navController.navigate(
                        Routes.ALL_ACTIVITIES
                    )
                }
            )
        }



        // 2. FAALİYET EKLE / DÜZENLE


        composable(
            route =
                Routes.ADD_ACTIVITY
        ) {

            AddActivityScreen(

                initialActivity =
                    selectedActivity,


                onBackClick = {

                    navController.popBackStack()
                },


                onCancelClick = {

                    navController.popBackStack()
                },


                onSaveSuccess = { activityData ->

                    val existingActivity =
                        selectedActivity



                    // MEVCUT FAALİYETİ DÜZENLE


                    if (
                        existingActivity != null &&
                        existingActivity.id != 0
                    ) {

                        val updatedEntity =
                            ActivityEntity(

                                id =
                                    existingActivity.id,

                                title =
                                    activityData.title,

                                description =
                                    activityData.description,

                                category =
                                    activityData.category,

                                technologies =
                                    activityData.technologies,

                                learnedConcepts =
                                    activityData.learnedConcepts,

                                durationMinutes =
                                    activityData.durationMinutes,

                                notes =
                                    activityData.notes,

                                date =
                                    activityData.date
                            )


                        activityViewModel
                            .updateActivity(
                                updatedEntity
                            )


                        selectedActivity =
                            activityData.copy(

                                id =
                                    existingActivity.id
                            )


                        navController
                            .popBackStack()
                    }



                    // YENİ FAALİYET


                    else {

                        val activityEntity =
                            ActivityEntity(

                                title =
                                    activityData.title,

                                description =
                                    activityData.description,

                                category =
                                    activityData.category,

                                technologies =
                                    activityData.technologies,

                                learnedConcepts =
                                    activityData.learnedConcepts,

                                durationMinutes =
                                    activityData.durationMinutes,

                                notes =
                                    activityData.notes,

                                date =
                                    activityData.date
                            )


                        activityViewModel
                            .insertActivity(

                                activity =
                                    activityEntity,

                                onActivityInserted =
                                    { insertedId ->


                                        selectedActivity =
                                            activityData.copy(

                                                id =
                                                    insertedId
                                            )


                                        navController.navigate(
                                            Routes.ACTIVITY_DETAIL
                                        )
                                    }
                            )
                    }
                }
            )
        }



        // 3. FAALİYET DETAY


        composable(
            route =
                Routes.ACTIVITY_DETAIL
        ) {

            selectedActivity?.let { activity ->

                ActivityDetailScreen(

                    activity =
                        activity,


                    onBackClick = {

                        navController
                            .popBackStack()
                    },


                    // DÜZENLE

                    onEditClick = {

                        navController.navigate(
                            Routes.ADD_ACTIVITY
                        )
                    },


                    // SİL

                    onDeleteClick = {

                        val activityEntity =
                            ActivityEntity(

                                id =
                                    activity.id,

                                title =
                                    activity.title,

                                description =
                                    activity.description,

                                category =
                                    activity.category,

                                technologies =
                                    activity.technologies,

                                learnedConcepts =
                                    activity.learnedConcepts,

                                durationMinutes =
                                    activity.durationMinutes,

                                notes =
                                    activity.notes,

                                date =
                                    activity.date
                            )


                        activityViewModel
                            .deleteActivity(
                                activityEntity
                            )


                        selectedActivity =
                            null


                        navController
                            .popBackStack(
                                route =
                                    Routes.HOME,

                                inclusive =
                                    false
                            )
                    },


                    // AI RAPOR

                    onCreateReportClick = {

                        navController.navigate(
                            Routes.DAILY_REPORT
                        )
                    }
                )
            }
        }



        // 4. GÜNLÜK AI RAPOR


        composable(
            route =
                Routes.DAILY_REPORT
        ) {

            selectedActivity?.let { activity ->

                DailyReportScreen(

                    activity =
                        activity,


                    onBackClick = {

                        navController
                            .popBackStack()
                    },


                    // TAKVİM İKONU

                    onCalendarClick = {

                        navController.navigate(
                            Routes.CALENDAR
                        )
                    },


                    // RAPORU ROOM'A KAYDET

                    onSaveReport =
                        { reportText, language ->


                            val currentDateTime =
                                LocalDateTime.now()


                            val formatter =
                                DateTimeFormatter
                                    .ofPattern(
                                        "dd.MM.yyyy HH:mm"
                                    )


                            val createdAt =
                                currentDateTime
                                    .format(
                                        formatter
                                    )


                            val reportTitle =

                                if (
                                    language == "en"
                                ) {

                                    "${activity.title} - English Report"

                                } else {

                                    "${activity.title} - Türkçe Rapor"
                                }


                            val reportEntity =
                                ReportEntity(

                                    activityId =
                                        activity.id,

                                    title =
                                        reportTitle,

                                    reportText =
                                        reportText,

                                    language =
                                        language,

                                    createdAt =
                                        createdAt
                                )


                            reportViewModel
                                .insertReport(
                                    reportEntity
                                )
                        }
                )
            }
        }



        // 5. TÜM RAPORLAR


        composable(
            route =
                Routes.REPORTS
        ) {

            ReportsScreen(

                reports =
                    reports,


                onBackClick = {

                    navController
                        .popBackStack()
                },


                onReportClick = { report ->

                    selectedReport =
                        report


                    navController.navigate(
                        Routes.REPORT_DETAIL
                    )
                },


                onDeleteReport = { report ->

                    reportViewModel
                        .deleteReport(
                            report
                        )
                }
            )
        }



        // 6. RAPOR DETAY


        composable(
            route =
                Routes.REPORT_DETAIL
        ) {

            selectedReport?.let { report ->

                ReportDetailScreen(

                    report =
                        report,


                    onBackClick = {

                        navController
                            .popBackStack()
                    },


                    onDeleteClick = {

                        reportViewModel
                            .deleteReport(
                                report
                            )


                        selectedReport =
                            null


                        navController
                            .popBackStack(

                                route =
                                    Routes.REPORTS,

                                inclusive =
                                    false
                            )
                    }
                )
            }
        }



        // 7. TAKVİM


        composable(
            route =
                Routes.CALENDAR
        ) {

            CalendarScreen(

                activities =
                    activities,


                onBackClick = {

                    navController
                        .popBackStack()
                },


                onActivityClick = { activity ->

                    selectedActivity =
                        activity.toFormData()


                    navController.navigate(
                        Routes.ACTIVITY_DETAIL
                    )
                }
            )
        }



        // 8. TÜM FAALİYETLER


        composable(
            route =
                Routes.ALL_ACTIVITIES
        ) {

            AllActivitiesScreen(

                activities =
                    activities,


                onBackClick = {

                    navController
                        .popBackStack()
                },


                onActivityClick = { activity ->

                    selectedActivity =
                        activity.toFormData()


                    navController.navigate(
                        Routes.ACTIVITY_DETAIL
                    )
                }
            )
        }
    }
}



// ActivityEntity → ActivityFormData
// Aynı dönüşüm kodunu tekrar tekrar yazmamak için.


private fun ActivityEntity.toFormData(): ActivityFormData {

    return ActivityFormData(

        id =
            id,

        title =
            title,

        description =
            description,

        category =
            category,

        technologies =
            technologies,

        learnedConcepts =
            learnedConcepts,

        durationMinutes =
            durationMinutes,

        notes =
            notes,

        date =
            date
    )
}