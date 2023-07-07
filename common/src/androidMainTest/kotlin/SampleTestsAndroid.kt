package org.mifos.openbanking.common

import org.junit.Assert.assertTrue
import org.junit.Test
import org.mifos.openbanking.hello

class SampleTestsAndroid {
    @Test
    fun testHello() {
        assertTrue("Android" in hello())
    }
}