package com.golden.gate.core.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.golden.gate.R

fun NavController.changeScreen(navDirections: NavDirections) {

    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in)
        .setExitAnim(R.anim.slide_out)
        .setPopEnterAnim(R.anim.slide_in_reverse)
        .setPopExitAnim(R.anim.slide_out_reverse)
        .build()

    navigate(navDirections, navOptions)
}