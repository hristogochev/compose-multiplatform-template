package io.github.hristogochev.template.presentation.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.hristogochev.template.presentation.component.common.CommonText
import io.github.hristogochev.template.presentation.component.common.ErrorText

@Composable
fun OTPInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    setKeyboardShown: (Boolean) -> Unit,
    error: String?,
    focusRequester: FocusRequester,
    letters: Int = 4,
    readOnly: Boolean = false,
    readOnlyStyles: Boolean = readOnly,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            modifier = Modifier.alpha(if (error != null) 0.4f else 1f)
                .focusRequester(focusRequester).onFocusChanged {
                    setKeyboardShown(it.isFocused)
                }.padding(top = 12.dp),
            value = TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,

            onValueChange = {
                if (it.text.length <= letters) {
                    onValueChange(it.text)
                }
            }) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(letters) { index ->
                    val number = when {
                        index >= value.length -> ""
                        else -> value[index]
                    }.toString()

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier.size(60.dp).background(
                                MaterialTheme.colorScheme.surfaceBright,
                                shape = RoundedCornerShape(10.dp)
                            ),
                            contentAlignment = Alignment.Center
                        ) {
                            CommonText(
                                text = number,
                                fontSize = 18.sp,
                                fontWeight = if (!readOnly || readOnlyStyles) FontWeight.Normal else FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
        if (error != null) {
            ErrorText(error)
        }
    }
}