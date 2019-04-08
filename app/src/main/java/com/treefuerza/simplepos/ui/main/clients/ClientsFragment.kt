package com.treefuerza.simplepos.ui.main.clients

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.treefuerza.simplepos.R
import kotlinx.android.synthetic.main.clients_fragment.*


data class ClientState(val userId: Int = 1): MvRxState

class ClientsFragment : BaseMvRxFragment() {

    private val viewModel: ClientsViewModel by fragmentViewModel()
    val args: ClientsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.clients_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun invalidate() = withState(viewModel) {
        title.text = it.userId.toString()
    }
}
