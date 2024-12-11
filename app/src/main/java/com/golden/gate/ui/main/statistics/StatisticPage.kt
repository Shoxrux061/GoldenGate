package com.golden.gate.ui.main.statistics

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.databinding.PageStatisticBinding
import com.golden.gate.ui.base.BaseFragment

class StatisticPage : BaseFragment(R.layout.page_statistic) {

    private val binding by viewBinding(PageStatisticBinding::bind)

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}
