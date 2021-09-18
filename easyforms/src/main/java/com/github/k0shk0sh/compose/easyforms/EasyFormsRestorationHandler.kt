package com.github.k0shk0sh.compose.easyforms

import android.os.Bundle
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.core.os.bundleOf

internal class EasyFormsRestorationHandler(
    private val savedStateHandle: SaveableStateRegistry? = null,
) {

    /**
     * Save each [EasyFormsState] state into a [Bundle] on its own with its unique identifier.
     * It will call each [EasyFormsState.saveState].
     */
    fun saveFormData(
        callback: () -> Map<Any, EasyFormsState<*, *>>,
    ) {
        savedStateHandle?.registerProvider(BUNDLE_KEY) {
            val savedBundle = bundleOf()
            callback.invoke().forEach { (key, value) ->
                val bundle = bundleOf()
                value.saveState(bundle)
                savedBundle.putBundle(key.toString(), bundle)
            }
            savedBundle
        }
    }

    /**
     * Restore each [EasyFormsState] saved state [Bundle] and it will
     * call each [EasyFormsState.restoreState].
     */
    fun restoreFormData(
        bundleCallback: (Bundle) -> Unit,
    ) {
        savedStateHandle?.consumeRestored(BUNDLE_KEY)?.let { bundle ->
            bundleCallback.invoke(bundle as Bundle)
        }
    }

    /**
     * @suppress
     */
    companion object {
        /**
         * Bundle key used by [EasyForms] to store its bundle of form fields data.
         */
        private const val BUNDLE_KEY = "easyforms_bundle_key"
    }
}