package com.treefuerza.simplepos.ui.main.products.create


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.treefuerza.simplepos.R
import com.treefuerza.simplepos.utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_create_product.*
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
        fabInsertProduct.setOnClickListener { viewModel.createItem(edtProdName.text.toString(),
            edtProdCode.text.toString(), edtProdPrice.text.toString().toDouble(), edtProdTax.text.toString().toDouble())
        }
//        viewModel.setName(edtProdName.afterTextChangeEvents().skipInitialValue()
//            .skip(500, TimeUnit.MILLISECONDS).map { edtProdName.text?.toString()?: "" })
//        viewModel.setCode(edtProdName.afterTextChangeEvents().skip(500, TimeUnit.MILLISECONDS).map { it.editable?.toString()?: "" })
//        viewModel.setPrice(edtProdName.afterTextChangeEvents().skip(500, TimeUnit.MILLISECONDS).map { it.editable?.toString()?.toDouble()?: 0.0})
//        viewModel.setTax(edtProdName.afterTextChangeEvents().skip(500, TimeUnit.MILLISECONDS).map { it.editable?.toString()?.toDouble()?: 0.0})
    }
}
