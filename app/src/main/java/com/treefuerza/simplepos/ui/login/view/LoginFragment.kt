package com.treefuerza.simplepos.ui.login.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.*
import com.google.android.material.snackbar.Snackbar
import com.treefuerza.simplepos.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : BaseMvRxFragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    private val viewModel: LoginViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnSignup.setOnClickListener { findNavController().navigate(R.id.signupFragment) }
        btnLogin.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToMainActivity(72)
            findNavController().navigate(action)
        }
        edtEmail.setOnFocusChangeListener { _, hasFocus -> viewModel.updateEmail(edtEmail.text.toString()) }
        edtPassword.setOnFocusChangeListener{ _, hasFocus -> viewModel.updatePassword(edtPassword.text.toString())}
        btnLogin.setOnClickListener { viewModel.login() }
    }

    override fun invalidate() = withState(viewModel) {
        edtEmail.setText(it.email)
        edtPassword.setText(it.password)
        when(it.login){
            is Success -> {
                Snackbar.make(parent, "Logged in!", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.mainActivity)
            }
        }
    }
}
