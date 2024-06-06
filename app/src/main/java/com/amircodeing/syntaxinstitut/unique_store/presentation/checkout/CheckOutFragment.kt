package com.amircodeing.syntaxinstitut.unique_store.presentation.checkout

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentCheckOutBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

class CheckOutFragment : Fragment() {
    private lateinit var binding: FragmentCheckOutBinding
    private val viewModel: CheckOutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        viewModel.getUserInfo()
        viewModel.getCartItems()
        CustomToolbar.setToolbar(
            ToolbarComponents(
                view = binding.root,
                title = "Check Out",
                visibility = false,
                backButtonVisibility = false, path = R.id.toolbar_checkOut,
                icon = null
            )
        )
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            val adapter = CheckOutAdapter()
            viewModel.items.observe(viewLifecycleOwner) { cart ->
                binding.subTotalPaymentTV.text =
                    String.format("%.2f €", viewModel.items.value?.subTotal)
                binding.shippingPrice.text =
                    String.format("%.2f €", viewModel.items.value?.shippingPrice)
                binding.totalPricePaymentTV.text =
                    String.format("%.2f €", viewModel.items.value?.totalCost)
                binding.recyclerView.adapter = adapter
                adapter.submitList(cart.items)

            }
            viewModel.user.observe(viewLifecycleOwner) { info ->
                paymentFullNameTV.text = info.fullName
                paymentStreetTV.text = info.address?.street
                paymentNumberTV.text = info.address?.number
                paymentZipTV.text = info.address?.zip
                paymentCityTV.text = info.address?.city
                paymentCountryTV.text = info.address?.country


            }

            binding.paymentPaypalButtom.setOnClickListener {
                // Create and show the first dialog
                val dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.loading_success_payment)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                // Handler to delay the display of the second dialog
                Handler(Looper.getMainLooper()).postDelayed({
                    dialog.dismiss() // Dismiss the first dialog after 5 seconds
                    val dialog2 = Dialog(requireContext())
                    dialog2.setContentView(R.layout.success_payment)
                    dialog2.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val backToHomeTextView: TextView = dialog2.findViewById(R.id.back_to_home)
                    backToHomeTextView.setOnClickListener {
                        // Dismiss the second dialog
                        dialog2.dismiss()
                        viewModel.deleteAllCart()
                        Navigation.findNavController(requireView()).navigate(R.id.homeFragment)
                    }
                    dialog2.show()
                }, 5000) // Delay in milliseconds
            }


        }
    }
}



