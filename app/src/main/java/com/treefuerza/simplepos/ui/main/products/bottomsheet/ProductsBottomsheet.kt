package com.treefuerza.simplepos.ui.main.products.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerProductsBottomsheetComponent
import com.treefuerza.simplepos.di.modules.ProductsModule
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.ui.main.products.ProductsAdapter
import com.treefuerza.simplepos.ui.main.products.ProductsViewModel
import com.treefuerza.simplepos.utils.DRAWABLE_RIGHT
import com.treefuerza.simplepos.utils.OnItemClickListener
import com.treefuerza.simplepos.utils.RoundedBottomSheetDialogFragment
import com.treefuerza.simplepos.utils.handleItemSearch
import kotlinx.android.synthetic.main.products_fragment.*
import javax.inject.Inject

class ProductsBottomsheet(private val listener: OnItemClickListener<Item>) : RoundedBottomSheetDialogFragment(){

    private val TAG = this.javaClass.canonicalName

    override fun invalidate() = withState(viewModel) {
        when(it.products){
            is Success -> {
                Log.i(TAG, "Orders success")
                val ls = it.products.invoke()
                Log.i(TAG, "Products list Length: ${ls.size}")
                adapter.replaceAll(ls)
            }
        }
    }

    @Inject
    lateinit var adapter: ProductsAdapter
    private val viewModel: ProductsViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = inflater.inflate(R.layout.products_fragment, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        if(context!=null){
            DaggerProductsBottomsheetComponent.builder()
                .appComponent(TreeApplication.get().component)
                .productsModule(ProductsModule(listener))
                .build().inject(this)
            Log.d(TAG, "Dagger injected!")
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = GridLayoutManager(requireContext(), 3)
        list.adapter = adapter
        edtItemCode.setOnTouchListener { _, motionEvent -> handleItemSearch(motionEvent, this::onItemSearch) }
    }

    private fun onItemSearch(event: MotionEvent): Boolean {
        if(event.rawX >= (edtItemCode.right - edtItemCode.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
            // your action here

            return true
        }
        return false
    }

}