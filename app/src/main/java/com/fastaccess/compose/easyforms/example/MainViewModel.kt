package com.fastaccess.compose.easyforms.example

import androidx.lifecycle.ViewModel
import com.fastaccess.compose.easyforms.EasyForms
import com.fastaccess.compose.easyforms.EasyFormsResult
import com.fastaccess.compose.easyforms.example.custom_states.MyEasyFormsCustomCheckboxListResult
import com.fastaccess.compose.easyforms.example.custom_states.MyEasyFormsCustomStringResult
import com.fastaccess.compose.easyforms.example.custom_states.MyFormKeys

class MainViewModel : ViewModel() {
    val easyForms = EasyForms()

    fun onButtonClicked() {
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
        when (result.name as MyFormKeys) {
            MyFormKeys.TRI_CHECKBOX -> TODO()
            else -> throw IllegalArgumentException("${result.name} is not handled")
        }
    }

    private fun handleString(result: EasyFormsResult.StringResult) {
        when (result.name as MyFormKeys) {
            MyFormKeys.EMAIL -> TODO()
            MyFormKeys.PASSWORD -> TODO()
            MyFormKeys.NAME -> TODO()
            MyFormKeys.URL -> TODO()
            MyFormKeys.PHONE -> TODO()
            MyFormKeys.CARD -> TODO()
            else -> throw IllegalArgumentException("${result.name} is not handled")
        }
    }

    private fun handleRangeSlider(result: EasyFormsResult.RangeSliderStateResult) {
        when (result.name as MyFormKeys) {
            MyFormKeys.RANGE_SLIDER -> TODO()
            else -> throw IllegalArgumentException("${result.name} is not handled")
        }
    }

    private fun handleSlider(result: EasyFormsResult.SliderStateResult) {
        when (result.name as MyFormKeys) {
            MyFormKeys.SLIDER -> TODO()
            else -> throw IllegalArgumentException("${result.name} is not handled")
        }
    }

    private fun handleBooleanResult(result: EasyFormsResult.BooleanResult) {
        when (result.name as MyFormKeys) {
            MyFormKeys.CHECKBOX -> TODO()
            MyFormKeys.RADIO_BUTTON -> TODO()
            MyFormKeys.SWITCH -> TODO()
            else -> throw IllegalArgumentException("${result.name} is not handled")
        }
    }

    private fun handleGeneric(result: EasyFormsResult.GenericStateResult<*>) {
        when (result) {
            is MyEasyFormsCustomStringResult -> when (result.name as MyFormKeys) {
                MyFormKeys.SALUTATION -> TODO()
                else -> throw IllegalArgumentException("${result.name} is not handled")
            }
            is MyEasyFormsCustomCheckboxListResult -> TODO()
            else -> TODO() //handle other custom results
        }
    }
}