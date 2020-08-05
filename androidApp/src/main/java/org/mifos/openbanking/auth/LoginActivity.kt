package org.mifos.openbanking.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.ActivityLoginBinding
import org.mifos.openbanking.navigation.NavigationActivity
import org.mifos.openbanking.viewModel.auth.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configView()
        initViewModel()
    }

    private fun configView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.clickHandler = this
        binding.shimmerLogin.hideShimmer()
    }

    private fun initViewModel() {
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    private fun observeAuthState(state: AuthState) {
        when (state) {
            is SuccessAuthState -> {
                onLoginSuccess()
            }

            is LoadingAuthState -> {
            }

            is ErrorAuthState -> {
                onLoginError(state.message)
            }
        }
    }

    private fun onLoginSuccess() {
        binding.shimmerLogin.hideShimmer()
        startActivity(Intent(this, NavigationActivity::class.java))
    }


    private fun onLoginError(message: String?) {
        binding.shimmerLogin.hideShimmer()
    }

    fun onLoginClicked(view: View) {
        binding.shimmerLogin.showShimmer(true)
        authViewModel.authStateLiveData.addObserver { observeAuthState(it) }
        authViewModel.loginClient(
            username = binding.etUsername.text.toString(),
            password = binding.etPassword.text.toString()
        )
    }
}