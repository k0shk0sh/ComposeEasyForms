package com.github.k0shk0sh.compose.easyforms.example

import androidx.lifecycle.ViewModel
import com.github.k0shk0sh.compose.easyforms.EasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyEasyFormsCustomCheckboxListResult
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyEasyFormsCustomStringResult
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyFormKeys

class MainViewModel : ViewModel() {

    fun onButtonClicked(easyForms: EasyForms) {
        val formData = easyForms.formData()
        formData.forEach {
            when (val result = it) {
                is EasyFormsResult.BooleanResult -> handleBooleanResult(result)
                is EasyFormsResult.GenericStateResult<*> -> handleGeneric(result)
                is EasyFormsResult.RangeSliderStateResult -> handleRangeSlider(result)
                is EasyFormsResult.SliderStateResult -> handleSlider(result)
                is EasyFormsResult.StringResult -> handleString(result)
                is EasyFormsResult.ToggleableStateResult -> handleToggleable(result)
            }
        }
    }

    private fun handleToggleable(result: EasyFormsResult.ToggleableStateResult) {
        when (result.key as MyFormKeys) {
            MyFormKeys.TRI_CHECKBOX -> Unit
            else -> throw IllegalArgumentException("${result.key} is not handled")
        }
    }

    private fun handleString(result: EasyFormsResult.StringResult) {
        when (result.key as MyFormKeys) {
            MyFormKeys.EMAIL -> Unit
            MyFormKeys.PASSWORD -> Unit
            MyFormKeys.NAME -> Unit
            MyFormKeys.URL -> Unit
            MyFormKeys.PHONE -> Unit
            MyFormKeys.CARD -> Unit
            else -> throw IllegalArgumentException("${result.key} is not handled")
        }
    }

    private fun handleRangeSlider(result: EasyFormsResult.RangeSliderStateResult) {
        when (result.key as MyFormKeys) {
            MyFormKeys.RANGE_SLIDER -> Unit
            else -> throw IllegalArgumentException("${result.key} is not handled")
        }
    }

    private fun handleSlider(result: EasyFormsResult.SliderStateResult) {
        when (result.key as MyFormKeys) {
            MyFormKeys.SLIDER -> Unit
            else -> throw IllegalArgumentException("${result.key} is not handled")
        }
    }

    private fun handleBooleanResult(result: EasyFormsResult.BooleanResult) {
        when (result.key as MyFormKeys) {
            MyFormKeys.CHECKBOX -> Unit
            MyFormKeys.RADIO_BUTTON -> Unit
            MyFormKeys.SWITCH -> Unit
            else -> throw IllegalArgumentException("${result.key} is not handled")
        }
    }

    private fun handleGeneric(result: EasyFormsResult.GenericStateResult<*>) {
        when (result) {
            is MyEasyFormsCustomStringResult -> when (result.key as MyFormKeys) {
                MyFormKeys.SALUTATION -> Unit
                else -> throw IllegalArgumentException("${result.key} is not handled")
            }
            is MyEasyFormsCustomCheckboxListResult -> Unit
            else -> Unit //handle other custom results
        }
    }
}