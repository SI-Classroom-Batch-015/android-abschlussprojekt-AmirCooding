package com.amircodeing.syntaxinstitut.unique_store.presentation.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSettingBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents
import com.amircodeing.syntaxinstitut.unique_store.utils.setToolbar

const val  TAG = "SettingFragment"
class SettingFragment : Fragment(R.layout.fragment_setting) {
    private lateinit var binding: FragmentSettingBinding
    private val viewModle  : SettingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

     setToolbar(
            ToolbarComponents(
                view = binding.root, screensTitle = "Setting", iconsVisibility = false,
                navigateUp = true,
                rootPath = R.id.toolbar_setting,
                iconPath = null
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      val adapter = SettingAdapter()
        binding.recyclerView2.adapter = adapter
      viewModle.settingElements.observe(viewLifecycleOwner){
          info ->
          adapter.submitList(info)
      }


        binding.customButtonSignout.setOnClickListener {
            viewModle.signOut()
            Toast.makeText(context, "Sign Out Successful", Toast.LENGTH_LONG).show()
            Navigation.findNavController(binding.root).navigate(R.id.signInFragment)

        }
    }
}