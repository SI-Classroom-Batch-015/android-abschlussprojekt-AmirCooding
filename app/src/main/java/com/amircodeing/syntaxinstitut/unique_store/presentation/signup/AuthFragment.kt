package com.amircodeing.syntaxinstitut.unique_store.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Auth
import com.amircodeing.syntaxinstitut.unique_store.data.model.hashPassword
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.SessionState
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentAuthBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility

class AuthFragment : Fragment() {

    private  lateinit var  binding : FragmentAuthBinding
    private  val viewModel  : SignUpViewModel by  viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      binding = FragmentAuthBinding.inflate(inflater, container, false)
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        viewModel.setViewOnAuthInput(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.customButtonCreateAccount.setOnClickListener {

                   val auth = Auth(
                       "${binding.signUpUserName.getText().trim()}@gmail.com",
                                   hashPassword(binding.signUpPassword.getText().trim())
                   )
                viewModel.signUp(auth)
                viewModel.sessionState.observe(viewLifecycleOwner) { state ->
                    val message = when (state) {
                        SessionState.REGISTERED -> {
                            Navigation.findNavController(binding.root).navigate(R.id.signUpFragment)
                            "You successfully registered!"
                        }
                        SessionState.FAILED -> "Your action failed!"
                        else -> throw NotImplementedError()
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }

    }

}