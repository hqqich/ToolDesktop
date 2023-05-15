package theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import util.Grey700
import util.Grey800
import util.Grey900

//为外部属性，不是StudyTheme中的临时变量
private val LightColors = lightColors(
    primary = Grey700,
    primaryVariant = Grey900,
    onPrimary = Color.White,
    secondary = Grey700,
    secondaryVariant = Grey900,
    onSecondary = Color.White,
    error = Grey800
)


/**
 * 自己的主题 <br>
 * 自己定义了一套颜色规范 <br>
 * 当 下面的组件用到了  MaterialTheme.colors.primaryVariant  ，将变为  Grey700 = Color(0xffdd0d3c)
 */
@Composable
fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content, colors = LightColors)
}