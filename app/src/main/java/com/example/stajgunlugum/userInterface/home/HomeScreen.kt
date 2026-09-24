package com.example.stajgunlugum.userInterface.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stajgunlugum.data.local.ActivityEntity
import com.example.stajgunlugum.model.ActivityUiModel
import com.example.stajgunlugum.ui.theme.MainGreen
import com.example.stajgunlugum.ui.theme.PageBackground
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.StajGunlugumTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun HomeScreen(
    onAddActivityClick: () -> Unit = {},
    onActivityClick: (ActivityEntity) -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onReportsClick: () -> Unit = {},
    activities: List<ActivityEntity> = emptyList(),
    onAllActivitiesClick: () -> Unit = {}

) {

    val currentDate = LocalDate.now()


    val dateFormatter = DateTimeFormatter.ofPattern(
        "dd MMMM yyyy, EEEE",
        Locale("tr", "TR")
    )


    val formattedDate =
        currentDate.format(dateFormatter)


    val today =
        LocalDate.now().toString()


    val todayActivities = activities.filter { activity ->

        activity.date == today
    }


    val todayDurationMinutes = todayActivities.sumOf {

        it.durationMinutes
    }


    Scaffold(

        containerColor = PageBackground,


        bottomBar = {

            HomeBottomNavigation(

                onCalendarClick =
                    onCalendarClick,

                onReportsClick =
                    onReportsClick
            )
        }

    ) { innerPadding ->


        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),

            verticalArrangement =
                Arrangement.spacedBy(15.dp)
        ) {


            item {

                HomeTopBar()
            }


            item {

                Text(
                    text = formattedDate,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText
                )
            }


            item {

                WorkingTimeCard(

                    workingTime =
                        formatDuration(
                            todayDurationMinutes
                        ),

                    targetTime = "8s"
                )
            }


            item {

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {


                    StatisticCard(

                        modifier =
                            Modifier.weight(1f),

                        title =
                            "Bugünkü Faaliyetler",

                        value =
                            todayActivities.size.toString(),

                        valueColor =
                            MainGreen
                    )


                    StatisticCard(

                        modifier =
                            Modifier.weight(1f),

                        title =
                            "Toplam Faaliyet",

                        value =
                            activities.size.toString(),

                        valueColor =
                            MainGreen
                    )
                }
            }


            item {

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {


                    Text(

                        text =
                            "Bugünkü Faaliyetler",

                        fontSize = 18.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            PrimaryText
                    )


                    Text(
                        text = "Tümü",

                        modifier = Modifier.clickable {
                            onAllActivitiesClick()
                        },

                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MainGreen
                    )
                }
            }


            if (todayActivities.isEmpty()) {

                item {

                    Text(

                        text =
                            "Bugün henüz faaliyet eklenmedi.",

                        fontSize = 14.sp,

                        color =
                            PrimaryText
                    )
                }
            }


            items(

                items =
                    todayActivities,

                key = { activity ->

                    activity.id
                }

            ) { activity ->


                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            onActivityClick(
                                activity
                            )
                        }
                ) {


                    ActivityItem(

                        activity =
                            ActivityUiModel(

                                title =
                                    activity.title,

                                duration =
                                    formatDuration(
                                        activity.durationMinutes
                                    ),

                                color =
                                    MainGreen
                            )
                    )
                }
            }


            item {

                AddActivityButton(

                    onClick =
                        onAddActivityClick
                )


                Spacer(

                    modifier =
                        Modifier.height(8.dp)
                )
            }
        }
    }
}


private fun formatDuration(
    totalMinutes: Int
): String {

    val hours =
        totalMinutes / 60

    val minutes =
        totalMinutes % 60


    return when {

        hours > 0 &&
                minutes > 0 ->

            "${hours}s ${minutes}dk"


        hours > 0 ->

            "${hours}s"


        else ->

            "${minutes}dk"
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomeScreenPreview() {

    StajGunlugumTheme {

        HomeScreen(
            activities = emptyList()
        )
    }
}