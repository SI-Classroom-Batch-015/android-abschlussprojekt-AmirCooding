package com.amircodeing.syntaxinstitut.unique_store.presentation.recoverypassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amircodeing.syntaxinstitut.unique_store.R

class RecoveryPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = RecoveryPasswordFragment()
    }

    private lateinit var viewModel: RecoveryPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recovery_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecoveryPasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}