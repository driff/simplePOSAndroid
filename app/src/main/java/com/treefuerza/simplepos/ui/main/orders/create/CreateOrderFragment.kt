package com.treefuerza.simplepos.ui.main.orders.create

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.view.SupportMenuInflater
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.di.components.DaggerCreateOrdersComponent
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.utils.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_edit_order.*
import javax.inject.Inject


class CreateOrderFragment : BaseMvRxFragment(), OnItemClickListener<Item> {

    //TODO: SET This as EditOrder

    var bottomSheetFragment: BottomSheetDialogFragment? = null

    override fun invalidate() = withState(viewModel) {
//        when(it.item) {
//            is Success -> {
//                val item = it.item.invoke()
//                tilProduct.helperText = String.format("Product: %s", item.name)
//                edtProduct.setText(item.code)
//                edtQuantity.setText("1")
//                edtTotal.setText(String.format("$ %9.2f", item.price + (item.price * (item.taxValue/100))))
//            }
//        }
        when(it.order) {
            is Success -> {
                edtClient.setText(it.client)
                txvSubtotal.text = String.format(getString(R.string.txv_subtotal), it.order.invoke().subtotal)
                txvTotal.text = String.format(getString(R.string.txv_total), it.order.invoke().total)
            }
        }
        when(it.details) {
            is Success -> adapter.replaceAll(it.details.invoke())
        }
    }

    val TAG = this.javaClass.canonicalName

    @Inject
    lateinit var adapter: OrderDetailsAdapter
    private val viewModel: CreateOrderViewModel by fragmentViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        if (context != null) {
            DaggerCreateOrdersComponent.builder()
                .appComponent(TreeApplication.get().component)
                .build().inject(this)
        }
        Log.i(TAG,"Menu Visibility: $isMenuVisible")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.i(TAG, "onCreateOptionsMenu")
        inflater.inflate(R.menu.create_order_menu, menu)
        Log.i(TAG, "menuItems: ${menu.hasVisibleItems()}")
//        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_order, container, false)
        initView(view)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{

            //Using SafeArgs
            viewModel.loadOrder(CreateOrderFragmentArgs.fromBundle(it).ORDERID)
        }
        recyclerOrderDetails.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerOrderDetails.adapter = adapter
//        edtProduct.setOnTouchListener { _, motionEvent -> handleItemSearch(motionEvent, this::onItemSearch) }
        btnAddProduct.setOnClickListener(this::addDetailAction)
//        fabSaveOrder.setOnClickListener(this::saveOrder)
    }

    private fun saveOrder(view: View) {
        val client = edtClient.text.toString()
        viewModel.saveOrder(client)
    }

    private fun addDetailAction(view: View) {
        Log.d(TAG, "adding product")
//        val quantity = edtQuantity.text.toString().toDoubleOrNull()?: 1.0
//        viewModel.addDetail(quantity)
    }

    private fun onItemSearch(event: MotionEvent): Boolean {
//        if(event.rawX >= (edtProduct.right - edtProduct.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
//            bottomSheetFragment = ProductsBottomsheet(this)
//            bottomSheetFragment?.show(childFragmentManager, bottomSheetFragment?.tag)
//        }
        return false
    }

    private fun initView(view: View) {
        view.apply {

        }
    }

    override fun onClick(t: Item) {
        viewModel.setProduct(t)
        bottomSheetFragment?.dismiss()
        bottomSheetFragment = null
    }

    override fun onLongClick(t: Item): Boolean {
        return false
    }

}
