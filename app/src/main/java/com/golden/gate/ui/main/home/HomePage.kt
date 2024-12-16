package com.golden.gate.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.core.adapter.HomeCarsAdapter
import com.golden.gate.core.extensions.changeScreen
import com.golden.gate.core.room.AppDataBase
import com.golden.gate.databinding.PageHomeBinding
import com.golden.gate.ui.base.BaseFragment
import com.golden.gate.ui.main.MainScreenDirections
import com.golden.gate.ui.main.home.dialogs.AddCarDialog


class HomePage : BaseFragment(R.layout.page_home) {

    private val binding by viewBinding(PageHomeBinding::bind)
    private lateinit var dialogAddCar: AddCarDialog
    private val adapter by lazy { HomeCarsAdapter(requireContext()) }
    private val room = AppDataBase.getInstance()


    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {
        dialogAddCar = AddCarDialog()
        dialogAddCar.onSaveListener = {
            setDataFromRoom()
        }
        setAdapter()
        setDataFromRoom()
        setActions()


    }

    private fun setAdapter() {
        binding.carsRecycler.adapter = adapter
        binding.carsRecycler.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setDataFromRoom() {
        if (room?.getCars()!!.isEmpty()) {
            binding.placeHolder.visibility = View.VISIBLE
        } else {
            binding.placeHolder.visibility = View.GONE
            adapter.setData(room.getCars())
        }
    }

    private fun setActions() {
        binding.btnAdd.setOnClickListener {
            dialogAddCar.show(childFragmentManager, "AddCarDialog")
        }
        adapter.onClickItem = {
            findNavController().changeScreen(MainScreenDirections.actionMainScreenToDetailsScreen(it))
        }
    }


}