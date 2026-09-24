package com.example.stajgunlugum.userInterface.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stajgunlugum.data.local.ActivityEntity
import com.example.stajgunlugum.ui.theme.MainGreen
import com.example.stajgunlugum.ui.theme.PageBackground
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.SecondaryText
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    activities: List<ActivityEntity>,
    onBackClick: () -> Unit = {},
    onActivityClick: (ActivityEntity) -> Unit = {}
) {

    var currentMonth by remember {
        mutableStateOf(
            YearMonth.now()
        )
    }

    var selectedDate by remember {
        mutableStateOf(
            LocalDate.now()
        )
    }

    val monthFormatter = remember {
        DateTimeFormatter.ofPattern(
            "MMMM yyyy",
            Locale("tr", "TR")
        )
    }

    val selectedDateFormatter = remember {
        DateTimeFormatter.ofPattern(
            "dd MMMM yyyy, EEEE",
            Locale("tr", "TR")
        )
    }

    val selectedDateActivities = activities.filter {
        it.date == selectedDate.toString()
    }


    Scaffold(
        containerColor = PageBackground,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Takvim",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Geri"
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )
            }


            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {

                            currentMonth =
                                currentMonth.minusMonths(1)
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ChevronLeft,

                            contentDescription =
                                "Önceki ay"
                        )
                    }


                    Text(
                        text =
                            currentMonth
                                .format(monthFormatter)
                                .replaceFirstChar {
                                    it.uppercase()
                                },

                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )


                    IconButton(
                        onClick = {

                            currentMonth =
                                currentMonth.plusMonths(1)
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ChevronRight,

                            contentDescription =
                                "Sonraki ay"
                        )
                    }
                }
            }


            item {

                CalendarWeekHeader()
            }


            item {

                CalendarMonthGrid(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,
                    activities = activities,

                    onDateClick = { date ->

                        selectedDate = date
                    }
                )
            }


            item {

                Text(
                    text =
                        selectedDate.format(
                            selectedDateFormatter
                        ),

                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )
            }


            if (selectedDateActivities.isEmpty()) {

                item {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(14.dp),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            )
                    ) {

                        Text(
                            text =
                                "Bu tarihte kayıtlı faaliyet yok.",

                            modifier =
                                Modifier.padding(18.dp),

                            fontSize = 14.sp,
                            color = SecondaryText
                        )
                    }
                }

            } else {

                items(
                    items =
                        selectedDateActivities,

                    key = {
                        it.id
                    }
                ) { activity ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                onActivityClick(
                                    activity
                                )
                            },

                        shape =
                            RoundedCornerShape(14.dp),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(16.dp)
                        ) {

                            Text(
                                text =
                                    activity.title,

                                fontSize = 15.sp,

                                fontWeight =
                                    FontWeight.SemiBold,

                                color = PrimaryText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(5.dp)
                            )


                            Text(
                                text =
                                    activity.category,

                                fontSize = 13.sp,

                                color =
                                    SecondaryText
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(5.dp)
                            )


                            Text(
                                text =
                                    formatDuration(
                                        activity.durationMinutes
                                    ),

                                fontSize = 13.sp,

                                color = MainGreen
                            )
                        }
                    }
                }
            }


            item {

                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )
            }
        }
    }
}


@Composable
private fun CalendarWeekHeader() {

    val days = listOf(
        "Pzt",
        "Sal",
        "Çar",
        "Per",
        "Cum",
        "Cmt",
        "Paz"
    )


    Row(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        days.forEach { day ->

            Box(
                modifier =
                    Modifier.weight(1f),

                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text = day,
                    fontSize = 12.sp,
                    fontWeight =
                        FontWeight.SemiBold,
                    color = SecondaryText
                )
            }
        }
    }
}


@Composable
private fun CalendarMonthGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    activities: List<ActivityEntity>,
    onDateClick: (LocalDate) -> Unit
) {

    val firstDay =
        currentMonth.atDay(1)

    val daysInMonth =
        currentMonth.lengthOfMonth()

    val firstDayOffset =
        firstDay.dayOfWeek.value - 1

    val totalCells =
        ((firstDayOffset + daysInMonth + 6) / 7) * 7


    Column(
        verticalArrangement =
            Arrangement.spacedBy(5.dp)
    ) {

        for (
        weekStart in 0 until totalCells step 7
        ) {

            Row(
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                for (column in 0..6) {

                    val cellIndex =
                        weekStart + column

                    val dayNumber =
                        cellIndex -
                                firstDayOffset + 1


                    if (
                        dayNumber in 1..daysInMonth
                    ) {

                        val date =
                            currentMonth.atDay(
                                dayNumber
                            )


                        val hasActivity =
                            activities.any {
                                it.date ==
                                        date.toString()
                            }


                        CalendarDayCell(
                            date = date,

                            selected =
                                date == selectedDate,

                            hasActivity =
                                hasActivity,

                            onClick = {

                                onDateClick(date)
                            }
                        )

                    } else {

                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun androidx.compose.foundation.layout.RowScope.CalendarDayCell(
    date: LocalDate,
    selected: Boolean,
    hasActivity: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .padding(3.dp)
            .clip(CircleShape)
            .background(
                if (selected) {
                    MainGreen
                } else {
                    Color.Transparent
                }
            )
            .clickable(
                onClick = onClick
            ),

        contentAlignment =
            Alignment.Center
    ) {

        Column(
            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            Text(
                text =
                    date.dayOfMonth.toString(),

                fontSize = 14.sp,

                fontWeight =
                    if (selected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    },

                color =
                    if (selected) {
                        Color.White
                    } else {
                        PrimaryText
                    }
            )


            if (hasActivity) {

                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )


                Box(
                    modifier = Modifier
                        .height(5.dp)
                        .fillMaxWidth(0.12f)
                        .background(
                            color =
                                if (selected) {
                                    Color.White
                                } else {
                                    MainGreen
                                },

                            shape =
                                CircleShape
                        )
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