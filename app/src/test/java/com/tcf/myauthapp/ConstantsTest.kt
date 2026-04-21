package com.tcf.myauthapp

import org.junit.Assert.*
import org.junit.Test

class ConstantsTest {

    @Test
    fun testRedirectUri_isCorrect() {
        val expected = "com.tcf.myauthapp://oauth2redirect"
        assertEquals("The redirect URI must match the scheme defined in build.gradle", 
            expected, Constants.REDIRECT_URI.toString())
    }

    @Test
    fun testIssuerUrl_isNotNull() {
        assertNotNull("Issuer URL should not be empty", Constants.ISSUER)
        assertTrue("Issuer should be a valid URL starting with http", 
            Constants.ISSUER.startsWith("http"))
    }
}
