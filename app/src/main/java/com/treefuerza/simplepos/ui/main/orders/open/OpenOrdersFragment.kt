package com.treefuerza.simplepos.ui.main.orders.open


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerOpenOrdersComponent
import com.treefuerza.simplepos.ui.main.orders.create.CreateOrderFragment
import kotlinx.android.synthetic.main.fragment_edit_order.*
import kotlinx.android.synthetic.main.fragment_open_orders.*
import kotlinx.android.synthetic.main.fragment_open_orders.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class OpenOrdersFragment : BaseMvRxFragment() {

    val TAG = this.javaClass.canonicalName

    val viewModel: OpenOrdersViewModel by fragmentViewModel()
    @Inject
    lateinit var adapter: OrdersAdapter

    override fun invalidate() = withState(viewModel) {
        txvPrice.text = "Total: $%.2f / %d Open Orders".format(it.total, it.size)
        when(it.orders){
            is Success -> {
                Log.i(TAG, "Orders success")
                adapter.replaceAll(it.orders.invoke())
            }
            else -> {
                Snackbar.make(parent, "State: ${it.orders}", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(context!=null){
            DaggerOpenOrdersComponent.builder()
                .appComponent(TreeApplication.get().component)
                .build().inject(this)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_open_orders, container, false)
        v.ordersRecycler.adapter = adapter
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
