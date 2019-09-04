package com.treefuerza.simplepos.utils

import android.app.Dialog
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewModelStore
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.treefuerza.simplepos.R
import java.util.*

abstract class RoundedBottomSheetDialogFragment : BottomSheetDialogFragment(), MvRxView {

//    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

    final override val mvrxViewId: String by lazy { mvrxPersistedViewId }

    private lateinit var mvrxPersistedViewId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        mvrxViewModelStore.restoreViewModels(this, savedInstanceState)
        mvrxPersistedViewId = savedInstanceState?.getString(PERSISTED_VIEW_ID_KEY) ?: this::class.java.simpleName + "_" + UUID.randomUUID().toString()
        super.onCreate(savedInstanceState)
    }

    /**
     * Fragments should override the subscriptionLifecycle owner so that subscriptions made after onCreate
     * are properly disposed as fragments are moved from/to the backstack.
     */
    override val subscriptionLifecycleOwner: LifecycleOwner
        get() = this.viewLifecycleOwnerLiveData.value ?: this

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvrxViewModelStore.saveViewModels(outState)
        outState.putString(PERSISTED_VIEW_ID_KEY, mvrxViewId)
    }

    override fun onStart() {
        super.onStart()
        // This ensures that invalidate() is called for static screens that don't
        // subscribe to a ViewModel.
        postInvalidate()
    }
}

private const val PERSISTED_VIEW_ID_KEY = "mvrx:persisted_view_id"