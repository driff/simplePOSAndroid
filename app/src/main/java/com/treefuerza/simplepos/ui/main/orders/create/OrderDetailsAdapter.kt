package com.treefuerza.simplepos.ui.main.orders.create

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.models.OrderDetail
import kotlinx.android.synthetic.main.fragment_create_order.view.*
import kotlinx.android.synthetic.main.order_detail_recycler.view.*
import javax.inject.Inject

class OrderDetailsAdapter @Inject constructor(private var list: MutableList<OrderDetail>) : RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    fun add(orderDetail: OrderDetail) {
        list.add(orderDetail)
        notifyItemInserted(list.size)
    }

    fun addAll(orderDetails: List<OrderDetail>) {
        this.list.addAll(orderDetails)
        notifyDataSetChanged()
    }

    fun replaceAll(details: List<OrderDetail>) {
        this.list = details.toMutableList()
        notifyDataSetChanged()
    }

    fun remove(pos: Int) {
        list.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_detail_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(detail: OrderDetail) {
            Log.w("Recycler", detail.toString())
            view.apply {
                txvQuantity.text = detail.quantity.toString()
                txvItemName.text = String.format(detail.description)
                txvTotal.text = String.format("$%4.2f", detail.total)
            }
        }

    }

}