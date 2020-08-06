package org.mifos.openbanking.transfer

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.mifos.openbanking.R
import org.mifos.openbanking.databinding.FragmentTransferBinding

class TransferFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTransferBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.fragment_transfer, null)
        binding = FragmentTransferBinding.inflate(layoutInflater, view as ViewGroup, false)
        binding.lifecycleOwner = this
        binding.clickHandler = this

        val dialog: BottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(binding.root)
        return dialog
    }

    fun onCancelClicked(view: View) {
        dismiss()
    }

}