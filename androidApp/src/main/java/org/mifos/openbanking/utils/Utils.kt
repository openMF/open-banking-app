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
fun formatBalance(balance: Double): String? {
    return fmt.format(balance)
}