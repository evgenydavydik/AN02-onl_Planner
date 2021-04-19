package io.techmeskills.an02onl_plannerapp.screen.splash

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.databinding.FragmentSplashBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : NavigationFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override val viewBinding: FragmentSplashBinding by viewBinding()

    private val viewModel: SplashViewModel by viewModel()



    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationRotateCenter : Animation = AnimationUtils.loadAnimation(
                this.context, R.anim.fragment_out);
        val myImageView : ImageView = viewBinding.root.findViewById(R.id.ic_splash);
        myImageView.startAnimation(animationRotateCenter)
        viewBinding.root.postDelayed({
            findNavController().navigateSafe(SplashFragmentDirections.toMainFragment())
        }, 5000)
    }

}