package com.developer.takenote.featurecreateuser.domain.util

import org.junit.Assert.*
import org.junit.Test

class RegistrationUtilsTest {

    @Test
    fun email_validator_correctEmailSamples_returnTrue() {
        assertTrue(RegistrationUtils.isValidUserEmail("abed@gmail.com"))
    }

    @Test
    fun password_validator_correctPasswordSamples_returnTrue() {
        assertEquals("Password not valid",true,RegistrationUtils.isValidPassword("Cat@017"))

    }

}