package com.treefuerza.simplepos.ui.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.treefuerza.simplepos.R
import kotlinx.android.synthetic.main.signup_fragment.*


class SignupFragment : BaseMvRxFragment() {
    override fun invalidate() = withState(viewModel) {
        edtEmail.setText(it.email)
        edtPassword.setText(it.password)
        edtName.setText(it.userName)
    }

    private val viewModel: LoginViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
