package com.treefuerza.simplepos.ui.login.view

import android.util.Log
import com.airbnb.mvrx.*
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.models.User
import com.treefuerza.simplepos.ui.base.MvRxViewModel
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

data class LoginState(@PersistState val userName: String = "",
                      @PersistState val password: String = "",
                      @PersistState val passwordConf: String = "",
                      val login: Async<Boolean> = Uninitialized,
                      val createUser: Async<Boolean> = Uninitialized,
                      @PersistState val email: String = "") : MvRxState

class LoginViewModel(initialState: LoginState, val repo: DataRepository) : MvRxViewModel<LoginState>(initialState) {

    companion object: MvRxViewModelFactory<LoginViewModel, LoginState> {
        override fun create(viewModelContext: ViewModelContext, state: LoginState): LoginViewModel? {
            val repo = viewModelContext.app<TreeApplication>().component.userRepository()
            return LoginViewModel(state, repo)
        }
    }

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

    fun updateName(name: String) {
        setState { this.copy(userName = name) }
    }

    fun updatePasswordConfirm(passConf: String) {
        setState { this.copy(passwordConf = passConf) }
    }

    fun createUser(){
        withState {state ->
            val user = User(UUID.randomUUID().toString(), state.userName, state.password, state.email)
            repo.addUser(user)
            Observable.just(true).execute { copy(createUser = it) }
        }
    }

    fun login() {
        withState {
            repo.findUserByEmail(it.email)
                .map { user -> it.password == user.password }
                .delay(2, TimeUnit.SECONDS)
                .execute { res -> copy(login = res) }

        }
    }
//    fun fetchTemperature() {
//        Observable.just(72)
//            .delay(3, TimeUnit.SECONDS)
//            .execute { copy( temperature = it) }
//    }
}
