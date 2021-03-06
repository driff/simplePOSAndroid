package com.treefuerza.simplepos.ui.main.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerProductsComponent
import com.treefuerza.simplepos.di.modules.ProductsModule
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.ui.main.products.create.CreateProductFragment
import com.treefuerza.simplepos.utils.DRAWABLE_RIGHT
import com.treefuerza.simplepos.utils.OnItemClickListener
import com.treefuerza.simplepos.utils.handleItemSearch
import kotlinx.android.synthetic.main.products_fragment.*
import kotlinx.android.synthetic.main.products_fragment.view.*
import javax.inject.Inject


class ProductsFragment : BaseMvRxFragment(), OnItemClickListener<Item> {

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
    val viewModel: ProductsViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.products_fragment, container, false)
        view.list.adapter = adapter
        view.list.layoutManager = GridLayoutManager(requireContext(), 3)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(context!=null){
            DaggerProductsComponent.builder()
                .appComponent(TreeApplication.get().component)
                .productsModule(ProductsModule(this))
                .build().inject(this)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        edtItemCode.setOnTouchListener { _, motionEvent -> handleItemSearch(motionEvent, this::onItemSearch)}
        fabAddProduct.setOnClickListener(this::addProductListener)
    }

    private fun addProductListener(v: View) {
        val bottomSheetFragment = CreateProductFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    private fun onItemSearch(event: MotionEvent): Boolean {
        if(event.rawX >= (edtItemCode.right - edtItemCode.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
            // your action here
            return true
        }
        return false
    }

    override fun onClick(t: Item) {

    }

    override fun onLongClick(t: Item): Boolean {
        return false
    }

}
