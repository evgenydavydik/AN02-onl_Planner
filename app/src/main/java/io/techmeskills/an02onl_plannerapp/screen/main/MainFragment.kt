package io.techmeskills.an02onl_plannerapp.screen.main

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentMainBinding
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : NavigationFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewBinding: FragmentMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModel()

    var count: Int = 0
        set(value) {
            field = value
            viewBinding.tvCount.text = field.toString()
        }

    var count_clone: Int = 0
        set(value) {
            field = value
            viewBinding.tvCountClone.text = field.toString()
        }

    var count_summ: Int = 0
        set(value) {
            field = value
            viewBinding.tvCountSumm.text = field.toString()
        }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnClicker.setOnClickListener {
            count++
        }

        viewBinding.btnClickerClone.setOnClickListener {
            count_clone++
        }

        viewBinding.btnClickerSumm.setOnClickListener {
            count_summ=count+count_clone
        }
    }
}