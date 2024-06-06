package com.amircodeing.syntaxinstitut.unique_store.presentation.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Auth
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.model.hashPassword
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.SessionState
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSignInBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.MainActivity
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeFragment
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
const val TAG = "Sign In Fragment"
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        viewModel.getProfile()
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)
        viewModel.setupUserNameInputField(binding.root)
        viewModel.setupPasswordInputField(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpFFTV.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.signUpFragment)
        }
        binding.recoveryPasswordTV.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.recoveryPasswordFragment)
        }
        binding.signInButtonGoogle.setOnClickListener {
            signInGoogle()
            //  viewModel.signInUserWithGoogleAccount(binding.root, requireContext(), this)

        }

        binding.customButtonSignIn.setOnClickListener {
            val auth = Auth(
                binding.signInUserName.getText().trim(),
                hashPassword(binding.signInPassword.getText().trim())
            )
           binding.signInProgressbar.visibility = View.VISIBLE
            binding.signInProgressbar2.visibility = View.VISIBLE
            if(viewModel.checkEmptyFieldInAuth(auth,requireContext())){
                viewModel.signIn(auth)
            viewModel.sessionState.observe(viewLifecycleOwner) { state ->
                val message = when (state) {
                    SessionState.LOGGED_IN -> {
                        viewModel.isLoggedIn
                       val  user = viewModel.userProfile.value
                                Toast.makeText(context, user.toString(), Toast.LENGTH_SHORT).show()

                        Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
                        "Login  been successfully"
                    }
                    SessionState.FAILED -> {
                        binding.signInProgressbar.visibility = View.GONE
                        binding.signInProgressbar2.visibility = View.GONE
                        "Your action failed!"
                    }
                    else -> throw NotImplementedError()
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            }


        }
        binding.signInProgressbar.visibility = View.GONE
        binding.signInProgressbar2.visibility = View.GONE


    }


    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }

    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account!= null){
                updateUI(account)
            }
        }else{

            Toast.makeText(context, "${task.exception}", Toast.LENGTH_LONG).show()

        }
    }

    private fun updateUI(account: GoogleSignInAccount) {

        val credential =  GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
            }else{
                Toast.makeText(context, "${task.exception}", Toast.LENGTH_LONG).show()
            }
        }
    }



}
