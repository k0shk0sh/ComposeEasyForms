package com.github.k0shk0sh.compose.easyforms.example.custom_states

import android.os.Bundle
import androidx.compose.runtime.*
import com.github.k0shk0sh.compose.easyforms.EasyFormsErrorState
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import com.github.k0shk0sh.compose.easyforms.EasyFormsState

class MyEasyFormsCustomStringState(
    private val defaultValue: String = "",
    private val validData: List<String>,
) : EasyFormsState<MutableState<String>, String>() {

    private val _isOpen = mutableStateOf(false)
    val isOpen: State<Boolean> = _isOpen

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

    override fun saveState(bundle: Bundle) {
        super.saveState(bundle)
        bundle.putString("value", state.value)
        bundle.putBoolean("open", _isOpen.value)
    }

    override fun restoreState(bundle: Bundle) {
        super.restoreState(bundle)
        state.value = bundle.getString("value", defaultValue)
        _isOpen.value = bundle.getBoolean("open", false)
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