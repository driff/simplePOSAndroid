package com.treefuerza.simplepos.ui.main.orders.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerCreateOrdersComponent
import javax.inject.Inject

class CreateOrderFragment : BaseMvRxFragment() {

    //TODO: SET This as EditOrder

    override fun invalidate() = withState(viewModel) {

    }

    val TAG = this.javaClass.canonicalName

    @Inject
    lateinit var adapter: OrderDetailsAdapter
    private val viewModel: CreateOrderViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (context != null) {
            DaggerCreateOrdersComponent.builder()
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
        val view = inflater.inflate(R.layout.fragment_edit_order, container, false)
        initView(view)
        return view

    }

    private fun initView(view: View) {
        view.apply {

        }
    }

}
