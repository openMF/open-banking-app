package org.mifos.openbanking.transfer

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.FragmentTransferBinding
import org.mifos.openbanking.viewModel.account.AccountViewModel
import org.mifos.openbanking.viewModel.model.AccountModel


class TransferFragment(private val account: AccountModel) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTransferBinding
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initBinding()

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        val supportedBanks = accountViewModel.getSupportedBanks()
        val bankNames = supportedBanks.map { it.shortName }.toTypedArray()
        val adapter: ArrayAdapter<String?> = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, bankNames
        )
        binding.etBank.setAdapter(adapter)

        return configDialog(savedInstanceState)
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