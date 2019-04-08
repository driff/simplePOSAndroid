package com.treefuerza.simplepos.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.mvrx.BaseMvRxActivity
import com.treefuerza.simplepos.R

class LoginActivity : BaseMvRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(savedInstanceState == null){
            
        }
    }
}
