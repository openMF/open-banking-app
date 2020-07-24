package org.mifos.openbanking.utils

import android.content.res.Resources

fun pxToDp(px: Float): Float {
    return px / Resources.getSystem().displayMetrics.density
}

fun dpToPx(dp: Float): Float {
    return dp * Resources.getSystem().displayMetrics.density
}