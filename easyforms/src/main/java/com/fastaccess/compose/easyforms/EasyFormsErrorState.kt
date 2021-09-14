package com.fastaccess.compose.easyforms

enum class EasyFormsErrorState {
    /**
     * This is the default state for all of the form fields as it provide better UX
     * to not show the errors when form is created.
     */
    INITIAL,

    /**
     * This determines that the form field data is valid.
     */
    VALID,

    /**
     * This determines that the form field data is invalid.
     */
    INVALID;
}