package com.golden.gate.ui.on_board

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.core.adapter.OnBoardAdapter
import com.golden.gate.core.data.models.OnBoardModel
import com.golden.gate.core.extensions.changeScreen
import com.golden.gate.databinding.ScreenOnBoardBinding
import com.golden.gate.ui.base.BaseFragment

class OnBoardScreen : BaseFragment(R.layout.screen_on_board) {

    private val adapter by lazy { OnBoardAdapter() }
    private val binding by viewBinding(ScreenOnBoardBinding::bind)

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {

        setViewPagerData()
        setActions()

    }

    private fun setActions() {
        binding.btnNext.setOnClickListener {

            if (binding.viewPager.currentItem == 0) {
                binding.viewPager.currentItem += 1
            } else {
                findNavController().changeScreen(OnBoardScreenDirections.actionOnBoardScreenToMainScreen())
            }
        }
    }

    private fun setViewPagerData() {
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)
        val data = ArrayList<OnBoardModel>()
        data.add(
            OnBoardModel(
                title1 = "The ability to monitor and\nanalyze data",
                title2 = "Сustomize under your control",
                image = R.drawable.on_board_1
            )
        )
        data.add(
            OnBoardModel(
                title1 = "Save time and analyze\nyour success",
                title2 = "Сonvenient interaction with your funds",
                image = R.drawable.on_board_2
            )
        )
        adapter.setData(data)
    }
}
