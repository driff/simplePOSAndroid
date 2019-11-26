package com.treefuerza.simplepos.ui.main.orders.open


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.*

import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerOpenOrdersComponent
import com.treefuerza.simplepos.models.Orders
import com.treefuerza.simplepos.ui.main.orders.OrdersFragmentDirections
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_open_orders.*
import kotlinx.android.synthetic.main.fragment_open_orders.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 *
 */
class OpenOrdersFragment : BaseMvRxFragment() {

    val TAG = this.javaClass.canonicalName
    val subs = CompositeDisposable()

    val viewModel: OpenOrdersViewModel by fragmentViewModel()
    @Inject
    lateinit var adapter: OrdersAdapter

    override fun invalidate(): Unit = withState(viewModel) {
        txvPrice.text = "Total: $%.2f / %d Open Orders"
            .format(it.total, it.size) // TODO: split this
        when(it.orders){
            is Success -> {
                Log.i(TAG, "Orders success")
                adapter.replaceAll(it.orders.invoke())
            }
            else -> Log.i(TAG, "Else....")
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
        subs.clear()
        subs.add(adapter.clickEvent
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .subscribe(this::onOrderSelected, this::onError))
        return v
    }

    fun onOrderSelected(order: Orders) {
        Log.i(TAG, "editOrder: $order")
        val bundle = Bundle().apply {
            putString(getString(R.string.order_id), order.id)
        }
        OrdersFragmentDirections.actionOrdersFragmentToCreateOrderFragment(order.id)
        findNavController().navigate(R.id.action_ordersFragment_to_createOrderFragment, bundle)
    }

    fun onError(error: Throwable) {
        Log.e(TAG, error.message?: "Error...")
    }

    override fun onDestroy() {
        subs.clear()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
