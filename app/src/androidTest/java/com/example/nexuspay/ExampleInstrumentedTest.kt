package com.example.nexuspay

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented tests run on a device or emulator and can access the Android framework.
 * Use this suite for checks that require a real application context.
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Target context for the app under test (not the test APK itself).
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.nexuspay", appContext.packageName)
    }
}
