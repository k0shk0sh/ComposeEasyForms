package com.github.k0shk0sh.compose.easyforms.example.custom_states

import androidx.compose.runtime.*
import com.github.k0shk0sh.compose.easyforms.EasyFormsErrorState
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import com.github.k0shk0sh.compose.easyforms.EasyFormsState

class MyEasyFormsCustomStringState(
    defaultValue: String = "",
    private val validData: List<String>,
) : EasyFormsState<MutableState<String>, String>() {

    private val _isOpen = mutableStateOf(false)

    val onDismissed: () -> Unit = {
        _isOpen.value = false
    }

    val onClick: () -> Unit = {
        _isOpen.value = true
    }

    override val state: MutableState<String> = mutableStateOf(defaultValue)

    override val onValueChangedCallback: (String) -> Unit = {
        state.value = it
        _isOpen.value = false
        errorState.value = when (it in validData) {
            true -> EasyFormsErrorState.VALID
            false -> EasyFormsErrorState.INVALID
        }
    }

    override fun mapToResult(key: Any): EasyFormsResult {
        return MyEasyFormsCustomStringResult(
            key = key,
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
    fun rememberOpen(): State<Boolean> {
        return remember { _isOpen }
    }
}

data class MyEasyFormsCustomStringResult(
    override val key: Any,
    override val easyFormsErrorState: EasyFormsErrorState,
    override val value: String,
) : EasyFormsResult.GenericStateResult<String>(
    key = key,
    easyFormsErrorState = easyFormsErrorState,
    value = value,
)