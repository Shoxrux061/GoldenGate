package com.golden.gate.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.golden.gate.ui.main.home.HomePage
import com.golden.gate.ui.main.payments.PaymentsPage
import com.golden.gate.ui.main.settings.SettingsPage
import com.golden.gate.ui.main.statistics.StatisticPage

class MainAdapter(fragmentManager: FragmentManager, ls: Lifecycle) :
    FragmentStateAdapter(fragmentManager, ls) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomePage()
        1 -> StatisticPage()
        2 -> PaymentsPage()
        else -> SettingsPage()
    }
}