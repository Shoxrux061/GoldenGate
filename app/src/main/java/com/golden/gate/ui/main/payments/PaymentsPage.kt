package com.golden.gate.ui.main.payments

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.databinding.PagePaymentsBinding
import com.golden.gate.ui.base.BaseFragment

class PaymentsPage : BaseFragment(R.layout.page_payments) {

    private val binding by viewBinding(PagePaymentsBinding::bind)

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}
