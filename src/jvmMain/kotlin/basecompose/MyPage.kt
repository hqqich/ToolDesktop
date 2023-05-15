package basecompose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


// 参考文档 https://blog.csdn.net/userhu2012/article/details/127482137
// 参考文档 https://www.geeksforgeeks.org/bottom-navigation-bar-in-android-jetpack-compose/
// 参考文档 https://amryousef.me/side-drawer-jetpack-compose


/**
 * Main screen
 * 定义主界面的脚手架框架
 */
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    //脚手架的状态
    val scaffoldState = rememberScaffoldState()
    //当前显示的界面
    val currentScreen: MutableState<Screens> = remember { mutableStateOf(Screens.HomePage) }
    //当前的上下文
    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopBarView(currentScreen, scaffoldState)
        },
        drawerContent = {
            HeaderBarView()
            DrawerViews(scaffoldState) { screen ->
                currentScreen.value = screen
            }
        },
        content = {
            currentScreen.value.screenToLoad()
        },
        bottomBar = {
            BottomViews(currentScreen = currentScreen)
        },
        floatingActionButton = {
//            FloatingActionButton(
//                backgroundColor = Color.Red,
//                onClick = {
////                    Toast.makeText(context,"返回首页",Toast.LENGTH_LONG).show()
//                    currentScreen.value = Screens.HomePage
//                    scope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar("go home")
//                    }
//                }) {
//                Icon(Icons.Filled.Home, contentDescription = "返回首页")
//            }
        })
}


/**
 * 定义应用头部内容
 * 在头部定义一个导航菜单
 * @param scaffoldState ScaffoldState 脚手架的状态
 */
@Composable
fun TopBarView(currentScreen: MutableState<Screens>, scaffoldState: ScaffoldState) {
    //协程的范围
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text("${currentScreen.value.title}", fontSize = 20.sp)
        },
        // 呼出隐藏栏
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = {
                println("a")
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = Color.Black,
        contentColor = Color.White
    )
}


/**
 * 定义应用底部的视图
 *接收从外部传递的要显示的当前界面
 */
@Composable
fun BottomViews(currentScreen: MutableState<Screens>) {
    BottomAppBar(
        backgroundColor = Color.Blue,
        contentColor = Color.Yellow
    ) {
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = "${screen.title}")
                },
                label = {
                    Text("${screen.title}")
                },
                selected = screen.route == currentScreen.value.route,
                onClick = {
                    currentScreen.value = screen
                }
            )


        }
    }
}


/** 定义首页界面*/
@Preview
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(color = MaterialTheme.colors.primaryVariant),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
//        Test()
        Test2()
        }
}



/**定义配置界面*/
@Preview
@Composable
fun SettingScreen() {
    Column(
        content = {
            Text(
                "配置界面",
                fontSize = 36.sp,
                color = Color.White
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primaryVariant),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    )
}

/**定义帮助界面*/
@Preview
@Composable
fun HelpScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colors.primaryVariant),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("帮助界面", color = Color.White, fontSize = 36.sp)
    }
}


@Composable
fun DisplayText(content: String) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colors.primaryVariant),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("$content", color = Color.White, fontSize = 36.sp)
    }
}

//
///** 定义首页界面*/
//@Preview
//@Composable
//fun HomeScreen(){
//    DisplayText("首页界面")
//}
//
///**定义配置界面*/
//@Preview
//@Composable
//fun SettingScreen(){
//    DisplayText("配置界面")
//}
//
///**定义帮助界面*/
//@Preview
//@Composable
//fun HelpScreen(){
//    DisplayText("帮助界面")
//}


/**
 * 定义要切换界面的密封类
 * @property route String 导航线路
 * @property title String  标题
 * @property icon ImageVector 图标
 * @property screenToLoad [@androidx.compose.runtime.Composable] Function0<Unit> 加载动作处理
 * @constructor
 */
sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val screenToLoad: @Composable () -> Unit
) {
    object HomePage : Screens("home", "首页界面", Icons.Filled.Home, {
        HomeScreen()
    })

    object SettingPage : Screens("setting", "配置界面", Icons.Filled.Settings, {
        SettingScreen()
    })

    object HelpPage : Screens("help", "帮助界面", Icons.Filled.Info, {
        HelpScreen()
    })
}

val screens = listOf<Screens>(Screens.HomePage, Screens.SettingPage, Screens.HelpPage)


/**
 * Top bar view
 * 定义头部
 */
@Preview
@Composable
fun HeaderBarView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colors.primaryVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column {
                Image(
                    painter = painterResource("images/a.jpg"),
                    contentDescription = "logo图标",
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Black)
                )
            }//end Column
            Column {
                Text("hqqich")
                Text("hqqich@mail.com")
            }
        }//end Column
    }
}


/**
 * 定义侧滑的下面的菜单
 * @param scaffoldState ScaffoldState 脚手架的状态
 * @param action Function1<[@kotlin.ParameterName] Screens, Unit>? 要处理的切换动作，默认为空
 */
@Composable
fun DrawerViews(
    scaffoldState: ScaffoldState,
    action: ((screen: Screens) -> Unit)? = null
) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        screens.forEach { screen ->
            val clickable = remember { mutableStateOf(false) }
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(onClick = {
                    action?.invoke(screen)
                    clickable.value = !clickable.value
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }) {
                    Icon(imageVector = screen.icon, "${screen.title}")
                }
                Text(
                    "${screen.title}", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    color = if (clickable.value) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}

