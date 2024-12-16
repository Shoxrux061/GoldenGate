package com.golden.gate.ui.details.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentsAdapter(fragmentManager: FragmentManager, ls: Lifecycle, val id: String) :
    FragmentStateAdapter(fragmentManager, ls) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> CurrentInfoFragment.newInstance(data = id)
        1 -> ExpensesFragment()
        else -> HistoryFragment()
    }
}