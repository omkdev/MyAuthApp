package com.tcf.myauthapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.openid.appauth.AuthState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthStateManagerTest {

    private lateinit var stateManager: AuthStateManager

    @Before
    fun setup() {
        // Get the context of the app under test
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        stateManager = AuthStateManager(context)
        stateManager.clear() // Start with a clean slate
    }

    @Test
    fun saveAndReadState_isSuccessful() {
        // 1. GIVEN: A new AuthState
        val originalState = AuthState()
        
        // 2. WHEN: We write it to storage
        stateManager.write(originalState)
        
        // 3. THEN: Reading it back should return a non-null object
        val savedState = stateManager.read()
        assertNotNull("Saved state should not be null", savedState)
    }

    @Test
    fun clearState_worksCorrect() {
        // GIVEN: We saved some state
        stateManager.write(AuthState())
        
        // WHEN: We clear it
        stateManager.clear()
        
        // THEN: The access token should be null
        val state = stateManager.read()
        assertEquals(null, state.accessToken)
    }
}
