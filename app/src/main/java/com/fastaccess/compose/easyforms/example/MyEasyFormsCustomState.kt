package com.fastaccess.compose.easyforms.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.fastaccess.compose.easyforms.EasyFormsErrorState
import com.fastaccess.compose.easyforms.EasyFormsResult
import com.fastaccess.compose.easyforms.EasyFormsState

class MyEasyFormsCustomState(
    defaultValue: String = "",
    private val validData: List<String>,
) : EasyFormsState<String>() {

    private val isOpen = mutableStateOf(false)

    val onDismissed: () -> Unit = {
        isOpen.value = false
    }

    val onClick: () -> Unit = {
        isOpen.value = true
    }

    override val state: MutableState<String> = mutableStateOf(defaultValue)

    override val onValueChangedCallback: (String) -> Unit = {
        state.value = it
        isOpen.value = false
        errorState.value = when (it in validData) {
            true -> EasyFormsErrorState.VALID
            false -> EasyFormsErrorState.INVALID
        }
    }

    override fun mapToResult(name: Any): EasyFormsResult {
        return MyEasyFormsCustomResult(
            name = name,
            easyFormsErrorState = errorState.value,
            value = state.value,
        )
    }

    @Composable
    fun rememberSaveable(): MutableState<String> {
        return androidx.compose.runtime.saveable.rememberSaveable {
            state
        }
    }

    @Composable
    fun rememberOpen(): MutableState<Boolean> {
        return remember { isOpen }
    }
}

data class MyEasyFormsCustomResult(
    override val name: Any,
    override val easyFormsErrorState: EasyFormsErrorState,
    override val value: String,
) : EasyFormsResult.GenericStateResult<String>(
    name = name,
    easyFormsErrorState = easyFormsErrorState,
    value = value,
)