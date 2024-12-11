package com.golden.gate.ui.main.home

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.databinding.PageHomeBinding
import com.golden.gate.ui.base.BaseFragment


class HomePage : BaseFragment(R.layout.page_home) {

    private val binding by viewBinding(PageHomeBinding::bind)

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {


    }

}