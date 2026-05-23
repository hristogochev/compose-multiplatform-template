package io.github.hristogochev.template.util

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cmptemplate2.shared.generated.resources.Res
import cmptemplate2.shared.generated.resources.*
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageVector
import org.jetbrains.compose.resources.stringResource


fun Modifier.fadingEdge(
    brush: Brush = Brush.verticalGradient(
        0f to Color.Transparent,
        0.01f to Color.Red,
        0.99f to Color.Red,
        1f to Color.Transparent
    ),
) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }


@Composable
fun monthToString(month: Int): String {
    return when (month) {
        1 -> stringResource(Res.string.month_1)
        2 -> stringResource(Res.string.month_2)
        3 -> stringResource(Res.string.month_3)
        4 -> stringResource(Res.string.month_4)
        5 -> stringResource(Res.string.month_5)
        6 -> stringResource(Res.string.month_6)
        7 -> stringResource(Res.string.month_7)
        8 -> stringResource(Res.string.month_8)
        9 -> stringResource(Res.string.month_9)
        10 -> stringResource(Res.string.month_10)
        11 -> stringResource(Res.string.month_11)
        12 -> stringResource(Res.string.month_12)
        else -> stringResource(Res.string.unknown)
    }
}

@Composable
fun byteArrayToImageVector(byteArray: ByteArray?): ImageVector? {

    val density = LocalDensity.current

    return remember(density, byteArray) {
        runCatching {
            byteArray?.decodeToImageVector(density)
        }.getOrNull()
    }
}


@Composable
fun dayOfWeekToString(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> stringResource(Res.string.day_of_week_1)
        DayOfWeek.TUESDAY -> stringResource(Res.string.day_of_week_2)
        DayOfWeek.WEDNESDAY -> stringResource(Res.string.day_of_week_3)
        DayOfWeek.THURSDAY -> stringResource(Res.string.day_of_week_4)
        DayOfWeek.FRIDAY -> stringResource(Res.string.day_of_week_5)
        DayOfWeek.SATURDAY -> stringResource(Res.string.day_of_week_6)
        DayOfWeek.SUNDAY -> stringResource(Res.string.day_of_week_7)
    }
}

@Composable
fun dayOfWeekToStringFull(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> stringResource(Res.string.day_of_week_full_1)
        DayOfWeek.TUESDAY -> stringResource(Res.string.day_of_week_full_2)
        DayOfWeek.WEDNESDAY -> stringResource(Res.string.day_of_week_full_3)
        DayOfWeek.THURSDAY -> stringResource(Res.string.day_of_week_full_4)
        DayOfWeek.FRIDAY -> stringResource(Res.string.day_of_week_full_5)
        DayOfWeek.SATURDAY -> stringResource(Res.string.day_of_week_full_6)
        DayOfWeek.SUNDAY -> stringResource(Res.string.day_of_week_full_7)
    }
}

@Composable
fun PaddingValues.print(): String {
    val top = calculateTopPadding()
    val bottom = calculateBottomPadding()
    val start = calculateStartPadding()
    val end = calculateEndPadding()

    return "TOP: $top, BOTTOM: $bottom, START: $start, END: $end"
}

@Composable

fun PaddingValues.plus(paddingValues: PaddingValues): PaddingValues {
    val top = calculateTopPadding() + paddingValues.calculateTopPadding()
    val bottom = calculateBottomPadding() + paddingValues.calculateBottomPadding()
    val start = calculateStartPadding() + paddingValues.calculateStartPadding()
    val end = calculateEndPadding() + paddingValues.calculateEndPadding()

    return PaddingValues(top = top, bottom = bottom, start = start, end = end)
}

@Composable
fun PaddingValues.calculateStartPadding(): Dp {
    return calculateStartPadding(LocalLayoutDirection.current)
}

fun PaddingValues.calculateStartPadding(layoutDirection: LayoutDirection): Dp {
    return calculateStartPadding(layoutDirection)
}

@Composable
fun PaddingValues.calculateEndPadding(): Dp {
    return calculateEndPadding(LocalLayoutDirection.current)
}

fun PaddingValues.calculateEndPadding(layoutDirection: LayoutDirection): Dp {
    return calculateEndPadding(layoutDirection)
}


@Composable
fun PaddingValues.withoutBottom(): PaddingValues {
    return PaddingValues(
        top = calculateTopPadding(),
        start = calculateStartPadding(),
        end = calculateEndPadding(),
        bottom = 0.dp
    )
}

fun PaddingValues.withoutBottom(layoutDirection: LayoutDirection): PaddingValues {
    return PaddingValues(
        top = calculateTopPadding(),
        start = calculateStartPadding(layoutDirection),
        end = calculateEndPadding(layoutDirection),
        bottom = 0.dp
    )
}

@Composable
fun Modifier.iOSKeyboardCloseable(isKeyboardShown: Boolean): Modifier {
//    if (isAndroidPlatform) return this
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    return this.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = {
            if (isKeyboardShown) {
                focusManager.clearFocus()
            }
        }
    )
}

fun rememberMyRipple(bounded: Boolean, radius: Dp): Indication {
    return ripple(bounded, radius)
}

@Composable
inline fun LazyListState.onFirstIndex(crossinline op: (Int) -> Unit) {
    val state = this
    LaunchedEffect(state) {
        snapshotFlow {
            state.firstVisibleItemIndex
        }.collect { firstIndex ->
            op(firstIndex)
        }
    }
}

@Composable
inline fun LazyListState.onFirstIndexTrue(crossinline op: (Int) -> Unit) {
    val state = this
    LaunchedEffect(state) {
        snapshotFlow {
            val visibleItems = state.layoutInfo.visibleItemsInfo
            visibleItems.getOrNull(0)?.index ?: 0
        }.collect { firstIndex ->
            op(firstIndex)
        }
    }
}

@Composable
fun LazyListState.index0Height(): Dp {
    val state = this
    var index0height by remember { mutableStateOf(0) }

    LaunchedEffect(state) {
        snapshotFlow {
            val visibleItems = state.layoutInfo.visibleItemsInfo
            val firstItem = visibleItems.getOrNull(0)
            if (firstItem != null && firstItem.index == 0) {
                (firstItem.size + firstItem.offset).coerceAtLeast(0)
            } else 0
        }.collect { size ->
            index0height = size
        }
    }

    val density = LocalDensity.current

    return remember(density, index0height) {
        with(density) {
            index0height.toDp()
        }
    }
}

@Composable
inline fun LazyListState.onFirstIndexSticky(offsetY: Int, crossinline op: (Int) -> Unit) {
    val state = this
    val stickyOffsetUpdated by rememberUpdatedState(offsetY)
    LaunchedEffect(state) {
        snapshotFlow {
            val visibleItems = state.layoutInfo.visibleItemsInfo
            val firstItem = visibleItems.getOrNull(0)
            if (firstItem != null && firstItem.index == 1) {
                visibleItems.firstOrNull { item ->
                    if (item.index == 1) return@firstOrNull false
                    (item.offset + item.size > firstItem.size - stickyOffsetUpdated)
                }?.index ?: 2
            } else {
                visibleItems.firstOrNull {
                    it.index != 0 && it.index != 1
                }?.index ?: 2
            }
        }.collect { firstIndex ->
            op(firstIndex)
        }
    }
}
