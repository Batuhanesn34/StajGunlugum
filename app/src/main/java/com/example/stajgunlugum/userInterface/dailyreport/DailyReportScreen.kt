package com.example.stajgunlugum.userInterface.dailyreport

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stajgunlugum.data.remote.ReportRequestDto
import com.example.stajgunlugum.data.remote.RetrofitInstance
import com.example.stajgunlugum.model.ActivityFormData
import com.example.stajgunlugum.ui.theme.DarkGreen
import com.example.stajgunlugum.ui.theme.LightGreen
import com.example.stajgunlugum.ui.theme.MainGreen
import com.example.stajgunlugum.ui.theme.PageBackground
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.SecondaryText
import com.example.stajgunlugum.ui.theme.StajGunlugumTheme
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyReportScreen(
    onBackClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    activity: ActivityFormData,
    onSaveReport: (String, String) -> Unit = { _, _ -> }
) {

    val coroutineScope = rememberCoroutineScope()

    var reportText by remember {
        mutableStateOf("")
    }

    var reportCreated by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var reportSaved by remember {
        mutableStateOf(false)
    }

    var selectedLanguage by remember {
        mutableStateOf("tr")
    }

    val currentDate = LocalDate.now()

    val dateFormatter = DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        Locale("tr", "TR")
    )

    val formattedDate = currentDate.format(dateFormatter)


    // Ana ekran
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Rapor ekranı
        Scaffold(

            modifier = Modifier.blur(
                radius = if (isLoading) {
                    8.dp
                } else {
                    0.dp
                }
            ),

            containerColor = PageBackground,

            topBar = {

                TopAppBar(

                    title = {
                        Text(
                            text = "Günlük Rapor",
                            color = PrimaryText,
                            fontWeight = FontWeight.Bold
                        )
                    },

                    navigationIcon = {

                        IconButton(
                            onClick = onBackClick
                        ) {

                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Geri",
                                tint = PrimaryText
                            )
                        }
                    },

                    actions = {

                        IconButton(
                            onClick = onCalendarClick
                        ) {

                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Tarih seç",
                                tint = PrimaryText
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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),

                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Tarih
                Text(
                    text = formattedDate,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )


                // Faaliyet istatistikleri
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    ReportStatisticCard(
                        modifier = Modifier.weight(1f),
                        value = "1",
                        title = "Faaliyet"
                    )

                    ReportStatisticCard(
                        modifier = Modifier.weight(1f),
                        value = formatDuration(
                            activity.durationMinutes
                        ),
                        title = "Toplam Süre"
                    )

                    ReportStatisticCard(
                        modifier = Modifier.weight(1f),
                        value = "-",
                        title = "Hedefe Ulaşım"
                    )
                }


                // Rapor dili
                Text(
                    text = "Rapor Dili",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    // Türkçe
                    Button(

                        onClick = {

                            selectedLanguage = "tr"
                            reportCreated = false
                            reportText = ""
                            reportSaved = false
                            errorMessage = ""
                        },

                        modifier = Modifier.weight(1f),

                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (selectedLanguage == "tr") {
                                    MainGreen
                                } else {
                                    Color.LightGray
                                },

                            contentColor =
                                if (selectedLanguage == "tr") {
                                    Color.White
                                } else {
                                    PrimaryText
                                }
                        )
                    ) {

                        Text(
                            text = "Türkçe",
                            fontWeight = FontWeight.SemiBold
                        )
                    }


                    // İngilizce
                    Button(

                        onClick = {

                            selectedLanguage = "en"
                            reportCreated = false
                            reportText = ""
                            reportSaved = false
                            errorMessage = ""
                        },

                        modifier = Modifier.weight(1f),

                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (selectedLanguage == "en") {
                                    MainGreen
                                } else {
                                    Color.LightGray
                                },

                            contentColor =
                                if (selectedLanguage == "en") {
                                    Color.White
                                } else {
                                    PrimaryText
                                }
                        )
                    ) {

                        Text(
                            text = "English",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }


                // AI raporu oluşturma butonu
                Button(

                    onClick = {

                        coroutineScope.launch {

                            try {

                                isLoading = true
                                errorMessage = ""
                                reportSaved = false
                                reportCreated = false
                                reportText = ""


                                val request = ReportRequestDto(
                                    title = activity.title,
                                    description = activity.description,
                                    category = activity.category,
                                    technologies = activity.technologies,
                                    learnedConcepts = activity.learnedConcepts,
                                    durationMinutes = activity.durationMinutes,
                                    notes = activity.notes,
                                    language = selectedLanguage,
                                    length = "medium"
                                )


                                val response =
                                    RetrofitInstance
                                        .reportApi
                                        .generateReport(
                                            request
                                        )


                                reportText = response.report
                                reportCreated = true


                            } catch (e: Exception) {

                                reportCreated = false
                                reportText = ""

                                errorMessage =
                                    getFriendlyErrorMessage(
                                        e
                                    )

                            } finally {

                                isLoading = false
                            }
                        }
                    },

                    enabled = !isLoading,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape = RoundedCornerShape(14.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen,
                        contentColor = Color.White
                    )
                ) {

                    Text(
                        text = "AI ile Rapor Oluştur",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }


                // Hata mesajı
                if (errorMessage.isNotEmpty()) {

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(12.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {

                            Text(
                                text = "Rapor oluşturulamadı",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB3261E)
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = errorMessage,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                color = Color(0xFFB3261E)
                            )
                        }
                    }
                }


                // Oluşturulan rapor
                if (
                    reportCreated &&
                    reportText.isNotEmpty()
                ) {

                    ReportCard(

                        title =
                            if (selectedLanguage == "en") {
                                "English Report"
                            } else {
                                "Türkçe Rapor"
                            },

                        reportText = reportText
                    )


                    // Raporu kaydetme butonu
                    Button(

                        onClick = {

                            if (!reportSaved) {

                                onSaveReport(
                                    reportText,
                                    selectedLanguage
                                )

                                reportSaved = true
                            }
                        },

                        enabled = !reportSaved,

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),

                        shape = RoundedCornerShape(12.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGreen,
                            contentColor = Color.White
                        )
                    ) {

                        Text(
                            text =
                                if (reportSaved) {
                                    "Kaydedildi"
                                } else {
                                    "Raporu Kaydet"
                                },

                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }


                    if (reportSaved) {

                        Text(
                            text = "Rapor, Raporlar bölümüne kaydedildi.",
                            fontSize = 13.sp,
                            color = DarkGreen
                        )
                    }
                }


                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }


        // AI rapor oluştururken gösterilen animasyon
        if (isLoading) {

            AiLoadingOverlay()
        }
    }
}


