package com.treefuerza.simplepos.ui.main.products.create


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_create_product.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 *
 */
class CreateProductFragment : RoundedBottomSheetDialogFragment() {

    override fun invalidate() = withState(viewModel){
        edtProdCode.setText(it.code)
        edtProdName.setText(it.name)
        edtProdPrice.setText(it.price.toString())
        edtProdTax.setText(it.tax.toString())
        when(it.done) {
            is Success -> if( it.done.invoke() ) this.dismiss()
        }
    }

    private val viewModel: CreateProductViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fabInsertProduct.setOnClickListener(this::addProductListener)
    }

    private fun addProductListener(view: View) {
        val name = edtProdName.text?.toString()?: ""
        val code = edtProdCode.text?.toString()?: ""
        val price = edtProdPrice.text?.toString()?.toDouble()?: 0.0
        val tax = edtProdTax.text?.toString()?.toDouble()?: 0.0
        if (price >= 0 && name.isNotEmpty() && code.isNotEmpty()) {
            viewModel.createItem(name, code, price, tax)
        } else {
            showToast(getString(R.string.add_product_error_msg))
        }
    }

    private fun showToast(msg: String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

}
