package com.github.k0shk0sh.compose.easyforms.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.github.k0shk0sh.compose.easyforms.EasyForms
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyEasyFormsCustomStringState
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyFormKeys

@Composable
fun Salutation(
    easyForm: EasyForms,
) {
    val list = listOf("Mr", "Ms", "Other")
    val state = easyForm.addAndGetCustomState(MyFormKeys.SALUTATION, MyEasyFormsCustomStringState(
        validData = list
    ))
    val text = state.rememberSaveable()
    val isOpen = state.rememberOpen()
    Box {
        Column {
            TextField(
                value = text.value,
                onValueChange = {},
                label = { Text(text = "Salutation") },
                placeholder = { Text(text = "Salutation") },
                modifier = Modifier.fillMaxWidth(),
            )
            DropDownList(
                state = state,
                requestToOpen = isOpen.value,
                list = list,
            )
        }
        Box(modifier = Modifier
            .matchParentSize()
            .alpha(0f)
            .clickable(onClick = state.onClick))
    }
}

@Composable
private fun DropDownList(
    state: MyEasyFormsCustomStringState,
    requestToOpen: Boolean = false,
    list: List<String>,
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        expanded = requestToOpen,
        onDismissRequest = state.onDismissed,
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = { state.onValueChangedCallback(it) }
            ) {
                Text(
                    text = it,
                    modifier = Modifier.wrapContentWidth(),
                )
            }
        }
    }
}