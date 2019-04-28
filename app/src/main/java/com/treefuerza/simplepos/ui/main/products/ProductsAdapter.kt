package com.treefuerza.simplepos.ui.main.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.models.Item
import kotlinx.android.synthetic.main.items_recycler.view.*
import javax.inject.Inject

class ProductsAdapter @Inject constructor(private var list: MutableList<Item>) : RecyclerView.Adapter<ProductsAdapter.ItemViewHolder>() {

    fun add(item: Item) {
        list.add(item)
        notifyItemInserted(list.size)
    }

    fun addAll(items: List<Item>) {
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    fun replaceAll(items: List<Item>) {
        this.list = items.toMutableList()
        notifyDataSetChanged()
    }

    fun remove(pos: Int) {
        list.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_recycler, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(items: Item) {
            view.apply {
                txvPrice.text = String.format("$%.2f", items.price)
                txvName.text = items.name
                txvItemCode.text = "Code: ${items.code}"
            }
        }

    }

}