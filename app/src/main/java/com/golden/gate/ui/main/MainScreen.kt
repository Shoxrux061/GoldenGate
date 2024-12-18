package com.golden.gate.ui.main

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.databinding.ScreenMainBinding
import com.golden.gate.ui.base.BaseFragment

class MainScreen : BaseFragment(R.layout.screen_main) {

    private val binding by viewBinding(ScreenMainBinding::bind)
    private var doubleBackToExitPressedOnce = false

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {

        setView()
        setPager()
        onBackPressed()

    }

    private fun setView() {

        val layoutParamsStatusBar = binding.viewPager.layoutParams as ViewGroup.MarginLayoutParams
        layoutParamsStatusBar.topMargin = getNavigationBarHeight("status_bar_height").plus(10)
        binding.viewPager.layoutParams = layoutParamsStatusBar

        val layoutParamsNavBar = binding.bottomAppBar.layoutParams as ViewGroup.MarginLayoutParams
        layoutParamsNavBar.bottomMargin = getNavigationBarHeight("navigation_bar_height").plus(5)
        binding.bottomAppBar.layoutParams = layoutParamsNavBar

    }

    private fun getNavigationBarHeight(bar: String): Int {
        val resources: Resources = requireContext().resources

        val resName =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                bar
            } else {
                "${bar}_landscape"
            }

        val id: Int = resources.getIdentifier(resName, "dimen", "android")

        return if (id > 0) {
            resources.getDimensionPixelSize(id)
        } else {
            0
        }
    }

    private fun setPager() {
        val adapter = MainAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeID -> {
                    binding.viewPager.setCurrentItem(0, true)
                }

                R.id.statisticID -> {
                    binding.viewPager.setCurrentItem(1, true)
                }

                R.id.paymentsID -> {
                    binding.viewPager.setCurrentItem(2, true)
                }

                else -> {
                    binding.viewPager.setCurrentItem(3, true)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentItem = binding.viewPager.currentItem
                    val navCurrentItem = binding.bottomNavigation.selectedItemId
                    if (currentItem > 0) {
                        binding.viewPager.currentItem--
                        when (navCurrentItem) {
                            R.id.settingsID -> {
                                binding.bottomNavigation.selectedItemId = R.id.paymentsID
                            }

                            R.id.paymentsID -> {
                                binding.bottomNavigation.selectedItemId = R.id.statisticID
                            }

                            R.id.statisticID -> {
                                binding.bottomNavigation.selectedItemId = R.id.homeID
                            }
                        }
                    } else {
                        if (doubleBackToExitPressedOnce) {
                            requireActivity().finish()
                            return
                        }

                        doubleBackToExitPressedOnce = true
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.double_tap),
                            Toast.LENGTH_SHORT
                        ).show()

                        Handler().postDelayed({
                            doubleBackToExitPressedOnce = false
                        }, 2000)
                    }
                }
            })
    }
}


