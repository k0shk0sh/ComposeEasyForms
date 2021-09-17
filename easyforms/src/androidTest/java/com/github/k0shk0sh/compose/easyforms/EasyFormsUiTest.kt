package com.github.k0shk0sh.compose.easyforms

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class EasyFormsUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBuildEasyForms_WithDefaultValidValue() {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    BuildTextField(state = easyForms.getTextFieldState(
                        key = "email",
                        easyFormsValidationType = EmailValidationType,
                        defaultValue = "email@example.com"
                    ))
                    BuildTextField(state = easyForms.getTextFieldState(
                        key = "password",
                        easyFormsValidationType = PasswordValidationType,
                        defaultValue = "Passw0rd!"
                    ))
                    BuildButton(easyForms) {
                        assertTrue(easyForms.formStates().all {
                            it.value == EasyFormsErrorState.VALID
                        })
                    }
                }
            }
        }
        composeTestRule.onNode(hasText("Button"))
            .assert(isEnabled())
            .performClick()
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidValue() {
        composeTestRule.setContent {
            BuildEasyForms { easyForms ->
                Column {
                    BuildTextField(state = easyForms.getTextFieldState(
                        key = "email",
                        easyFormsValidationType = EmailValidationType,
                        defaultValue = "email@"
                    ))
                    BuildTextField(state = easyForms.getTextFieldState(
                        key = "password",
                        easyFormsValidationType = PasswordValidationType,
                        defaultValue = "Passw0rd"
                    ))
                    BuildButton(easyForms) {
                        assertTrue(easyForms.formStates().all {
                            it.value == EasyFormsErrorState.INVALID
                        })
                    }
                }
            }
        }
        composeTestRule.onNode(hasText("Button"))
            .assertIsNotEnabled()
    }
}

@Composable
private fun BuildButton(
    easyForms: EasyForms,
    onClick: () -> Unit,
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