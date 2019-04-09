package com.treefuerza.simplepos.ui.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.treefuerza.simplepos.R
import kotlinx.android.synthetic.main.signup_fragment.*


class SignupFragment : BaseMvRxFragment() {
    override fun invalidate() = withState(viewModel) {
        edtEmail.setText(it.email)
        edtPassword.setText(it.password)
        edtName.setText(it.userName)
        edtPassConfirm.setText(it.passwordConf)
        when (it.createUser) {
            is Success -> {
                this@SignupFragment.findNavController().popBackStack()
                Toast.makeText(requireContext(), "Your user was created", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val viewModel: LoginViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        edtEmail.setOnFocusChangeListener(this::edtFocusChanged)
        btnAddUser.setOnClickListener { viewModel.createUser() }
    }

    private fun edtFocusChanged(view: View, focus: Boolean) {
        if(!focus) {
            viewModel.updateEmail(edtEmail.text.toString())
            viewModel.updateName(edtName.text.toString())
            viewModel.updatePassword(edtPassword.text.toString())
            viewModel.updatePasswordConfirm(edtPassConfirm.text.toString())
        }
    }
}
