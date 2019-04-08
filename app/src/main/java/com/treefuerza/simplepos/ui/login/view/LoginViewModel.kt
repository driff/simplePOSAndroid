package com.treefuerza.simplepos.ui.login.view

import android.util.Log
import com.treefuerza.simplepos.ui.base.MvRxViewModel
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit


class LoginViewModel(initialState: LoginState) : MvRxViewModel<LoginState>(initialState) {

    init {
        subscribe{ state ->
            Log.d(LoginFragment.TAG, "The state is: $state")
        }

//        selectSubscribe(LoginState::temperature){
//            Log.d(LoginFragment.TAG, "The temperature is: $it")
//        }
//
//        asyncSubscribe(LoginState::temperature, onSuccess = {
//            Log.d(LoginFragment.TAG, "The temperature is: $it")
//        }, onFail = {
//            Log.e(LoginFragment.TAG, "Temperature failed with ", it)
//        })
    }

    fun updateEmail(email:String){
        setState { this.copy(email = email) }
    }

    fun updatePassword(password: String) {
        setState { this.copy(password = password) }
    }

//    fun fetchTemperature() {
//        Observable.just(72)
//            .delay(3, TimeUnit.SECONDS)
//            .execute { copy( temperature = it) }
//    }
}
