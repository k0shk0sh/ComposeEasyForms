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
    private val buttonNode get() = composeTestRule.onNode(hasText("Button"))

    @Test
    fun testBuildEasyForms_WithDefaultValidEmailValue() {
        testTextFieldStates(EmailValidationType, "example@example.com")
        buttonNode
            .assert(isEnabled())
            .performClick()
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidEmailValue() {
        testTextFieldStates(EmailValidationType, "example@")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidPasswordValue() {
        testTextFieldStates(PasswordValidationType, "Passw0rd!")
        buttonNode
            .assert(isEnabled())
            .performClick()
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidPasswordValue() {
        testTextFieldStates(PasswordValidationType, "Password")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidNameValue() {
        testTextFieldStates(NameValidationType, "Name")
        buttonNode
            .assert(isEnabled())
            .performClick()
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidNameValue() {
        testTextFieldStates(NameValidationType, "")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidUrlValue() {
        testTextFieldStates(UrlValidationType, "example.com")
        buttonNode
            .assert(isEnabled())
            .performClick()
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidUrlValue() {
        testTextFieldStates(UrlValidationType, ".com")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidPhoneValue() {
        testTextFieldStates(PhoneNumberValidationType, "+49 123-456-7899")
        buttonNode
            .assert(isEnabled())
            .performClick()
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidPhoneValue() {
        testTextFieldStates(PhoneNumberValidationType, "012345")
        buttonNode.assertIsNotEnabled()
    }

    @Test
    fun testBuildEasyForms_WithDefaultValidCardValue() {
        testTextFieldStates(CardValidationType, "5555555555555555")
        buttonNode
            .assert(isEnabled())
            .performClick()
    }

    @Test
    fun testBuildEasyForms_WithDefaultInValidCardValue() {
        testTextFieldStates(CardValidationType, "55555555555555")
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
                        key = "email",
                        easyFormsValidationType = easyFormsValidationType,
                        defaultValue = defaultValue
                    ))
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