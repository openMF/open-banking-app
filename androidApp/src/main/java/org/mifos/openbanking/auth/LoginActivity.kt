package org.mifos.openbanking.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import org.mifos.openbanking.R
import org.mifos.openbanking.common.viewModel.account.AccountViewModel
import org.mifos.openbanking.common.viewModel.auth.*
import org.mifos.openbanking.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configView()
        initViewModel()
    }

    private fun configView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.clickHandler = this
    }

    private fun initViewModel() {
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
    }

    private fun observeAuthState(state: AuthState) {
        when (state) {
            is SuccessAuthState -> {
                onLoginSuccess()
            }

            is LoadingAuthState -> {
                binding.tvProgress.text = "Logging in..."
            }

            is ErrorAuthState -> {
                onLoginError(state.message)
            }
        }
    }

    private fun onLoginSuccess() {
        binding.tvProgress.text = "Logged in"
        accountViewModel.fetchAccounts()
    }


    private fun onLoginError(message: String?) {
        binding.tvProgress.text = message
    }

    fun onLoginClicked(view: View) {
        authViewModel.authStateLiveData.addObserver { observeAuthState(it) }
        authViewModel.loginClient(
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString())
    }
}