package ru.stogram.android.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import ru.stogram.android.R

@ExperimentalTextApi
internal fun createSingleGoogleFontFamily(
    name: String,
    provider: GoogleFont.Provider = GmsFontProvider,
    weights: List<FontWeight>,
): FontFamily = FontFamily(
    weights.map { weight ->
        Font(
            googleFont = GoogleFont(name),
            fontProvider = provider,
            weight = weight,
        )
    }
)

@ExperimentalTextApi
internal val GmsFontProvider: GoogleFont.Provider by lazy {
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}

@ExperimentalTextApi
val StogramTypography = Typography(
    defaultFontFamily = createSingleGoogleFontFamily(
        name = "Inter",
        weights = listOf(
            FontWeight.Light,
            FontWeight.Normal,
            FontWeight.Medium,
            FontWeight.Bold,
        ),
    )
)

val MontserratFamily = FontFamily(
    androidx.compose.ui.text.font.Font(
        resId = R.font.montserrat_regular,
        weight = FontWeight.Normal
    ),
    androidx.compose.ui.text.font.Font(
        resId = R.font.montserrat_bold,
        weight = FontWeight.Bold
    ),
    androidx.compose.ui.text.font.Font(
        resId = R.font.montserrat_medium,
        weight = FontWeight.Medium
    ),
    androidx.compose.ui.text.font.Font(
        resId = R.font.montserrat_italic,
        weight = FontWeight.Normal, style = FontStyle.Italic
    ),
    androidx.compose.ui.text.font.Font(
        resId = R.font.montserrat_semibold,
        weight = FontWeight.SemiBold
    ),
)

val MontserratTypography = Typography(
    defaultFontFamily = MontserratFamily
)
