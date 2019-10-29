package com.treefuerza.simplepos.ui.login

import com.treefuerza.simplepos.ui.login.view.LoginFragment
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.FragmentTestUtil

@RunWith(JUnit4::class)
class LoginFragmentTest {

    private lateinit var fragment: LoginFragment

    @Before
    fun setUp() {
        this.fragment = LoginFragment()

    }

}