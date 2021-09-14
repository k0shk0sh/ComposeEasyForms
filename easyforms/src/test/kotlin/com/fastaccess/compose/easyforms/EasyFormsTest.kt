package com.fastaccess.compose.easyforms

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.input.TextFieldValue
import org.junit.Assert.*
import org.junit.Test

class EasyFormsTest {
    private val easyForms = EasyForms()

    @Test
    fun `test getTextFieldState with name validation is valid`() {
        val name = "textField"
        val value = "Hello"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, NameValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with name validation is invalid`() {
        val name = "textField"
        val value = ""
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, NameValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.INVALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with email validation is valid`() {
        val name = "textField"
        val value = "email@example.com"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, EmailValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with email validation is invalid`() {
        val name = "textField"
        val value = "email@example"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, EmailValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.INVALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with password validation is valid`() {
        val name = "textField"
        val value = "Passw0rd!"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, PasswordValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with password validation is invalid`() {
        val name = "textField"
        val value = "password1!"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, PasswordValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.INVALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with phone validation is valid`() {
        val name = "textField"
        val value = "+49 (123) 456 7899"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, PhoneNumberValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with phone validation is invalid`() {
        val name = "textField"
        val value = "123"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, PhoneNumberValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.INVALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with creditcard validation is valid`() {
        val name = "textField"
        val value = "5555555555555555"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, CardValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTextFieldState with creditcard validation is invalid`() {
        val name = "textField"
        val value = "55555555"
        assertKeysAreEmpty()
        val state = easyForms.getTextFieldState(name, CardValidationType)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        assertEquals(EasyFormsErrorState.INVALID, state.errorState.value)
        assertEquals(value, state.state.value.text)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getCheckboxState is required`() {
        val name = "checkbox"
        val value = true
        assertKeysAreEmpty()
        val state = easyForms.getCheckboxState(name, isRequired = true)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onValueChangedCallback.invoke(true)
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getCheckboxState not required`() {
        val name = "checkbox"
        val value = false
        assertKeysAreEmpty()
        val state = easyForms.getCheckboxState(name, isRequired = false)
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        state.onValueChangedCallback.invoke(false)
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTriCheckboxState is required`() {
        val name = "tricheckbox"
        val value = ToggleableState.On
        assertKeysAreEmpty()
        val state = easyForms.getTriCheckboxState(
            key = name,
            defaultValue = ToggleableState.Off,
            isRequired = true,
        )
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onClick.invoke()
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getTriCheckboxState not required`() {
        val name = "tricheckbox"
        val value = ToggleableState.Off
        assertKeysAreEmpty()
        val state = easyForms.getTriCheckboxState(
            name,
            defaultValue = ToggleableState.Indeterminate,
            isRequired = false,
        )
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        state.onClick.invoke()
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getRadioButtonState is required`() {
        val name = "radiobutton"
        val value = true
        assertKeysAreEmpty()
        val state = easyForms.getRadioButtonState(name, isRequired = true)
        assertEquals(EasyFormsErrorState.INITIAL, state.errorState.value)
        state.onClick.invoke()
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value)
        assertKeysNotEmpty()
    }

    @Test
    fun `test getRadioButtonState not required`() {
        val name = "radiobutton"
        val value = false
        assertKeysAreEmpty()
        val state = easyForms.getRadioButtonState(name, defaultValue = true, isRequired = false)
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        state.onClick.invoke()
        assertEquals(EasyFormsErrorState.VALID, state.errorState.value)
        assertEquals(value, state.state.value)
        assertKeysNotEmpty()
    }

    @Test
    fun `test addAndGetCustomState with valid state returns same instance`() {
        val key = "key"
        val state = easyForms.addAndGetCustomState(key, TestCustomState())
        assertTrue(easyForms.formKeys().size == 1)
        val newState = easyForms.addAndGetCustomState(key, TestCustomState())
        assertTrue(easyForms.formKeys().size == 1)
        assertEquals(state, newState)
    }

    @Test
    fun `test data() returns empty list`() {
        assertTrue(easyForms.formData().isEmpty())
    }

    @Test
    fun `test formData returns data`() {
        val name = "textField"
        val value = "Hello"
        val state = easyForms.getTextFieldState(name, NameValidationType)
        state.onValueChangedCallback.invoke(TextFieldValue(value))
        val data = easyForms.formData()
        assertEquals(1, data.size)
        assertTrue(data.first() is EasyFormsResult.StringResult)
        assertEquals(data.first().key, name)
        assertEquals(data.first().easyFormsErrorState, EasyFormsErrorState.VALID)
        assertEquals((data.first() as EasyFormsResult.StringResult).value, value)
    }

    private fun assertKeysNotEmpty() = assertTrue(easyForms.formKeys().isNotEmpty())
    private fun assertKeysAreEmpty() = assertTrue(easyForms.formKeys().isEmpty())
}

private class TestCustomState : EasyFormsState<MutableState<Any>, Any>() {
    override val state: MutableState<Any> = mutableStateOf("")
    override val onValueChangedCallback: (Any) -> Unit = {}
    override fun mapToResult(key: Any): EasyFormsResult {
        return TestCustomResult(key, errorState.value, state.value)
    }
}

private class TestCustomResult(
    override val key: Any,
    override val easyFormsErrorState: EasyFormsErrorState,
    override val value: Any,
) : EasyFormsResult.GenericStateResult<Any>(
    key = key,
    easyFormsErrorState = easyFormsErrorState,
    value = value,
)