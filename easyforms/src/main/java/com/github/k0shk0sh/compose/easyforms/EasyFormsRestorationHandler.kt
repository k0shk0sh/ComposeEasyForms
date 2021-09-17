package com.github.k0shk0sh.compose.easyforms

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.*

internal class EasyFormsRestorationHandler(
    private val savedStateHandle: SaveableStateRegistry? = null,
) {

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
            Log.e("Save", "$savedBundle")
            savedBundle
        }
    }

    fun restoreFormData(
        bundleCallback: (Bundle) -> Unit,
    ) {
        savedStateHandle?.consumeRestored(BUNDLE_KEY)?.let { bundle ->
            Log.e("restoring", "$bundle")
            bundleCallback.invoke(bundle as Bundle)
        }
    }

    companion object {
        private const val BUNDLE_KEY = "easyforms_bundle_key"
        private const val BUNDLE_DATA_KEY = "easyforms_bundle_data_key"
    }
}