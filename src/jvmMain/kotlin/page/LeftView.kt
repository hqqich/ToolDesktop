package page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import basecompose.Screens




@Composable
@Preview
fun left(modifier: Modifier = Modifier, currentScreen: MutableState<Screens>) {

    Column(modifier = modifier) {

        basecompose.screens.forEach { screen ->
            Button(onClick = {
                currentScreen.value = screen
            },
                modifier = Modifier.height(50.dp).fillMaxWidth()) {
                Text("${screen.title}")
            }
        }

    }

}