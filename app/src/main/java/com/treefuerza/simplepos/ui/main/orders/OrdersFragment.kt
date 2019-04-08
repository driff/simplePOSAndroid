package com.treefuerza.simplepos.ui.main.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.airbnb.mvrx.*
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.models.User
import kotlinx.android.synthetic.main.orders_fragment.*

data class OrdersState(val user: Async<User> = Uninitialized): MvRxState

class OrdersFragment : BaseMvRxFragment() {

    private val viewModel: OrdersViewModel by fragmentViewModel()
    private val args: OrdersFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title.setOnClickListener {
            viewModel.fetchUser()
        }
    }

    fun getNavArgs() = this.args

    override fun invalidate() = withState(viewModel) {

        title.text = it.user.toString()
    }

}
