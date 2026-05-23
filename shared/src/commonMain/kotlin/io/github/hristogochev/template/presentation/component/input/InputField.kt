package io.github.hristogochev.template.presentation.component.input

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cmptemplate2.shared.generated.resources.*
import io.github.hristogochev.template.presentation.component.common.CommonIcon
import io.github.hristogochev.template.presentation.component.common.CommonIconButton
import io.github.hristogochev.template.presentation.component.common.CommonOutlinedTextField
import io.github.hristogochev.template.presentation.component.common.CommonText
import io.github.hristogochev.template.presentation.component.common.ErrorText
import io.github.hristogochev.template.util.PainterChoice
import org.jetbrains.compose.resources.painterResource

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    setKeyboardShown: (Boolean) -> Unit,
    leadingPainter: PainterChoice? = null,
    trailingPainter: PainterChoice? = null,
    error: String?,
    focusable: Boolean = true,
    readOnly: Boolean = false,
    readOnlyStyles: Boolean = readOnly,
    clickable: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textModifier: Modifier = Modifier,
    displayHeader: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = if (singleLine) 1 else 4,
    containerColorNotReadOnly: Color = MaterialTheme.colorScheme.surfaceBright,
    displayInfoIcon: Boolean = false,
    onInfoIconClick: () -> Unit = {},
) {
    val containerColor: Color = if (readOnly && readOnlyStyles) {
        MaterialTheme.colorScheme.surfaceBright.copy(alpha = 0.4f)
    } else {
        containerColorNotReadOnly
    }


    val leadingPainterExists = leadingPainter != null

    val trailingPainterExists = trailingPainter != null

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (displayHeader) {
            Row(
                modifier = Modifier.focusable(false),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                CommonText(
                    modifier = Modifier.focusable(false),
                    text = text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    maxLines = Int.MAX_VALUE
                )
                if (displayInfoIcon) {
                    CommonIconButton(onClick = onInfoIconClick) {
                        Icon(
                            painter = painterResource(Res.drawable.info_icon),
                            contentDescription = text,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
        }

        Box {
            if (leadingPainter != null) {
                Box(
                    modifier = Modifier.zIndex(1f).width(36.dp)
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    CommonIcon(
                        modifier = Modifier.size(20.dp),
                        painter = leadingPainter,
                        contentDescription = null,
                        tint = leadingPainter.color.let {
                            if (readOnly && readOnlyStyles) it.copy(alpha = 0.6f) else it
                        }
                    )
                }
            }
            if (trailingPainter != null) {
                Box(
                    modifier = Modifier.zIndex(1f).size(36.dp)
                        .align(Alignment.CenterEnd),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CommonIcon(
                        modifier = Modifier.size(20.dp).alpha(0.2f),
                        painter = trailingPainter,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            CommonOutlinedTextField(
                modifier = textModifier.zIndex(0f).fillMaxWidth().onFocusChanged {
                    setKeyboardShown(it.isFocused)
                }.let { if (!focusable) it.focusable(false) else it },
                value = value,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.Normal,
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    color = MaterialTheme.colorScheme.onBackground.let {
                        if (readOnly && readOnlyStyles) it.copy(alpha = 0.4f) else it
                    },
                    fontSize = 16.sp,
                    lineHeight = 20.8.sp,
                    letterSpacing = 0.sp
                ),
                enabled = enabled,
                readOnly = readOnly,
                placeholder = {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Light,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        fontSize = 16.sp,
                        lineHeight = 20.8.sp,
                        letterSpacing = 0.sp,
                        maxLines = 1
                    )
                },
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = containerColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = containerColor,
                    focusedBorderColor = Color.Transparent,
                    disabledContainerColor = containerColor,
                    disabledBorderColor = Color.Transparent,
                    errorContainerColor = containerColor,
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                ),
                isError = error != null,
                leadingIcon = if (leadingPainter != null) {
                    {}
                } else {
                    null
                },
                trailingIcon = if (trailingPainter != null) {
                    {}
                } else {
                    null
                },
                singleLine = singleLine,
                minLines = minLines,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation,
                contentPadding = PaddingValues(
                    start = if (leadingPainterExists) 12.dp else 16.dp,
                    top = 16.dp,
                    bottom = 16.dp,
                    end = if (trailingPainterExists) 12.dp else 16.dp
                )
            )
        }
        if (error != null) {
            ErrorText(error)
        }
    }
}