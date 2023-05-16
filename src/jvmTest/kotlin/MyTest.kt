import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lt.load_the_image.LoadTheImageManager
import com.lt.load_the_image.rememberImagePainter
import java.sql.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
// 测试图片
@Composable
@Preview
fun App() {


    MaterialTheme {



        Box {

            val videoPlayerState = rememberVideoPlayerState(
                time = 0L,
                isPlaying = false,
            )
            val time by videoPlayerState.time.collectAsState()
            val isPlaying by videoPlayerState.isPlaying.collectAsState()
            val length by videoPlayerState.length.collectAsState()

            VideoPlayer(
//                mrl = "G:\\seaplane.mp4",
//                mrl = "rtsp://127.0.0.1:8554/golang",
                mrl = "http://vd3.bdstatic.com/mda-na1dn827jgeuh7yq/cae_h264/1641116884533920115/mda-na1dn827jgeuh7yq.mp4",
                state = videoPlayerState,
            )

            Column(
                modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            videoPlayerState.seekTo(videoPlayerState.time.value.time - 5000)
                        }) {

                        Text("Backward")
                    }
                    Button(
                        onClick = {
                            // show =  !show
                            if(isPlaying) {
                                videoPlayerState.pause()
                            } else {
                                videoPlayerState.play()
                            }
                        }) {

                        Text(if(isPlaying) "Pause" else "Play")
                    }
                    Button(
                        onClick = {
                            videoPlayerState.seekTo(videoPlayerState.time.value.time + 5000)
                        }) {

                        Text("Forward")
                    }
                }

                Slider(
                    value = time.time/length.length.toFloat(),
                    onValueChange = {videoPlayerState.seekTo((it*length.length).toLong())},
                    modifier= Modifier.fillMaxWidth()
                )
            }


        }

    }
}

fun main() = application {
    Window(
        title = "Video Test",
        onCloseRequest = ::exitApplication,
    ) {
//        com.sun.jna.Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc::class.java)
//        NativeLibrary.addSearchPath(
//            RuntimeUtil.getLibVlcLibraryName(),
//            "C:/Program Files/VideoLAN/VLC/"
//        )
//        com.sun.jna.Native.loadLibrary(
//            RuntimeUtil.getLibVlcLibraryName(),
//            LibVlc::class.java
//        )

//        println(LibVlc.libvlc_get_version())

//        App()

        TodoList()
    }
}


data class TodoItem(val id: Int, val text: String)

@Composable
fun TodoList() {
    val todoItems = remember { mutableStateListOf(
        TodoItem(1, "Buy milk"),
        TodoItem(2, "Do laundry"),
        TodoItem(3, "Call mom"),
    )}

    LazyColumn {
        items(todoItems) { todoItem ->
            TodoItemRow(todoItem) { id ->
                todoItems.removeIf { it.id == id }
            }
        }
    }
}

@Composable
fun TodoItemRow(todoItem: TodoItem, onDelete: (Int) -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    val screenWidth = 50
    val onGesture = Modifier.pointerInput(Unit) {
        detectHorizontalDragGestures { change, a ->
            offsetX += change.position.x
        }
    }

    Row(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .fillMaxWidth()
            .then(onGesture)
    ) {
        Text(todoItem.text, Modifier.padding(16.dp))

        if (offsetX.absoluteValue > screenWidth / 2) {
            IconButton(onClick = { onDelete(todoItem.id) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}



fun aa() {
    LoadTheImageManager.defaultErrorImagePath = "drawable-xxhdpi/load_error.jpeg"//this
    application {
        Window(onCloseRequest = ::exitApplication) {
            MaterialTheme {
                Image(rememberImagePainter("https://img.zcool.cn/community/017e625e57415ea801216518c25819.jpg@1280w_1l_2o_100sh.jpg"),"")
            }
        }
    }
}

class MyTest {








    fun aa() {
        lateinit var connection: Connection
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:mydesktop.db")
            val statement: Statement = connection.createStatement()
            statement.setQueryTimeout(30) // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists person")
            statement.executeUpdate("create table person (id integer, name string)")
            statement.executeUpdate("insert into person values(1, 'hqq')")
            statement.executeUpdate("insert into person values(2, 'ch')")
            val rs: ResultSet = statement.executeQuery("select * from person")
            while (rs.next()) {
                println("name = " + rs.getString("name"))
                println("id = " + rs.getInt("id"))
            }
        } catch (e: SQLException) {
            System.err.println(e.message)
        } finally {
            try {
                connection.close()
            } catch (e: SQLException) {
                System.err.println(e.message)
            }
        }
    }

}