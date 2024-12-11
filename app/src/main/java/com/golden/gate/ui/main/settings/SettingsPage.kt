package com.golden.gate.ui.main.settings

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.databinding.PageSettingsBinding
import com.golden.gate.ui.base.BaseFragment

class SettingsPage : BaseFragment(R.layout.page_settings) {

    private val binding by viewBinding(PageSettingsBinding::bind)

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}
