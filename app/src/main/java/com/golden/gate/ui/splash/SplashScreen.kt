package com.golden.gate.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.golden.gate.R
import com.golden.gate.core.cache.LocalStorage
import com.golden.gate.core.extensions.changeScreen
import com.golden.gate.databinding.ScreenSplashBinding
import com.golden.gate.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : BaseFragment(R.layout.screen_splash) {

    override fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000)
            if (LocalStorage.getObject().getIsFirst()) {
                LocalStorage.getObject().setIsFirst()
                findNavController().changeScreen(SplashScreenDirections.actionSplashScreenToOnBoardScreen())
            } else {
                findNavController().changeScreen(SplashScreenDirections.actionSplashScreenToMainScreen())
            }
        }

    }

}
