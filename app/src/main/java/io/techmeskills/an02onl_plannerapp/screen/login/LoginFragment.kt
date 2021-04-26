package io.techmeskills.an02onl_plannerapp.screen.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import io.techmeskills.an02onl_plannerapp.R
import io.techmeskills.an02onl_plannerapp.databinding.FragmentLoginBinding
import io.techmeskills.an02onl_plannerapp.models.User
import io.techmeskills.an02onl_plannerapp.support.NavigationFragment
import io.techmeskills.an02onl_plannerapp.support.navigateSafe
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : NavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override val viewBinding: FragmentLoginBinding by viewBinding()

    private val viewModel: LoginViewModel by viewModel()


    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.apply {
            alpha = 0f
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorLiveData.observe(this.viewLifecycleOwner) { errorText ->
            Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()
        }

        viewModel.logged.observe(this.viewLifecycleOwner) { logged ->
            if (logged) {
                findNavController().navigateSafe(LoginFragmentDirections.toMainFragment())
            } else {
                viewBinding.root.alpha = 1f
            }
        }

        viewBinding.btnLogin.setOnClickListener {
            viewModel.login(viewBinding.etUser.text.toString())
        }

        viewModel.autoCompleteUsersLiveData.observe(this.viewLifecycleOwner) { names ->
            val namesHintAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(), android.R.layout.select_dialog_item, names
            )
            viewBinding.etUser.setAdapter(namesHintAdapter)
        }

    }
}