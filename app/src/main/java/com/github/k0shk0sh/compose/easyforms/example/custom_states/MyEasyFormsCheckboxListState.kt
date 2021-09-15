package com.github.k0shk0sh.compose.easyforms.example.custom_states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.github.k0shk0sh.compose.easyforms.EasyFormsErrorState
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import com.github.k0shk0sh.compose.easyforms.EasyFormsState
import com.github.k0shk0sh.compose.easyforms.example.model.CheckboxModel

class MyEasyFormsCheckboxListState(
    list: List<CheckboxModel>,
    private val isRequired: Boolean = true,
    private val minCheckRequired: Int = 2,
) : EasyFormsState<SnapshotStateList<CheckboxModel>, Pair<String, Boolean>>() {
    init {
        if (isRequired.not() || list.count { it.isSelected } >= minCheckRequired)
            errorState.value = EasyFormsErrorState.VALID
    }

    override val state: SnapshotStateList<CheckboxModel> = mutableStateListOf(*list.toTypedArray())

    override val onValueChangedCallback: (Pair<String, Boolean>) -> Unit = { (key, value) ->
        state[state.indexOfFirst { it.id == key }] =
            state.first { it.id == key }.copy(isSelected = value)
        if (isRequired) {
            errorState.value = when (state.count { it.isSelected } >= minCheckRequired) {
                true -> EasyFormsErrorState.VALID
                false -> EasyFormsErrorState.INVALID
            }
        }
    }

    override fun mapToResult(key: Any): EasyFormsResult {
        return MyEasyFormsCustomCheckboxListResult(
            key = key,
            easyFormsErrorState = errorState.value,
            value = state.filter { it.isSelected }
        )
    }


    @Composable
    fun rememberSaveable(): SnapshotStateList<CheckboxModel> {
        return rememberSaveable(
            saver = listSaver(
                save = { it.toList() },
                restore = { it.toMutableStateList() }
            )
        ) {
            state
        }
    }
}

class MyEasyFormsCustomCheckboxListResult(
    override val key: Any,
    override val easyFormsErrorState: EasyFormsErrorState,
    override val value: List<CheckboxModel>,
) : EasyFormsResult.GenericStateResult<List<CheckboxModel>>(
    key = key,
    easyFormsErrorState = easyFormsErrorState,
    value = value
)
