package org.mifos.openbanking.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.ItemAccountBinding
import org.mifos.openbanking.transactions.TransactionsFragment
import org.mifos.openbanking.transfer.TransferFragment
import org.mifos.openbanking.viewModel.model.AccountModel

class AccountsAdapter(private val activity: FragmentActivity) :
    RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    private var accountList: List<AccountModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_account,
                parent,
                false
            )
        )
    }

    fun setAccountList(accountList: List<AccountModel>) {
        this.accountList = accountList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(accountList[position])
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    inner class ViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clickHandler = this
        }

        fun bindItems(account: AccountModel) {
            binding.account = account
        }

        fun onTransferClicked(view: View) {
            TransferFragment.newInstance(binding.account!!)
                .show(this@AccountsAdapter.activity.supportFragmentManager, "Transfer")
        }

        fun onTransactionsClicked(view: View) {
            val navController = findNavController(activity, R.id.nav_host_fragment)
            val account = binding.account!!
            val args = Bundle()
            args.putString(TransactionsFragment.bankIdKey, account.bankId)
            args.putString(TransactionsFragment.accountIdKey, account.accountId)
            navController.navigate(R.id.navigation_transactions, args)
        }

    }
}