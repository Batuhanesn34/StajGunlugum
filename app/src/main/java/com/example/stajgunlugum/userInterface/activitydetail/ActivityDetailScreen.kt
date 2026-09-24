package com.example.stajgunlugum.userInterface.activitydetail

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.TimeUtils.formatDuration
import com.example.stajgunlugum.model.ActivityFormData
import com.example.stajgunlugum.ui.theme.DarkGreen
import com.example.stajgunlugum.ui.theme.PageBackground
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.SecondaryText
import com.example.stajgunlugum.ui.theme.StajGunlugumTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration
import com.example.stajgunlugum.ui.theme.LightGreen



@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ActivityDetailScreen(
    activity : ActivityFormData, //2.sayfadan gelen faaliyet bilgileri
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onCreateReportClick: () -> Unit = {},

){
   /* val currentDate = LocalDate.now()

    val dateFormatter = DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        Locale("tr", "TR")
    )

    val formattedDate = currentDate.format(dateFormatter)
*/


        Scaffold(
            containerColor = PageBackground,

            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Faaliyet Detayı",
                            color = PrimaryText,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        TextButton( onClick = onBackClick) {
                            Text(
                                text = "Geri",
                                color = DarkGreen
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )

                )
            }

        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = 15.dp,
                        vertical = 15.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                ActivitySummaryCard(
                    title = activity.title,
                    category = activity.category,
                    duration = formatDuration(
                        activity.durationMinutes
                    ),
                    date = activity.date
                )
                DetailSection(
                    title = "Açıklama",
                    value = activity.description
                )

                DetailSection(
                    title = "Kullanılan Teknolojiler",
                    value = activity.technologies.ifBlank {
                        "Kullanılan teknoloji eklenmedi."
                    }
                )

                DetailSection(
                    title = "Öğrenilen Kavramlar",
                    value = activity.learnedConcepts.ifBlank {
                        "Öğrenilen kavram eklenmedi."
                    }
                )

                DetailSection(
                    title = "Notlar",
                    value = activity.notes.ifBlank {
                        "Not eklenmedi."
                    }
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)

                ) {
                    OutlinedButton(
                        onClick = onEditClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Düzenle",
                            tint = DarkGreen
                        )
                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                        Text(
                            text = "Düzenle",
                            color = DarkGreen
                        )
                    }
                    OutlinedButton(
                        onClick = onDeleteClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Sil"
                        )
                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )
                        Text(
                            text = "Sil"
                        )
                    }
                }
                Button(
                    onClick = onCreateReportClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen,
                        contentColor = Color.White
                    )
                ){
                    Text(
                        text = "Rapor Oluştur",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }


            }
        }
    }



@Composable
fun ActivitySummaryCard(
    title: String,
    category: String,
    duration: String,
    date: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightGreen
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = DarkGreen,
                        shape = RoundedCornerShape(10.dp),
                    ),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = Icons.Default.Storage,
                    contentDescription = "Veritabanı",
                    tint = Color.White
                )

            }
            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )

                Text(
                    text = "$category • $duration",
                    fontSize = 13.sp,
                    color = SecondaryText
                )

                Text(
                    text = date,
                    fontSize = 13.sp,
                    color = PrimaryText
                )
            }

        }
    }
}

@Composable
fun DetailSection(
    title : String,
    value : String,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = value,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            color = PrimaryText
        )
        Spacer(
            modifier = Modifier.height(14.dp)
        )

        HorizontalDivider()

    }
}

fun formatDuration(
    totalMinutes: Int
): String {
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return when {
        hours > 0 && minutes > 0 ->
            "$hours saat $minutes dakika"

        hours > 0 ->
            "$hours saat"

        else ->
            "$minutes dakika"
    }
}



@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
private fun ActivityDetailScreenPreview() {
    StajGunlugumTheme {
        ActivityDetailScreen(
            activity = ActivityFormData(
                title = "SQL tablo oluşturma",
                description = "Bilgisayar envanteri için SQL tabloları oluşturuldu.",
                category = "Veritabanı",
                technologies = "SQL Server, SSMS, Excel",
                learnedConcepts = "Primary key, Foreign key, INNER JOIN",
                durationMinutes = 90,
                notes = "Test sorguları çalıştırıldı.",
                date = "31 Temmuz 2026"
            )
        )
    }
}











