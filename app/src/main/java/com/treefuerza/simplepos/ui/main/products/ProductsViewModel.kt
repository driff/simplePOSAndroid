package com.treefuerza.simplepos.ui.main.products

import com.airbnb.mvrx.*
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.ui.base.MvRxViewModel
import com.treefuerza.simplepos.ui.main.products.bottomsheet.calculateTotal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class ProductsState(val products: Async<List<Item>> = Uninitialized, val selectedProduct: Item? = null, val quantity: Double = 1.0, val total: Double = 0.0): MvRxState

class ProductsViewModel(initialState: ProductsState, private val repo: DataRepository) : MvRxViewModel<ProductsState>(initialState) {

    companion object: MvRxViewModelFactory<ProductsViewModel, ProductsState> {
        override fun create(viewModelContext: ViewModelContext, state: ProductsState): ProductsViewModel? {
            val repo = viewModelContext.app<TreeApplication>().component.dataRepository()
            return ProductsViewModel(state, repo)
        }
    }

    init {
        fetchProducts()
    }

    fun fetchProducts(){
        this.repo.getAllItems().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .execute { copy(products = it) }
    }

    fun setSelectedProduct(item: Item) {
        setState {
            copy(selectedProduct = item, total = calculateTotal(item.price, item.taxValue))
        }
    }

    fun setSelectedQuantity(n: Double) {
        withState {
            if (it.selectedProduct != null) {
                setState {
                    copy(quantity = n, total = calculateTotal(it.selectedProduct.price, it.selectedProduct.taxValue, n))
                }
            } else {
                setState { copy(total = 0.0, quantity = 1.0) }
            }
        }
    }

}
