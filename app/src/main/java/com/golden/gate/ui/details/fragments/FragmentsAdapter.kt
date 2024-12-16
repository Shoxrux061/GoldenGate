package com.golden.gate.ui.details.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.golden.gate.core.room.RoomArticles

class FragmentsAdapter(fragmentManager: FragmentManager, ls: Lifecycle, val data: RoomArticles) :
    FragmentStateAdapter(fragmentManager, ls) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> CurrentInfoFragment.newInstance(data = data)
        1 -> ExpensesFragment()
        else -> HistoryFragment()
    }
}