package page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun timeConversion(modifier: Modifier = Modifier) {

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Text(
            text = "Hello World",
            color = Color.White,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }

}