package com.treefuerza.simplepos.ui.main.orders.open


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.snackbar.Snackbar

import com.treefuerza.simplepos.R
import kotlinx.android.synthetic.main.fragment_open_orders.*

/**
 * A simple [Fragment] subclass.
 *
 */
class OpenOrdersFragment : BaseMvRxFragment() {

    val viewModel: OpenOrdersViewModel by fragmentViewModel()

    override fun invalidate() = withState(viewModel) {
        txvTitle.text = String.format("Total: $%0.2f / %d Open Orders", it.total, it.size)
        when(it.orders){
            is Success -> {
                //TODO: set values to adapter
            }
            else -> {
                Snackbar.make(parent, "State: ${it.orders}", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_orders, container, false)
    }


}
