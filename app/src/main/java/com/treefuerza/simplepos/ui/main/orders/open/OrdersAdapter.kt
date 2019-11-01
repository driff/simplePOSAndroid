package com.treefuerza.simplepos.ui.main.orders.open

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.models.Orders
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.orders_recycler.view.*
import javax.inject.Inject

class OrdersAdapter @Inject constructor(private var list: MutableList<Orders>) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    private val clickSubject = PublishSubject.create<Orders>()
    val clickEvent: Observable<Orders> = clickSubject

    fun add(order: Orders) {
        list.add(order)
        notifyItemInserted(list.size)
    }

    fun addAll(orders: List<Orders>) {
        this.list.addAll(orders)
        notifyDataSetChanged()
    }

    fun replaceAll(orders: List<Orders>) {
        this.list = orders.toMutableList()
        notifyDataSetChanged()
    }

    fun remove(pos: Int) {
        list.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.orders_recycler, parent, false)
        return OrdersViewHolder(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(list[position], position)
    }


    inner class OrdersViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(orders: Orders, pos: Int) {
            view.apply {
                imvTable.text = String.format("%3d", pos+1)
                txvPrice.text = String.format(context.getString(R.string.order_title), orders.client)
                txvTime.text = String.format(context.getString(R.string.order_start_date), orders.createdAt)
                txvTotal.text = String.format("$%4.2f", orders.total)
            }
            view.setOnClickListener { clickSubject.onNext(orders) }
        }

    }

}