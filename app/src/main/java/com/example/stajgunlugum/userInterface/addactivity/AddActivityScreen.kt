package com.example.stajgunlugum.userInterface.addactivity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stajgunlugum.model.ActivityFormData
import com.example.stajgunlugum.ui.theme.MainGreen
import com.example.stajgunlugum.ui.theme.PageBackground
import com.example.stajgunlugum.ui.theme.PrimaryText
import com.example.stajgunlugum.ui.theme.StajGunlugumTheme
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(
    initialActivity: ActivityFormData? = null,
    onBackClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onSaveSuccess: (ActivityFormData) -> Unit = {}
) {


    // GENEL KATEGORİLER


    val categories = listOf(
        "Yazılım / Bilişim",
        "Hukuk",
        "Muhasebe / Finans",
        "İnsan Kaynakları",
        "Pazarlama / Satış",
        "Makine / Üretim",
        "Elektrik / Elektronik",
        "Sağlık",
        "Mimarlık / İnşaat",
        "Eğitim",
        "Lojistik",
        "Yönetim",
        "Araştırma",
        "Diğer"
    )



    // FORM STATE


    var title by remember(initialActivity) {
        mutableStateOf(
            initialActivity?.title ?: ""
        )
    }


    var description by remember(initialActivity) {
        mutableStateOf(
            initialActivity?.description ?: ""
        )
    }


    var selectedCategory by remember(initialActivity) {

        val existingCategory =
            initialActivity?.category ?: ""

        mutableStateOf(
            when {

                existingCategory.isBlank() ->
                    ""

                categories.contains(existingCategory) ->
                    existingCategory

                else ->
                    "Diğer"
            }
        )
    }


    var customCategory by remember(initialActivity) {

        val existingCategory =
            initialActivity?.category ?: ""

        mutableStateOf(
            if (
                existingCategory.isNotBlank() &&
                !categories.contains(existingCategory)
            ) {
                existingCategory
            } else {
                ""
            }
        )
    }


    var technologies by remember(initialActivity) {
        mutableStateOf(
            initialActivity?.technologies ?: ""
        )
    }


    var learnedConcepts by remember(initialActivity) {
        mutableStateOf(
            initialActivity?.learnedConcepts ?: ""
        )
    }


    var notes by remember(initialActivity) {
        mutableStateOf(
            initialActivity?.notes ?: ""
        )
    }


    var hour by remember(initialActivity) {
        mutableStateOf(
            if (initialActivity != null) {
                (initialActivity.durationMinutes / 60)
                    .toString()
            } else {
                ""
            }
        )
    }


    var minute by remember(initialActivity) {
        mutableStateOf(
            if (initialActivity != null) {
                (initialActivity.durationMinutes % 60)
                    .toString()
            } else {
                ""
            }
        )
    }


    var categoryMenuExpanded by remember {
        mutableStateOf(false)
    }


    var saveAttempted by remember {
        mutableStateOf(false)
    }



    // KAYDETME


    fun saveActivity() {

        saveAttempted = true


        val finalCategory =

            if (selectedCategory == "Diğer") {

                customCategory.trim()

            } else {

                selectedCategory
            }


        if (
            title.isBlank() ||
            description.isBlank() ||
            finalCategory.isBlank()
        ) {
            return
        }


        val totalMinutes =

            ((hour.toIntOrNull() ?: 0) * 60) +
                    (minute.toIntOrNull() ?: 0)


        val activityData = ActivityFormData(

            id = initialActivity?.id ?: 0,

            title = title,

            description = description,

            category = finalCategory,

            technologies = technologies,

            learnedConcepts = learnedConcepts,

            durationMinutes = totalMinutes,

            notes = notes,

            date = initialActivity?.date
                ?: LocalDate.now().toString()
        )


        onSaveSuccess(activityData)
    }



    // EKRAN


    Scaffold(

        containerColor = PageBackground,


        topBar = {

            TopAppBar(

                title = {

                    Text(

                        text =
                            if (initialActivity == null) {

                                "Faaliyet Ekle"

                            } else {

                                "Faaliyeti Düzenle"
                            },

                        fontSize = 20.sp,

                        fontWeight = FontWeight.Bold,

                        color = PrimaryText
                    )
                },


                navigationIcon = {

                    TextButton(
                        onClick = onBackClick
                    ) {

                        Text(
                            text = "Geri",
                            color = MainGreen
                        )
                    }
                },


                actions = {

                    TextButton(

                        onClick = {

                            saveActivity()
                        }
                    ) {

                        Text(

                            text =
                                if (initialActivity == null) {

                                    "Kaydet"

                                } else {

                                    "Güncelle"
                                },

                            color = MainGreen,

                            fontWeight = FontWeight.Bold
                        )
                    }
                },


                colors =
                    TopAppBarDefaults.topAppBarColors(

                        containerColor =
                            PageBackground
                    )
            )
        }

    ) { innerPadding ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {


            // BAŞLIK

            Text(
                text = "Faaliyet Başlığı",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )


            OutlinedTextField(

                value = title,

                onValueChange = {

                    title = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                placeholder = {

                    Text(
                        text =
                            "Faaliyet başlığını girin"
                    )
                },

                isError =
                    saveAttempted &&
                            title.isBlank(),

                supportingText = {

                    if (
                        saveAttempted &&
                        title.isBlank()
                    ) {

                        Text(
                            text =
                                "Başlık boş bırakılamaz."
                        )
                    }
                },

                singleLine = true
            )



            // KATEGORİ

            Text(
                text = "Kategori / Alan",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )


            Column {


                OutlinedTextField(

                    value = selectedCategory,

                    onValueChange = {},

                    modifier =
                        Modifier.fillMaxWidth(),

                    readOnly = true,

                    placeholder = {

                        Text(
                            text =
                                "Kategori seçin"
                        )
                    },

                    isError =

                        saveAttempted &&
                                selectedCategory
                                    .isBlank()
                )


                Button(

                    onClick = {

                        categoryMenuExpanded = true
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                MainGreen
                        )
                ) {

                    Text(
                        text = "Kategori Seç"
                    )
                }


                DropdownMenu(

                    expanded =
                        categoryMenuExpanded,

                    onDismissRequest = {

                        categoryMenuExpanded =
                            false
                    }
                ) {


                    categories.forEach { category ->


                        DropdownMenuItem(

                            text = {

                                Text(
                                    text = category
                                )
                            },

                            onClick = {

                                selectedCategory =
                                    category


                                if (
                                    category != "Diğer"
                                ) {

                                    customCategory = ""
                                }


                                categoryMenuExpanded =
                                    false
                            }
                        )
                    }
                }
            }



            // DİĞER KATEGORİ


            if (selectedCategory == "Diğer") {


                Text(
                    text = "Kendi Alanınızı Yazın",
                    fontSize = 14.sp,
                    fontWeight =
                        FontWeight.SemiBold,
                    color = PrimaryText
                )


                OutlinedTextField(

                    value = customCategory,

                    onValueChange = {

                        customCategory = it
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    placeholder = {

                        Text(
                            text =
                                "Örn: Denizcilik, Psikoloji, Gastronomi"
                        )
                    },

                    isError =

                        saveAttempted &&
                                customCategory
                                    .isBlank(),

                    supportingText = {

                        if (
                            saveAttempted &&
                            customCategory.isBlank()
                        ) {

                            Text(
                                text =
                                    "Alan boş bırakılamaz."
                            )
                        }
                    },

                    singleLine = true
                )
            }

            // SÜRE

            Text(
                text = "Süre",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )


            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {


                OutlinedTextField(

                    value = hour,

                    onValueChange = {

                        hour = it
                    },

                    modifier =
                        Modifier.weight(1f),

                    label = {

                        Text(
                            text = "Saat"
                        )
                    },

                    singleLine = true
                )


                OutlinedTextField(

                    value = minute,

                    onValueChange = {

                        minute = it
                    },

                    modifier =
                        Modifier.weight(1f),

                    label = {

                        Text(
                            text = "Dakika"
                        )
                    },

                    singleLine = true
                )
            }



            // AÇIKLAMA

            Text(
                text = "Açıklama",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )


            OutlinedTextField(

                value = description,

                onValueChange = {

                    description = it
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),

                placeholder = {

                    Text(
                        text =
                            "Bugün ne yaptığınızı açıklayın"
                    )
                },

                isError =
                    saveAttempted &&
                            description.isBlank(),

                supportingText = {

                    if (
                        saveAttempted &&
                        description.isBlank()
                    ) {

                        Text(
                            text =
                                "Açıklama boş bırakılamaz."
                        )
                    }
                }
            )



            // KULLANILAN ARAÇLAR / TEKNOLOJİLER

            Text(
                text = "Kullanılan Araçlar / Teknolojiler",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )


            OutlinedTextField(

                value = technologies,

                onValueChange = {

                    technologies = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                placeholder = {

                    Text(
                        text =
                            "Örn: UYAP, Excel, SQL Server, CNC"
                    )
                }
            )


            // ÖĞRENİLEN KAVRAMLAR

            Text(
                text = "Öğrenilen Kavramlar / Konular",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )


            OutlinedTextField(

                value = learnedConcepts,

                onValueChange = {

                    learnedConcepts = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                placeholder = {

                    Text(
                        text =
                            "Örn: İcra takibi, DAO, kalite kontrol"
                    )
                }
            )



            // NOTLAR

            Text(
                text = "Notlar / AI Talimatı",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryText
            )


            OutlinedTextField(

                value = notes,

                onValueChange = {

                    notes = it
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),

                placeholder = {

                    Text(
                        text =
                            "Ek not veya rapor talimatı yazın. Örn: 5 paragraf yaz."
                    )
                }
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )



            // KAYDET BUTONU
            Button(

                onClick = {

                    saveActivity()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            MainGreen
                    )
            ) {


                Text(

                    text =
                        if (initialActivity == null) {

                            "Faaliyeti Kaydet"

                        } else {

                            "Değişiklikleri Kaydet"
                        },

                    fontSize = 16.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }

            // İPTAL
            TextButton(

                onClick = onCancelClick,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "İptal",
                    color = PrimaryText
                )
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AddActivityScreenPreview() {

    StajGunlugumTheme {

        AddActivityScreen()
    }
}