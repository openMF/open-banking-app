package org.mifos.openbanking.common

import org.mifos.openbanking.Proxy
import org.mifos.openbanking.Sample
import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
    }

    @Test
    fun testProxy() {
        assertTrue(Proxy().proxyHello().isNotEmpty())
    }
}