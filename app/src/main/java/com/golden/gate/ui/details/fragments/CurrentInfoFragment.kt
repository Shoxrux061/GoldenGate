package com.golden.gate.ui.details.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.core.room.RoomArticles
import com.golden.gate.databinding.FragmentCurrentInfoBinding
import com.golden.gate.ui.base.BaseFragment
import com.golden.gate.ui.details.dialog.AddTenantDialog
import com.golden.gate.ui.details.dialog.EditCarDialog

class CurrentInfoFragment : BaseFragment(R.layout.fragment_current_info) {

    private val binding by viewBinding(FragmentCurrentInfoBinding::bind)
    private lateinit var dialogAddTenant: AddTenantDialog
    private lateinit var dialogEditCar: EditCarDialog
    private var data: RoomArticles? = null

    companion object {
        private const val ARG_DATA = "data"

        fun newInstance(data: RoomArticles): CurrentInfoFragment {
            val fragment = CurrentInfoFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(ARG_DATA, data)
            }
            return fragment
        }
    }

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {
        setData()
        dialogAddTenant = AddTenantDialog.newInstance(data!!)
        dialogEditCar = EditCarDialog.newInstance(data!!)

        setActions()

    }

    private fun setActions() {
        binding.btnAdd.setOnClickListener {

            if (data?.status == 1) {
                dialogEditCar.show(childFragmentManager, "EditCarDialog")
            } else {
                dialogAddTenant.show(parentFragmentManager.beginTransaction(), "AddTenantDialog")
            }
        }
    }

    private fun setData() {

        data = arguments?.getParcelable(ARG_DATA) as? RoomArticles
        binding.price.text = data?.currentPrice

        if (data?.status == 1) {
            binding.datesFrame.visibility = View.VISIBLE
            binding.datesText.text = data?.tenantDate
            binding.thirdView.visibility = View.VISIBLE
            binding.tenantName.text = data?.tenantName
            binding.btnAdd.text = "Edit"

        } else {
            binding.tenantName.text = "Nobody"
            binding.btnAdd.text = "Add Tenant"
            binding.datesFrame.visibility = View.GONE
            binding.thirdView.visibility = View.GONE
        }

    }
}