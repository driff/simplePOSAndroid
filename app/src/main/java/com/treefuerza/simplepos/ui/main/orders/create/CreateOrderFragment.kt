package com.treefuerza.simplepos.ui.main.orders.create

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.snackbar.Snackbar

import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerCreateOrdersComponent
import com.treefuerza.simplepos.utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_create_order.*
import kotlinx.android.synthetic.main.fragment_create_order.view.*
import javax.inject.Inject

class CreateOrderFragment : RoundedBottomSheetDialogFragment() {

    override fun invalidate() = withState(viewModel) {
        when (it.item) {
            is Success -> {
                val item = it.item.invoke()
                tilItemCode.hint = "${item.name}/$${item.price}"
            }
            is Loading -> Log.i(TAG, "LOADING ITEM")

            else -> Log.i(TAG, "Something hapened...")

        }
        when (it.details) {
            is Loading -> Snackbar.make(container, "Loading Detail", Snackbar.LENGTH_SHORT).show()
            is Success -> adapter.replaceAll(it.details.invoke())
            else -> Snackbar.make(container, "Something happened with detail", Snackbar.LENGTH_SHORT).show()
        }
    }

    val TAG = this.javaClass.canonicalName

    @Inject
    lateinit var adapter: OrderDetailsAdapter
    val viewModel: CreateOrderViewModel by fragmentViewModel()

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
        val view = inflater.inflate(R.layout.fragment_create_order, container, false)
        initView(view)
        return view

    }

    private fun initView(view: View) {
        view.apply {
            tilItemCode.setEndIconOnClickListener {
                if (!edtItemCode.text.isNullOrEmpty())
                    viewModel.fetchProduct(edtItemCode.text.toString())
            }
            btnAddDetail.setOnClickListener { viewModel.addDetail() }
            recyclerOrderDetails.adapter = adapter
            edtQuantity.doOnTextChanged { text, _, _, _ -> viewModel.setQuantity(text.toString().toDoubleOrNull() ?: 0.0) }
        }
    }

}
