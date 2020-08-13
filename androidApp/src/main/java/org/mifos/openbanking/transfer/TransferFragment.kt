package org.mifos.openbanking.transfer

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.FragmentTransferBinding
import org.mifos.openbanking.viewModel.model.AccountModel
import org.mifos.openbanking.viewModel.transaction.*

class TransferFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTransferBinding
    private lateinit var createTransactionRequestViewModel: CreateTransactionRequestViewModel
    private lateinit var account: AccountModel

    companion object {
        private const val accountKey = "account"

        fun newInstance(account: AccountModel): TransferFragment {
            val args = Bundle()
            args.putString(accountKey, Gson().toJson(account))
            val fragment = TransferFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        account =
            Gson().fromJson(requireArguments().getString(accountKey), AccountModel::class.java)

        initBinding()

        createTransactionRequestViewModel =
            ViewModelProviders.of(this).get(CreateTransactionRequestViewModel::class.java)
        val supportedBanks = createTransactionRequestViewModel.getSupportedBanks()
        val bankNames = supportedBanks.map { it.shortName }.toTypedArray()
        val adapter: ArrayAdapter<String?> = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, bankNames
        )
        binding.etBank.setAdapter(adapter)
        binding.shimmerProceed.hideShimmer()

        return configDialog(savedInstanceState)
    }

    fun onProceedClicked(view: View) {
        binding.shimmerProceed.showShimmer(true)
        createTransactionRequestViewModel.createTransactionRequestStateLiveData.addObserver {
            observeTransferState(
                it
            )
        }
        val bankName = binding.etBank.text.toString()
        val supportedBanks = createTransactionRequestViewModel.getSupportedBanks()
        val destinationBankId = supportedBanks.find { it.shortName == bankName }!!.id
        createTransactionRequestViewModel.createTransactionRequest(
            account.bankId,
            account.accountId,
            destinationBankId,
            binding.etAccount.text.toString(),
            "EUR",
            binding.etAmount.text.toString().toDouble(),
            binding.etDescription.text.toString()
        )
    }

    private fun observeTransferState(state: CreateTransactionRequestState) {
        when (state) {
            is SuccessCreateTransactionRequestState -> {
                binding.shimmerProceed.hideShimmer()
                Toast.makeText(
                    requireContext(),
                    "Money transferred successfully",
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
            }

            is LoadingCreateTransactionRequestState -> {

            }

            is ErrorCreateTransactionRequestState -> {
                binding.shimmerProceed.hideShimmer()
                Toast.makeText(
                    requireContext(),
                    "Unable to transfer money",
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
            }
        }
    }

    private fun initBinding() {
        val view = layoutInflater.inflate(R.layout.fragment_transfer, null)
        binding = FragmentTransferBinding.inflate(layoutInflater, view as ViewGroup, false)
        binding.lifecycleOwner = this
        binding.clickHandler = this
        binding.account = account
    }

    private fun configDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        val dialog: BottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(binding.root)

        dialog.setOnShowListener { dialog2 ->
            val bottomSheetDialog = dialog2 as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
            val behavior: BottomSheetBehavior<*> =
                BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!)
            val layoutParams = bottomSheet.layoutParams
            if (layoutParams != null) {
                val displayMetrics = DisplayMetrics()
                (context as Activity?)!!.windowManager.defaultDisplay
                    .getMetrics(displayMetrics)
                layoutParams.height = displayMetrics.heightPixels
            }
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    fun onCancelClicked(view: View) {
        dismiss()
    }

}