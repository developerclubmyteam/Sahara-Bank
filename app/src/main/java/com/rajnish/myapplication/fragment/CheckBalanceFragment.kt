package com.rajnish.myapplication.fragment



import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.rajnish.myapplication.R
import com.rajnish.myapplication.databinding.FragmentCheckBalanceBinding

class CheckBalanceFragment : Fragment(R.layout.fragment_check_balance) {


    private lateinit var binding: FragmentCheckBalanceBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckBalanceBinding.bind(view)

        // Inside CheckBalanceFragment
        val balanceAmount = arguments?.getString("balanceAmount")


        binding.balance.text = "Your Account Balance is \n $balanceAmount"



    }

}
