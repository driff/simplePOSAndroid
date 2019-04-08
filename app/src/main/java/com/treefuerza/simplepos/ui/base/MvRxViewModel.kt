package com.treefuerza.simplepos.ui.base

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.treefuerza.simplepos.BuildConfig

abstract class MvRxViewModel<T: MvRxState> (initialState: T): BaseMvRxViewModel<T>(initialState, debugMode = BuildConfig.DEBUG)