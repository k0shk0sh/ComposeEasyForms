package com.github.k0shk0sh.compose.easyforms

/**
 * Represent the three different states a field could have.
 */
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