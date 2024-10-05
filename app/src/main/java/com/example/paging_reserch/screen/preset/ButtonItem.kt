package com.example.paging_reserch.screen.preset

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class ButtonItem(
    val text: String = "",
    val onClick: () -> Unit = {}
)

class ButtonItemParameterProvider : PreviewParameterProvider<ButtonItem> {
    override val values = sequenceOf(
        ButtonItem("Test")
    )
}

@Composable
@Preview
fun ButtonComposable(
    @PreviewParameter(ButtonItemParameterProvider::class) item: ButtonItem
) {
    Button(item.onClick) { Text(item.text) }
}