package basecompose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kotlinx.coroutines.launch
import server.HttpDataServer
import com.lt.load_the_image.rememberImagePainter
import page.VideoPlayer
import page.rememberVideoPlayerState

//class MyDataSource(
//    private val repo: MyRepository
//) : PagingSource<Int, MyData>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyData> {
//        return try {
//            val nextPage = params.key ?: 1
//            val response = repo.fetchData(nextPage)
//
//            LoadResult.Page(
//                data = response.results,
//                prevKey = if (nextPage == 1) null else nextPage - 1,
//                nextKey = repo.page.plus(1)
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}
//
//class MainViewModel(
//    repo: MyRepository
//) : ViewModel() {
//
//    val pagingData: Flow<PagingData<MyData>> = Pager(PagingConfig(pageSize = 20)) {
//        MyDataSource(repo)
//    }.flow
//}
//
//
//
//@Composable
//fun MyList(pagingData: Flow<PagingData<MyData>>) {
//    val listItems: LazyPagingItems<MyData> = pagingData.collectAsLazyPagingItems()
//
//    LazyColumn {
//        items(listItems) {
//            ItemCard(data = it)
//        }
//    }
//}

@Composable
@Preview
fun Test() {

    val list = (0..1_000).map { "Item $it" }.toList()

    val state = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.padding(5.dp).fillMaxWidth(),
        // 靠右对齐
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        reverseLayout = false,
        state = state

    ) {
        items(list) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(10.dp))
                Image(
                    painter = painterResource("images/a.jpg"),
                    contentDescription = it,
                    modifier = Modifier.width(60.dp).height(60.dp).clip(shape = CircleShape)
                        .background(Color.Black)
                )
                Text(text = it)
            }
        }
    }


}


@Composable
@Preview
fun Test2() {


    Column() {
        val scope = rememberCoroutineScope()
        var demoReqData by remember { mutableStateOf(JsonArray()) }

        scope.launch {
            try {
                demoReqData = HttpDataServer().getVideoData()
            } catch (_: Exception) {
            }
        }

        LazyColumn {
            repeat(demoReqData.size()) {
                item {
                    ItemMessage(demoReqData.get(it))
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemMessage(data: JsonElement) {

    val scrollState = rememberScrollState()


    var openDialog by remember {
        mutableStateOf(false)
    }
    var isClose by remember {
        mutableStateOf(false)
    }


    if (openDialog) {
        Dialog(onCloseRequest = {
            openDialog = false
        }, title = "视频播放") {
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
                    mrl = data.asJsonObject.get("playUrl").asString,
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


    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .fillMaxWidth(), elevation = 10.dp,
        onClick = {
            println("${data.asJsonObject.get("playUrl").asString}")

            openDialog = true

        }
    ) {
        Row(
            modifier = Modifier
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Horizontal,
                )
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(10.dp))
            Image(
                painter = rememberImagePainter(data.asJsonObject.get("userPic").asString),
                contentDescription = "",
                modifier = Modifier.width(60.dp).height(60.dp).clip(shape = CircleShape)
                    .background(Color.Black)
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "作者：${data.asJsonObject.get("userName").asString}"
                )
                Text(text = "${data.asJsonObject.get("title").asString}")
            }
        }

    }

//    val maxScroll = with(LocalDensity.current) { scrollState.maxValue.toDp() }
//    val curPos = scrollState.value
//    val scrollController = rememberScrollableController { delta ->
//        val newPos = (curPos - delta).coerceIn(0f, maxScroll.value)
//        scrollState.animateScrollTo(newPos.toInt())
//    }
//    DisposableEffect(scrollController) {
//        scrollController.scrollBy(0.5f)
//        onDispose { }
//    }

}

@Composable
fun MyScrollableRow(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
//    val scrollState = rememberScrollState()
//    Row(
//        modifier = modifier
//            .scrollable(
//                orientation = Orientation.Horizontal,
//                scrollState = scrollState
//            )
//            .horizontalScroll(scrollState),
//        content = content
//    )
//
//    val maxScroll = with(LocalDensity.current) { scrollState.maxValue.toDp() }
//    val curPos = scrollState.value
//    val scrollController = rememberScrollableController { delta ->
//        val newPos = (curPos - delta).coerceIn(0f, maxScroll.value)
//        scrollState.animateScrollTo(newPos.toInt())
//    }
//    DisposableEffect(scrollController) {
//        scrollController.scrollBy(0.5f)
//        onDispose { }
//    }
}