// Kullanıcı dostu hata mesajları
private fun getFriendlyErrorMessage(
    error: Exception
): String {

    return when (error) {

        is UnknownHostException -> {

            "İnternet bağlantısı bulunamadı. Bağlantınızı kontrol edip tekrar deneyin."
        }


        is SocketTimeoutException -> {

            "AI yanıtı gecikti. Lütfen birkaç saniye sonra tekrar deneyin."
        }


        is ConnectException -> {

            "Sunucuya bağlanılamadı. Lütfen biraz sonra tekrar deneyin."
        }


        is HttpException -> {

            when (error.code()) {

                429 -> {

                    "AI hizmeti şu anda yoğun veya kullanım sınırına ulaşıldı. Lütfen biraz sonra tekrar deneyin."
                }


                in 500..599 -> {

                    "AI servisine şu anda ulaşılamıyor. Lütfen biraz sonra tekrar deneyin."
                }


                401, 403 -> {

                    "AI servisine erişim sağlanamadı."
                }


                else -> {

                    "Rapor oluşturulamadı. Lütfen tekrar deneyin."
                }
            }
        }


        else -> {

            val message =
                error.message.orEmpty()


            when {

                message.contains(
                    "Unable to resolve host",
                    ignoreCase = true
                ) -> {

                    "İnternet bağlantısı bulunamadı. Bağlantınızı kontrol edip tekrar deneyin."
                }


                message.contains(
                    "timeout",
                    ignoreCase = true
                ) -> {

                    "AI yanıtı gecikti. Lütfen tekrar deneyin."
                }


                message.contains(
                    "Failed to connect",
                    ignoreCase = true
                ) -> {

                    "Sunucuya bağlanılamadı. Lütfen biraz sonra tekrar deneyin."
                }


                else -> {

                    "Beklenmeyen bir hata oluştu. İnternet bağlantınızı kontrol edip tekrar deneyin."
                }
            }
        }
    }
}


