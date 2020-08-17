package org.mifos.openbanking.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.FragmentTransactionsBinding
import org.mifos.openbanking.utils.formatAccount
import org.mifos.openbanking.viewModel.transaction.*

class TransactionsFragment : Fragment() {

    private lateinit var transactionRequestsAdapter: TransactionRequestsAdapter
    private lateinit var binding: FragmentTransactionsBinding
    private val transactionViewModel: TransactionViewModel by activityViewModels()

    companion object {
        const val bankIdKey = "bankId"
        const val accountIdKey = "accountId"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transactions,
            container,
            false
        )
        binding.lifecycleOwner = this

        if (arguments != null) {
            binding.tvSubtitle.visibility = View.VISIBLE

            val bankId = requireArguments().getString(bankIdKey)!!
            val accountId = requireArguments().getString(accountIdKey)!!
            binding.tvAccount.text = formatAccount(bankId, accountId)
            binding.tvAccount.visibility = View.VISIBLE

            binding.layoutTransactionSummary.visibility = View.GONE

            binding.rvGeneral.layoutManager = LinearLayoutManager(context)
            binding.rvGeneral.setHasFixedSize(false)
            transactionRequestsAdapter = TransactionRequestsAdapter()
            binding.rvGeneral.adapter = transactionRequestsAdapter

            transactionViewModel.fetchTransactionStateLiveData.addObserver {
                observeFetchTransactionsState(
                    it
                )
            }
            transactionViewModel.fetchTransactionRequestsFor(
                bankId,
                accountId
            )
        }

        return binding.root
    }

    private fun observeFetchTransactionsState(state: FetchTransactionState) {
        when (state) {
            is SuccessFetchTransactionState -> {
                transactionRequestsAdapter.setTransactionRequests(state.transactionRequestModelList)
                if (state.transactionRequestModelList.isEmpty()) {
                    binding.tvNoRequests.visibility = View.VISIBLE
                } else {
                    binding.tvNoRequests.visibility = View.GONE
                }
            }

            is LoadingFetchTransactionState -> {
            }

            is ErrorFetchTransactionState -> {
            }
        }
    }

}