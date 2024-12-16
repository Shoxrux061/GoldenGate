package com.golden.gate.ui.details

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.databinding.ScreenDetailsBinding
import com.golden.gate.ui.base.BaseFragment
import com.golden.gate.ui.details.fragments.FragmentsAdapter
import com.google.android.material.tabs.TabLayout

class DetailsScreen : BaseFragment(R.layout.screen_details) {

    private val binding by viewBinding(ScreenDetailsBinding::bind)
    private val args: DetailsScreenArgs by navArgs()

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {
        setDataFromArgs()
        setPager()
        setTabs()
        setActions()

    }

    private fun setActions() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setDataFromArgs() {

        if (args.data.status == 1) {
            binding.status.text = "Rented"
            binding.status.setTextColor(Color.GREEN)
        } else {
            binding.status.text = "Not Rented"
            binding.status.setTextColor(Color.RED)
        }

        binding.image.setImageURI(Uri.parse(args.data.image))
        binding.carName.text = args.data.name
        binding.carTitle.text = args.data.name
        binding.priceTitle.text = args.data.currentPrice.plus("$")
    }

    private fun setTabs() {
        val tabTitles = listOf("Current info", "Expenses", "History")

        tabTitles.forEachIndexed { _, title ->
            val tab = binding.tabLayout.newTab()
            val customView = LayoutInflater.from(context).inflate(R.layout.tab_item, null)
            val tabTitle = customView.findViewById<TextView>(R.id.tab_text_view_item)
            tabTitle.text = title
            tab.customView = customView
            binding.tabLayout.addTab(tab)
        }
    }

    private fun setPager() {
        val adapter = FragmentsAdapter(childFragmentManager, lifecycle, data = args.data)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> binding.viewPager.currentItem = 0
                    1 -> binding.viewPager.currentItem = 1
                    2 -> binding.viewPager.currentItem = 2
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }
}