// AI loading animasyonu
@Composable
private fun AiLoadingOverlay() {

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "ai_loading"
        )


    val pulseScale by
    infiniteTransition.animateFloat(

        initialValue = 0.92f,
        targetValue = 1.08f,

        animationSpec =
            infiniteRepeatable(

                animation =
                    tween(
                        durationMillis = 700
                    ),

                repeatMode =
                    RepeatMode.Reverse
            ),

        label = "ai_pulse"
    )


    val glowAlpha by
    infiniteTransition.animateFloat(

        initialValue = 0.20f,
        targetValue = 0.48f,

        animationSpec =
            infiniteRepeatable(

                animation =
                    tween(
                        durationMillis = 900
                    ),

                repeatMode =
                    RepeatMode.Reverse
            ),

        label = "ai_glow"
    )


    val interactionSource =
        remember {
            MutableInteractionSource()
        }


    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(
                    alpha = 0.30f
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                // Loading sırasında ekrandaki tıklamaları engeller.
            },

        contentAlignment = Alignment.Center
    ) {


        Card(

            modifier = Modifier
                .fillMaxWidth(0.82f),

            shape = RoundedCornerShape(28.dp),

            colors = CardDefaults.cardColors(
                containerColor =
                    Color.White.copy(
                        alpha = 0.96f
                    )
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 18.dp
            )
        ) {


            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 28.dp,
                        vertical = 32.dp
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {


                // AI logosu ve animasyonu
                Box(

                    modifier =
                        Modifier.size(115.dp),

                    contentAlignment =
                        Alignment.Center
                ) {


                    // Dış parlama
                    Box(

                        modifier = Modifier
                            .size(100.dp)
                            .scale(
                                pulseScale
                            )
                            .clip(
                                CircleShape
                            )
                            .background(
                                MainGreen.copy(
                                    alpha = glowAlpha
                                )
                            )
                    )


                    // Dönen yükleme göstergesi
                    CircularProgressIndicator(

                        modifier =
                            Modifier.size(95.dp),

                        color =
                            MainGreen,

                        trackColor =
                            LightGreen,

                        strokeWidth =
                            4.dp
                    )


                    // Ortadaki AI logosu
                    Box(

                        modifier = Modifier
                            .size(70.dp)
                            .clip(
                                CircleShape
                            )
                            .background(
                                DarkGreen
                            ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text = "AI",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )


                Text(
                    text = "Yapay zekâ raporunu hazırlıyor",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText,
                    textAlign = TextAlign.Center
                )


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                Text(
                    text = "Faaliyet bilgilerin analiz ediliyor ve raporun oluşturuluyor.",
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = SecondaryText,
                    textAlign = TextAlign.Center
                )


                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )


                Text(
                    text = "Bu işlem birkaç saniye sürebilir.",
                    fontSize = 12.sp,
                    color = SecondaryText.copy(
                        alpha = 0.75f
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


// Oluşturulan rapor kartı
@Composable
fun ReportCard(
    title: String,
    reportText: String
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

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

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {


            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = PrimaryText
            )


            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )


            HorizontalDivider()


            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )


            Text(
                text = reportText,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = SecondaryText
            )
        }
    }
}


// İstatistik kartı
@Composable
fun ReportStatisticCard(
    modifier: Modifier = Modifier,
    value: String,
    title: String
) {

    Card(

        modifier =
            modifier.height(95.dp),

        shape =
            RoundedCornerShape(15.dp),

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

            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),

            verticalArrangement =
                Arrangement.Center,

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {


            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryText,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )


            Text(
                text = title,
                fontSize = 12.sp,
                color = SecondaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}


// Dakika değerini saat ve dakika olarak gösterir.
private fun formatDuration(
    totalMinutes: Int
): String {

    val hours =
        totalMinutes / 60

    val minutes =
        totalMinutes % 60


    return when {

        hours > 0 &&
                minutes > 0 -> {

            "${hours}s ${minutes}dk"
        }


        hours > 0 -> {

            "${hours}s"
        }


        else -> {

            "${minutes}dk"
        }
    }
}


// Önizleme
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DailyReportScreenPreview() {

    StajGunlugumTheme {

        DailyReportScreen(

            activity =
                ActivityFormData(

                    id = 1,

                    title =
                        "SQL tablo oluşturma",

                    description =
                        "Bilgisayar envanteri için tablolar oluşturuldu.",

                    category =
                        "Yazılım / Bilişim",

                    technologies =
                        "SQL Server, SSMS",

                    learnedConcepts =
                        "Primary Key, Foreign Key",

                    durationMinutes =
                        90,

                    notes =
                        "5 paragraf yaz ve resmi bir dil kullan.",

                    date =
                        "2026-08-17"
                )
        )
    }
}