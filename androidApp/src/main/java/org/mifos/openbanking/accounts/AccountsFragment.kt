package org.mifos.openbanking.accounts

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.FragmentAccountsBinding
import org.mifos.openbanking.utils.dpToPx
import org.mifos.openbanking.utils.formatBalance
import org.mifos.openbanking.viewModel.account.*

class AccountsFragment : Fragment() {

    private val accountViewModel: AccountViewModel by activityViewModels()
    private lateinit var binding: FragmentAccountsBinding
    private lateinit var accountsAdapter: AccountsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_accounts,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.clickHandler = this

        binding.accountList.layoutManager =
            LinearLayoutManager(context)
        binding.accountList.setHasFixedSize(false)
        binding.accountList.addItemDecoration(AccountItemsDecoration())
        accountsAdapter = AccountsAdapter(requireActivity())
        binding.accountList.adapter = accountsAdapter

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        accountViewModel.accountStateLiveData.addObserver { observeAccountState(it) }
    }

    private fun observeAccountState(state: AccountState) {
        when (state) {
            is SuccessAccountState -> {
                accountsAdapter.setAccountList(state.accountList)
                if (state.balancesFetched) {
                    var totalBalance = 0.0
                    for (account in state.accountList) {
                        totalBalance += account.balance!!
                    }
                    binding.tvTotalBalance.text = formatBalance(totalBalance, true)
                    binding.tvFetchBalances.visibility = View.GONE
                    binding.tvTotalBalance.visibility = View.VISIBLE
                } else {
                    binding.tvFetchBalances.visibility = View.VISIBLE
                    binding.tvTotalBalance.visibility = View.GONE
                }
            }

            is LoadingAccountState -> {
            }

            is ErrorAccountState -> {
            }
        }
    }

    fun onFetchBalancesClicked(v: View) {
        accountViewModel.fetchBalances()
    }

    override fun onResume() {
        super.onResume()
        accountViewModel.fetchAccounts()
    }

    private class AccountItemsDecoration : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val itemPosition = parent.getChildAdapterPosition(view)
            outRect.top = if (itemPosition == 0) dpToPx(8F).toInt() else 0
            outRect.bottom = dpToPx(8F).toInt()
            outRect.left = dpToPx(15F).toInt()
            outRect.right = dpToPx(15F).toInt()
        }
    }
}