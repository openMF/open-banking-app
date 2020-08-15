package org.mifos.openbanking.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.ItemTransactionRequestBinding
import org.mifos.openbanking.utils.formatAccount
import org.mifos.openbanking.viewModel.model.TransactionRequestModel

class TransactionRequestsAdapter : RecyclerView.Adapter<TransactionRequestsAdapter.ViewHolder>() {

    private var transactionRequestsList: List<TransactionRequestModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_transaction_request,
                parent,
                false
            )
        )
    }

    fun setTransactionRequests(transactionRequestsList: List<TransactionRequestModel>) {
        this.transactionRequestsList = transactionRequestsList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(transactionRequestsList[position])
    }

    override fun getItemCount(): Int {
        return transactionRequestsList.size
    }

    inner class ViewHolder(private val binding: ItemTransactionRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(transactionRequestModel: TransactionRequestModel) {
            binding.request = transactionRequestModel
            binding.tvAccount.text =
                formatAccount(transactionRequestModel.toBankId, transactionRequestModel.toAccountId)
            binding.root.setOnClickListener {
                val tvDescription = binding.tvDescription
                if (tvDescription.visibility == View.VISIBLE) {
                    tvDescription.visibility = View.GONE
                } else {
                    tvDescription.visibility = View.VISIBLE
                }
            }
        }
    }
}