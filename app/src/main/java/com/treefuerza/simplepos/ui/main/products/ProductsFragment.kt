package com.treefuerza.simplepos.ui.main.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
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
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerProductsComponent
import com.treefuerza.simplepos.ui.main.products.create.CreateProductFragment
import kotlinx.android.synthetic.main.products_fragment.*
import kotlinx.android.synthetic.main.products_fragment.view.*
import javax.inject.Inject


class ProductsFragment : BaseMvRxFragment() {

    val TAG = this.javaClass.canonicalName

    override fun invalidate() = withState(viewModel) {
        when(it.products){
            is Success -> {
                Log.i(TAG, "Orders success")
                val ls = it.products.invoke()
                Log.i(TAG, "Products list Length: ${ls.size}")
                adapter.replaceAll(ls)
            }
            else -> {
                Log.w(TAG, it.products.toString())
                Snackbar.make(parent, "State: ${it.products}", Snackbar.LENGTH_LONG).show()
            }
        }
    }


    @Inject
    lateinit var adapter: ProductsAdapter
    val viewModel: ProductsViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.products_fragment, container, false)
        v.list.adapter = adapter
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(context!=null){
            DaggerProductsComponent.builder()
                .appComponent(TreeApplication.get().component)
                .build().inject(this)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fabAddProduct.setOnClickListener {
            val bottomSheetFragment = CreateProductFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            list.adapter = adapter
        }
    }

}
