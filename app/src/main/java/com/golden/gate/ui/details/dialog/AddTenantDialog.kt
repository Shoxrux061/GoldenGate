package com.golden.gate.ui.details.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.core.room.AppDataBase
import com.golden.gate.core.room.RoomArticles
import com.golden.gate.databinding.DialogAddTenantBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTenantDialog : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_KEY = "arg_key"

        fun newInstance(data: RoomArticles): AddTenantDialog {
            return AddTenantDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_KEY, data)
                }
            }
        }
    }

    private val binding by viewBinding(DialogAddTenantBinding::bind)
    private val room = AppDataBase.getInstance()
    private var data: RoomArticles? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_tenant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActions()
        data = arguments?.getParcelable(ARG_KEY) as? RoomArticles

    }

    private fun setActions() {
        binding.btnSave.setOnClickListener {
            if (isDataFull()) {

                room?.addTenant(
                    id = data!!.id,
                    tenantDate = binding.dateEdt.text.toString(),
                    tenantName = binding.nameEdt.text.toString()
                )
                Log.d("TAGRoomData", "setActions: ${room?.getCars()}")
            }
        }

        binding.btnClose.setOnClickListener {
            clearData()
            dismiss()
        }
    }

    private fun clearData() {
        binding.dateEdt.text = null
        binding.nameEdt.text = null
    }

    private fun isDataFull(): Boolean {

        return if (binding.dateEdt.length() < 23) {
            binding.dateEdt.error = "Input Please"
            false
        } else if (binding.nameEdt.text.isBlank() || binding.nameEdt.length() < 3) {
            binding.nameEdt.error = "Input PLease"
            false
        } else {
            true
        }
    }

}