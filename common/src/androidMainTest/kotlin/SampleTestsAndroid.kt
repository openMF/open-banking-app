package org.mifos.openbanking.common

import org.mifos.openbanking.hello
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsAndroid {
    @Test
    fun testHello() {
        assertTrue("Android" in hello())
    }
}