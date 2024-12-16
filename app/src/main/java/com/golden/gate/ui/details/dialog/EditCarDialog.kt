package com.golden.gate.ui.details.dialog

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.core.room.RoomArticles
import com.golden.gate.databinding.DialogEditCarBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditCarDialog : BottomSheetDialogFragment() {

    private val binding by viewBinding(DialogEditCarBinding::bind)

    companion object {
        private const val ARG_DATA = "data"

        fun newInstance(data: RoomArticles): EditCarDialog {
            val fragment = EditCarDialog()
            fragment.arguments = Bundle().apply {
                putParcelable(ARG_DATA, data)
            }
            return fragment
        }
    }

    private var data: RoomArticles? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_edit_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setActions()

    }

    private fun setActions() {

        binding.btnSave.setOnClickListener {
            isDataFull()
        }

    }

    private fun isDataFull(): Boolean {

        return false
    }

    private fun setData() {
        data = arguments?.getParcelable(ARG_DATA)
        binding.nameEdt.setText(data?.name)
        binding.priceEdt.setText(data?.currentPrice)
        binding.dateEdt.setText(data?.tenantDate)
        binding.tenantNameEdt.setText(data?.tenantName)
        binding.carImage.setImageURI(Uri.parse(data?.image))
    }
}