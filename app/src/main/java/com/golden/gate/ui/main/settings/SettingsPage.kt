package com.golden.gate.ui.main.settings

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.core.room.AppDataBase
import com.golden.gate.databinding.PageSettingsBinding
import com.golden.gate.ui.base.BaseFragment

class SettingsPage : BaseFragment(R.layout.page_settings) {

    private val binding by viewBinding(PageSettingsBinding::bind)
    private val room = AppDataBase.getInstance()

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {
        setActions()
    }

    private fun setActions() {
        binding.resetProgress.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle(getString(R.string.app_name))
        alertDialogBuilder.setMessage(getString(R.string.clear_progress))

        alertDialogBuilder.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
            context?.cacheDir?.deleteRecursively()
            context?.filesDir?.deleteRecursively()
            context?.getSharedPreferences("shared_prefs_name", Context.MODE_PRIVATE)?.edit()
                ?.clear()?.apply()
            requireContext().deleteDatabase("CarsCards")
            room?.clearCars()
            requireActivity().recreate()
            dialog.dismiss()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
