import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import basecompose.Screens
import page.left
import page.right
import theme.MyTheme

@Composable
@Preview
fun App() {



    MyTheme {

        //当前显示的界面
        val currentScreen: MutableState<Screens> = remember { mutableStateOf(Screens.HomePage) }


        Row(
            modifier = Modifier
                .size(width = 1000.dp, height = 800.dp)
                .background(Color.White),
            verticalAlignment = Alignment.Top, // 垂直居中
            horizontalArrangement = Arrangement.SpaceBetween // 水平方向: 前后没有空隙，且子view之间均匀分散
        )
        {

            // 左边占比1
            left(modifier = Modifier.weight(1f/8f), currentScreen)
            // 右边占比7
            right(modifier = Modifier.weight(7f/8f)) {
                currentScreen.value.screenToLoad()
            }

        }

    }
}




fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
//        MyTheme {
//            MainScreen()
//        }
    }
}
