package com.treefuerza.simplepos.ui.main.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerProductsComponent
import com.treefuerza.simplepos.ui.main.products.create.CreateProductFragment
import kotlinx.android.synthetic.main.products_fragment.*
import javax.inject.Inject


class ProductsFragment : Fragment() {

    //TODO: Fetch products and display them

    companion object {
        fun newInstance() = ProductsFragment()
    }

    @Inject
    lateinit var adapter: ProductsAdapter
            private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(context!=null){
            DaggerProductsComponent.builder()
                .appComponent(TreeApplication.get().component)
                .build().inject(this)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fabAddProduct.setOnClickListener {
            val bottomSheetFragment = CreateProductFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
    }

}
