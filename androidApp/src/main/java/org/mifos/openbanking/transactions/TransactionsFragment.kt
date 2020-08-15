package org.mifos.openbanking.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.FragmentTransactionsBinding
import org.mifos.openbanking.viewModel.transaction.*

class TransactionsFragment : Fragment() {

    private lateinit var transactionRequestsAdapter: TransactionRequestsAdapter
    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var transactionViewModel: TransactionViewModel

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

        transactionViewModel =
            ViewModelProviders.of(this).get(TransactionViewModel::class.java)

        if (arguments != null) {
            binding.tvSubtitle.visibility = View.VISIBLE
            binding.layoutTransactionSummary.visibility = View.GONE
            transactionViewModel.fetchTransactionStateLiveData.addObserver {
                observeFetchTransactionsState(
                    it
                )
            }
            transactionViewModel.fetchTransactionRequestsFor(
                requireArguments().getString(bankIdKey)!!,
                requireArguments().getString(accountIdKey)!!
            )

            binding.rvGeneral.layoutManager = LinearLayoutManager(context)
            binding.rvGeneral.setHasFixedSize(false)
            transactionRequestsAdapter = TransactionRequestsAdapter()
            binding.rvGeneral.adapter = transactionRequestsAdapter
        }

        return binding.root
    }

    private fun observeFetchTransactionsState(state: FetchTransactionState) {
        when (state) {
            is SuccessFetchTransactionState -> {
                transactionRequestsAdapter.setTransactionRequests(state.transactionRequestModelList)
            }

            is LoadingFetchTransactionState -> {
            }

            is ErrorFetchTransactionState -> {
            }
        }
    }

}