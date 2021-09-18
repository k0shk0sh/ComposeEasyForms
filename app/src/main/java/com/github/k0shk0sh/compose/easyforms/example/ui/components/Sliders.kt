package com.github.k0shk0sh.compose.easyforms.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.github.k0shk0sh.compose.easyforms.EasyForms
import com.github.k0shk0sh.compose.easyforms.example.custom_states.MyFormKeys

@Composable
fun SliderLayout(easyForm: EasyForms) {
    Column {
        val state = easyForm.getSliderState(MyFormKeys.SLIDER)
        val sliderPosition = state.state.value
        Text(text = "${state.errorState.value}:$sliderPosition")
        Slider(
            value = sliderPosition,
            onValueChange = state.onValueChangedCallback,
            valueRange = 0f..100f,
            onValueChangeFinished = state.onValueChangeFinished,
            steps = 5,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.secondary
            )
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RangeSliderLayout(easyForm: EasyForms) {
    Column {
        val state = easyForm.getRangeSliderState(MyFormKeys.RANGE_SLIDER)
        val sliderPosition = state.state.value

        Text(text = "${state.errorState.value}:$sliderPosition")
        RangeSlider(
            steps = 5,
            values = sliderPosition,
            onValueChange = state.onValueChangedCallback,
            valueRange = 0f..100f,
            onValueChangeFinished = state.onValueChangeFinished,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.secondary
            )
        )
    }
}