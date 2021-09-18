package com.github.k0shk0sh.compose.easyforms

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class EasyFormsUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val buttonNode get() = composeTestRule.onNode(hasText("Button"))

    /*   TEXTFIELDS   */

    @Test
    fun testBuildEasyForms_WithDefaultValidEmailValue() {
        testTextFieldStates(EmailValidationType, "example@example.com")
        buttonNode.assert(isEnabled())
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidEmailValue() {
        testTextFieldStates(EmailValidationType, "example@")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidPasswordValue() {
        testTextFieldStates(PasswordValidationType, "Passw0rd!")
        buttonNode.assert(isEnabled())
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidPasswordValue() {
        testTextFieldStates(PasswordValidationType, "Password")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidNameValue() {
        testTextFieldStates(NameValidationType, "Name")
        buttonNode.assert(isEnabled())
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidNameValue() {
        testTextFieldStates(NameValidationType, "")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidUrlValue() {
        testTextFieldStates(UrlValidationType, "example.com")
        buttonNode.assert(isEnabled())
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidUrlValue() {
        testTextFieldStates(UrlValidationType, ".com")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidPhoneValue() {
        testTextFieldStates(PhoneNumberValidationType, "+49 123-456-7899")
        buttonNode.assert(isEnabled())
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidPhoneValue() {
        testTextFieldStates(PhoneNumberValidationType, "012345")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidCardValue() {
        testTextFieldStates(CardValidationType, "5555555555555555")
        buttonNode.assert(isEnabled())
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidCardValue() {
        testTextFieldStates(CardValidationType, "55555555555555")
        buttonNode.assertIsNotEnabled()
    }

    /*   CHECKBOX   */

    @Test
    fun testBuildEasyForms_WithOnDefaultValidRequiredCheckboxState() {
        buildCheckbox(defaultValue = true)
        composeTestRule.onNode(hasTestTag(Keys.CHECKBOX.name)).assertIsOn()
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithOffButNotRequiredCheckboxState() {
        buildCheckbox(defaultValue = false, isRequired = false)
        composeTestRule.onNode(hasTestTag(Keys.CHECKBOX.name)).assertIsOff()
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithOffButRequiredCheckboxState() {
        buildCheckbox(defaultValue = false, isRequired = true)
        composeTestRule.onNode(hasTestTag(Keys.CHECKBOX.name)).assertIsOff()
        buttonNode.assertIsNotEnabled()
    }

    /*   SWITCH   */

    @Test
    fun testBuildEasyForms_WithOnDefaultValidRequiredSwitchState() {
        buildSwitch(defaultValue = true)
        composeTestRule.onNode(hasTestTag(Keys.SWITCH.name)).assertIsOn()
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithOffButNotRequiredSwitchState() {
        buildSwitch(defaultValue = false, isRequired = false)
        composeTestRule.onNode(hasTestTag(Keys.SWITCH.name)).assertIsOff()
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithOffButRequiredSwitchState() {
        buildSwitch(defaultValue = false, isRequired = true)
        composeTestRule.onNode(hasTestTag(Keys.SWITCH.name)).assertIsOff()
        buttonNode.assertIsNotEnabled()
    }

    /*   RADIOBUTTON   */

    @Test
    fun testBuildEasyForms_WithOnDefaultValidRequiredRadioButtonState() {
        buildRadioButton(defaultValue = true)
        composeTestRule.onNode(hasTestTag(Keys.RADIO_BUTTON.name)).assertIsSelected()
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithOffButNotRequiredRadioButtonState() {
        buildRadioButton(defaultValue = false, isRequired = false)
        composeTestRule.onNode(hasTestTag(Keys.RADIO_BUTTON.name)).assertIsNotSelected()
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithOffButRequiredRadioButtonState() {
        buildRadioButton(defaultValue = false, isRequired = true)
        composeTestRule.onNode(hasTestTag(Keys.RADIO_BUTTON.name)).assertIsNotSelected()
        buttonNode.assertIsNotEnabled()
    }

    /*   TRI_STATE_CHECKBOX   */

    @Test
    fun testBuildEasyForms_WithOnDefaultValidRequiredTriStateCheckboxState() {
        buildTriStateCheckbox(defaultValue = ToggleableState.On)
        composeTestRule.onNode(hasTestTag(Keys.TRI_STATE_CHECKBOX.name)).assertIsOn()
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithOffButNotRequiredTriStateCheckboxState() {
        buildTriStateCheckbox(defaultValue = ToggleableState.Off, isRequired = false)
        composeTestRule.onNode(hasTestTag(Keys.TRI_STATE_CHECKBOX.name)).assertIsOff()
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithOffButRequiredTriStateCheckboxState() {
        buildTriStateCheckbox(defaultValue = ToggleableState.Off, isRequired = true)
        composeTestRule.onNode(hasTestTag(Keys.TRI_STATE_CHECKBOX.name)).assertIsOff()
        buttonNode.assertIsNotEnabled()
    }

    /*   Slider   */

    @Test
    fun testBuildEasyForms_WithDefaultValidRequiredSliderState() {
        buildSlider(defaultValue = 50F)
        composeTestRule.onNode(hasTestTag(Keys.SLIDER.name))
            .assertRangeInfoEquals(
                ProgressBarRangeInfo(current = 50F, range = 0F..100f),
            )
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithZeroButNotRequiredSliderState() {
        buildSlider(defaultValue = 0F, isRequired = false)
        composeTestRule.onNode(hasTestTag(Keys.SLIDER.name))
            .assertRangeInfoEquals(
                ProgressBarRangeInfo(current = 0F, range = 0F..100f),
            )
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithZeroButRequiredSliderState() {
        buildSlider(defaultValue = 0F)
        composeTestRule.onNode(hasTestTag(Keys.SLIDER.name))
            .assertRangeInfoEquals(
                ProgressBarRangeInfo(current = 0F, range = 0F..100f),
            )
        buttonNode.assertIsNotEnabled()
    }

    /*   RangeSlider   */

    @Test
    fun testBuildEasyForms_WithDefaultValidRequiredRangeSliderState() {
        buildRangeSlider(defaultValue = 10F..50F, result = {
            assert(it == 10F..50F)
        })
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithZeroButNotRequiredRangeSliderState() {
        buildRangeSlider(defaultValue = 0F..0F, isRequired = false, result = {
            assert(it == 0F..0F)
        })
        buttonNode.assertIsEnabled()
    }

    @Test
    fun testBuildEasyForms_WithZeroButRequiredRangeSliderState() {
        buildRangeSlider(defaultValue = 0F..0F, result = {
            assert(it == 0F..0F)
        })
        buttonNode.assertIsNotEnabled()
    }

    private fun testTextFieldStates(
        easyFormsValidationType: EasyFormsValidationType,
        defaultValue: String = "",
    ) {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    BuildTextField(state = easyForms.getTextFieldState(
                        key = Keys.TEXT_FIELD,
                        easyFormsValidationType = easyFormsValidationType,
                        defaultValue = defaultValue
                    ))
                    BuildButton(easyForms)
                }
            }
        }
    }

    private fun buildCheckbox(
        defaultValue: Boolean = false,
        isRequired: Boolean = true,
    ) {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    val state = easyForms.getCheckboxState(
                        Keys.CHECKBOX, defaultValue, isRequired,
                    )
                    Checkbox(
                        checked = state.state.value,
                        onCheckedChange = state.onValueChangedCallback,
                        modifier = Modifier.testTag(Keys.CHECKBOX.name)
                    )
                    BuildButton(easyForms)
                }
            }
        }
    }

    private fun buildSwitch(
        defaultValue: Boolean = false,
        isRequired: Boolean = true,
    ) {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    val state = easyForms.getSwitchState(
                        Keys.SWITCH, defaultValue, isRequired,
                    )
                    Switch(
                        checked = state.state.value,
                        onCheckedChange = state.onValueChangedCallback,
                        modifier = Modifier.testTag(Keys.SWITCH.name)
                    )
                    BuildButton(easyForms)
                }
            }
        }
    }

    private fun buildRadioButton(
        defaultValue: Boolean = false,
        isRequired: Boolean = true,
    ) {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    val state = easyForms.getRadioButtonState(
                        Keys.TRI_STATE_CHECKBOX, defaultValue, isRequired,
                    )
                    RadioButton(
                        selected = state.state.value,
                        onClick = state.onClick,
                        modifier = Modifier.testTag(Keys.RADIO_BUTTON.name)
                    )
                    BuildButton(easyForms)
                }
            }
        }
    }

    private fun buildTriStateCheckbox(
        defaultValue: ToggleableState = ToggleableState.Indeterminate,
        isRequired: Boolean = true,
    ) {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    val state = easyForms.getTriCheckboxState(
                        Keys.TRI_STATE_CHECKBOX, defaultValue, isRequired,
                    )
                    TriStateCheckbox(
                        state = state.state.value,
                        onClick = state.onClick,
                        modifier = Modifier.testTag(Keys.TRI_STATE_CHECKBOX.name)
                    )
                    BuildButton(easyForms)
                }
            }
        }
    }

    private fun buildSlider(
        defaultValue: Float = 0F,
        isRequired: Boolean = true,
    ) {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    val state = easyForms.getSliderState(
                        Keys.SLIDER, defaultValue, isRequired,
                    )
                    Slider(
                        value = state.state.value,
                        onValueChange = state.onValueChangedCallback,
                        onValueChangeFinished = state.onValueChangeFinished,
                        modifier = Modifier.testTag(Keys.SLIDER.name),
                        valueRange = 0f..100f,
                    )
                    BuildButton(easyForms)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun buildRangeSlider(
        defaultValue: ClosedFloatingPointRange<Float> = 0F..0F,
        result: (ClosedFloatingPointRange<Float>) -> Unit,
        isRequired: Boolean = true,
    ) {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    val state = easyForms.getRangeSliderState(
                        Keys.RANGE_SLIDER, defaultValue, isRequired,
                    )
                    result.invoke(state.state.value)
                    RangeSlider(
                        values = state.state.value,
                        onValueChange = state.onValueChangedCallback,
                        onValueChangeFinished = state.onValueChangeFinished,
                        modifier = Modifier.testTag(Keys.RANGE_SLIDER.name),
                        valueRange = 0f..100f,
                    )
                    BuildButton(easyForms)
                }
            }
        }
    }

    @Composable
    private fun BuildButton(
        easyForms: EasyForms,
        onClick: () -> Unit = {},
    ) {
        Button(onClick = onClick, enabled = easyForms.observeFormStates().value.all {
            it.value == EasyFormsErrorState.VALID
        }) {
            Text("Button")
        }
    }

    @Composable
    private fun BuildTextField(
        state: EasyFormsTextFieldState,
    ) {
        TextField(value = state.state.value, onValueChange = state.onValueChangedCallback)
    }
}

internal enum class Keys {
    TEXT_FIELD, CHECKBOX, TRI_STATE_CHECKBOX, RADIO_BUTTON, SWITCH, SLIDER, RANGE_SLIDER, 
} 