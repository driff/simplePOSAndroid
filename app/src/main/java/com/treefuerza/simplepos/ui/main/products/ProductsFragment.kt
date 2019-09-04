package com.treefuerza.simplepos.ui.main.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        list.adapter = adapter
        list.layoutManager = GridLayoutManager(requireContext(), 3)
        edtItemCode.setOnTouchListener(this::handleItemSearch)
        fabAddProduct.setOnClickListener(this::addProductListener)
    }

    private fun addProductListener(v: View) {
        val bottomSheetFragment = CreateProductFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    private fun handleItemSearch(view: View, event: MotionEvent): Boolean {
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3
        if(event.action == MotionEvent.ACTION_UP) {
            if(event.rawX >= (edtItemCode.right - edtItemCode.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                // your action here
                Toast.makeText(requireContext(), "Works!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }
}
