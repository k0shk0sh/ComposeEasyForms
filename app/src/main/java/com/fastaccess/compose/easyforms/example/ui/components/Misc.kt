package com.fastaccess.compose.easyforms.example.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SmallText(text: String) {
    Text(text = text, style = MaterialTheme.typography.overline)
}

@Composable
fun Space(
    padding: Dp = 16.dp,
) {
    Spacer(modifier = Modifier.padding(padding))
}