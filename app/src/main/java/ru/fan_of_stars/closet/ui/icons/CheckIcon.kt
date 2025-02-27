package ru.fan_of_stars.closet.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CheckIC: ImageVector
    get() {
        if (_CheckIC != null) {
            return _CheckIC!!
        }
        _CheckIC = ImageVector.Builder(
            name = "Check24Dp1F1F1FFILL0Wght400GRAD0Opsz24",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(382f, 720f)
                lineTo(154f, 492f)
                lineToRelative(57f, -57f)
                lineToRelative(171f, 171f)
                lineToRelative(367f, -367f)
                lineToRelative(57f, 57f)
                lineToRelative(-424f, 424f)
                close()
            }
        }.build()

        return _CheckIC!!
    }

@Suppress("ObjectPropertyName")
private var _CheckIC: ImageVector? = null
