package com.treefuerza.simplepos.ui.login

import com.treefuerza.simplepos.R
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoginActivityTest {
    private lateinit var activity: LoginActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(LoginActivity::class.java).create().resume().get()
    }

    @Test
    fun shouldNotBeNull() {
        assertNotNull(activity)
    }

    @Test
    fun shouldHaveFragment() {
        assertNotNull(activity.supportFragmentManager.primaryNavigationFragment?.id == R.id.loginFragment)
    }

}