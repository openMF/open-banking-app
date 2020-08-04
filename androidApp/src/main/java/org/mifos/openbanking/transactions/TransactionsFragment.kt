package org.mifos.openbanking.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.FragmentTransactionsBinding

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding

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

//        binding.btnPay.setOnClickListener(View.OnClickListener {
//            binding.transactions.visibility = View.VISIBLE
//        })

        return binding.root
    }
}