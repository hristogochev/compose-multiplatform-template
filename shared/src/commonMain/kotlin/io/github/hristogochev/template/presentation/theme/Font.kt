package io.github.hristogochev.template.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import cmptemplate2.shared.generated.resources.Res
import cmptemplate2.shared.generated.resources.*
import org.jetbrains.compose.resources.Font


@Composable
fun fontDefault(): FontFamily {
    return FontFamily(
        Font(Res.font.montserrat_italic_black, FontWeight.W900, FontStyle.Italic),
        Font(Res.font.montserrat_italic_bold, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.montserrat_italic_extrabold, FontWeight.ExtraBold, FontStyle.Italic),
        Font(Res.font.montserrat_italic_extralight, FontWeight.ExtraLight, FontStyle.Italic),
        Font(Res.font.montserrat_italic_light, FontWeight.Light, FontStyle.Italic),
        Font(Res.font.montserrat_italic_medium, FontWeight.Medium, FontStyle.Italic),
        Font(Res.font.montserrat_italic_regular, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.montserrat_italic_semibold, FontWeight.SemiBold, FontStyle.Italic),
        Font(Res.font.montserrat_italic_thin, FontWeight.Thin, FontStyle.Italic),
        Font(Res.font.montserrat_normal_black, FontWeight.W900, FontStyle.Normal),
        Font(Res.font.montserrat_normal_bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.montserrat_normal_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
        Font(Res.font.montserrat_normal_extralight, FontWeight.ExtraLight, FontStyle.Normal),
        Font(Res.font.montserrat_normal_light, FontWeight.Light, FontStyle.Normal),
        Font(Res.font.montserrat_normal_medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.montserrat_normal_regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.montserrat_normal_semibold, FontWeight.SemiBold, FontStyle.Normal),
        Font(Res.font.montserrat_normal_thin, FontWeight.Thin, FontStyle.Normal),
    )
}