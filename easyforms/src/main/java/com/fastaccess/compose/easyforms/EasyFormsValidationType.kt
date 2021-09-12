package com.fastaccess.compose.easyforms

import androidx.core.util.PatternsCompat

abstract class EasyFormsValidationType(
    open val minLength: Int = Int.MIN_VALUE,
    open val maxLength: Int = Int.MAX_VALUE,
    open val regex: String? = null,
) {
    fun isValid(value: String): Boolean {
        val regex = regex
        val validLength = {
            value.length in minLength..maxLength
        }
        return if (regex == null) {
            validLength()
        } else {
            val matches = regex.toRegex().matches(value)
            matches && validLength()
        }
    }
}

/**
 * Email Validation using @see [androidx.core.util.PatternsCompat.EMAIL_ADDRESS]
 */
object EmailValidationType : EasyFormsValidationType(
    regex = PatternsCompat.EMAIL_ADDRESS.pattern(),
    minLength = 5,
)

/**
 * Password Validation using below criteria:
 * - Password must contain at least one digit [0-9].
 * - Password must contain at least one lowercase Latin character [a-z].
 * - Password must contain at least one uppercase Latin character [A-Z].
 * - Password must contain at least one special character like ! @ # & ( ).
 * - Password must contain a length of at least 8 characters and a maximum of 20 characters.
 */
object PasswordValidationType : EasyFormsValidationType(
    minLength = 8,
    maxLength = 20,
    regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
)

/**
 * Phone Validation using below criteria:
 * - +49 (162) 123 1234
 * - +49 162 123 1234
 * - 162 123 1234
 * - 1621231234
 */
object PhoneNumberValidationType : EasyFormsValidationType(
    regex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
    minLength = 6,
    maxLength = 18,
)

/**
 * Phone Validation using @see [androidx.core.util.PatternsCompat.WEB_URL]
 */
object UrlValidationType : EasyFormsValidationType(
    regex = PatternsCompat.WEB_URL.pattern(),
)

/**
 * Name Validation
 * - Min = 1
 * - Max = 15
 */
object NameValidationType : EasyFormsValidationType(
    minLength = 1,
    maxLength = 20,
)

object CardValidationType : EasyFormsValidationType(
    minLength = 5,
    regex = "^(3[47][0-9]{13}|(6541|6556)[0-9]{12}|389[0-9]{11}|3(?:0[0-5]|[68][0-9])[0-9]{11}|65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})|63[7-9][0-9]{13}|(?:2131|1800|35\\d{3})\\d{11}|9[0-9]{15}|(6304|6706|6709|6771)[0-9]{12,15}|(5018|5020|5038|6304|6759|6761|6763)[0-9]{8,15}|(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))|(6334|6767)[0-9]{12}|(6334|6767)[0-9]{14}|(6334|6767)[0-9]{15}|(4903|4905|4911|4936|6333|6759)[0-9]{12}|(4903|4905|4911|4936|6333|6759)[0-9]{14}|(4903|4905|4911|4936|6333|6759)[0-9]{15}|564182[0-9]{10}|564182[0-9]{12}|564182[0-9]{13}|633110[0-9]{10}|633110[0-9]{12}|633110[0-9]{13}|(62[0-9]{14,17})|4[0-9]{12}(?:[0-9]{3})?|(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}))$"
)

object ToggleableValidationType : EasyFormsValidationType()