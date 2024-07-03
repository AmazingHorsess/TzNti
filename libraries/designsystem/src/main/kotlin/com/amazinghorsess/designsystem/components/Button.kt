package com.amazinghorsess.designsystem.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NtiButton(
    modifier: Modifier = Modifier,
    color: Color,
    onClick:() -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(100.dp)
            .width(150.dp),
        colors = ButtonDefaults.buttonColors(color)
    ){
        Text(text)
    }

}