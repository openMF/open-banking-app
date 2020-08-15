package org.mifos.openbanking.utils

import android.content.res.Resources
import java.text.DecimalFormat
import java.util.*

fun pxToDp(px: Float): Float {
    return px / Resources.getSystem().displayMetrics.density
}

fun dpToPx(dp: Float): Float {
    return dp * Resources.getSystem().displayMetrics.density
}

private val fmt = DecimalFormat.getInstance(Locale("en", "US"))
fun formatBalance(balance: Double, withCurrencySymbol: Boolean = false): String? {
    val formatted = fmt.format(balance)
    return if (withCurrencySymbol) {
        "£$formatted"
    } else {
        formatted
    }
}

fun formatTransactionRequestId(requestId: String): String {
    return try {
        requestId.substring(0, 4) + "xxxxx" + requestId.substring(
            requestId.lastIndex - 2,
            requestId.lastIndex
        )
    } catch (exp: Exception) {
        requestId
    }
}