

### 使组件左右滑动

以下是一个示例代码，展示如何在 Jetpack Compose 中实现可左右滑动的行：

``` kotlin
Box( 
    Modifier
        .fillMaxWidth()
        .height(50.dp)
) {
    var offsetX by remember { mutableStateOf(0f) }

    // 监听手势事件，更新 offsetX 的值
    val onGesture = Modifier.pointerInput(Unit) {
        detectHorizontalDragGestures { change, _ ->
            offsetX += change.distance
        }
    }

    // 创建可左右滑动的子项
    Row(
        Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .background(Color.White)
            .fillMaxSize()
            .then(onGesture)
    ) {
        // 在此添加子项
        Text(
            "Swipe me left and right",
            Modifier.padding(16.dp)
        )
    }
}
```

该代码创建了一个 `Box` 组合项，其中包含一个可左右滑动的 `Row` 组合项。使用 `pointerInput` 组合项监听手势事件，然后通过 `offset` 组合项将子项根据手势的水平偏移量移动。请注意，这只是一个简单的示例，实际应用中可能需要更复杂的逻辑来实现完整的左右滑动功能。



### 实现todo功能
以下是一个示例代码，展示如何在 Jetpack Compose 中实现可左右滑动的 To-Do 任务列表：

``` kotlin
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
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val onGesture = Modifier.pointerInput(Unit) {
        detectHorizontalDragGestures { change, _ ->
            offsetX += change.distance
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
```

该代码创建了一个 To-Do 任务列表，其中每个任务都是一个可左右滑动的行。使用 `mutableStateListOf` 组合项创建一个可变列表，然后使用 `LazyColumn` 组合项循环渲染每个任务行。每个任务行包含一个 To-Do 任务文本和一个删除按钮，当向左滑动行时，如果滑动距离超过屏幕宽度的一半，则显示删除按钮。

通过传递一个回调函数来处理删除操作，当用户点击删除按钮时，会从列表中删除对应的任务项。