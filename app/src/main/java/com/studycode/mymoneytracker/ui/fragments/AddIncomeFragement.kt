package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Income
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_income.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddIncomeFragement : Fragment(R.layout.fragment_add_income) {
    private val viewModel: MainViewModel by viewModels()

    companion object {
        private const val TAG = "AddIncomeFragement"
    }

    private val mAppUnitId: String by lazy {
        "ca-app-pub-7628201468416367~8045665967"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveIncome.setOnClickListener {
            if (payee_name.text!!.isEmpty() || amountEt.text!!.isEmpty()) {
                Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_LONG).show()
            } else {
                addIncome()
                findNavController().navigate(R.id.dashBoardFragment)
            }
        }

        initializeBannerAd(mAppUnitId)
        loadBannerAd()

    }

    private fun addIncome() {
        val sdf = SimpleDateFormat("MMMM")
        val calendar = Calendar.getInstance()
        val income = payee_name.text.toString()
        val amount = amountEt.text.toString()
        val month = sdf.format(Date())
        val year = calendar.get(Calendar.YEAR)
        Log.d(TAG, "addIncome:$month $year ")

        val source_of_income = Income(income, amount.toFloat(), month, year)
        viewModel.addIncome(source_of_income)
        Snackbar.make(requireView(), "Source saved successfully", Snackbar.LENGTH_LONG).show()
        Log.d(TAG, "addIncome: $source_of_income")
    }

    private fun initializeBannerAd(appUnitId: String) {
        MobileAds.initialize(requireContext())
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("BBCA5E24BC5636FC66C9E085A1DB6C0A"))
                .build()
        )
    }

    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder()
//            .addTestDevice("BBCA5E24BC5636FC66C9E085A1DB6C0A")
            .build()
        ad_View.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        ad_View.resume()
    }

    override fun onPause() {
        super.onPause()
        ad_View.pause()
    }
}