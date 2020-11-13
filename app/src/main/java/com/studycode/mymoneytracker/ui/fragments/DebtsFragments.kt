package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.DebtsRecyclerAdapter
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_debts.*
import java.util.*

@AndroidEntryPoint
class DebtsFragments : Fragment(R.layout.fragment_debts) {
    private val viewModel: MainViewModel by viewModels()
    lateinit var debtsRecyclerAdapter: DebtsRecyclerAdapter

    private val mAppUnitId: String by lazy {
        "ca-app-pub-7628201468416367~8045665967"
    }
    private val mInterstitialAdUnitId: String by lazy {
        "ca-app-pub-3940256099942544/1033173712"
    }
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        viewModel.debts.observe(viewLifecycleOwner, Observer {
            debtsRecyclerAdapter.submitList(it)
            val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    val adapter = recyclerView.adapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition

                    Collections.swap(it,from, to)
                    adapter?.notifyItemMoved(from, to)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                  val position = viewHolder.adapterPosition
                    val debt = debtsRecyclerAdapter.differ.currentList[position]
                    viewModel.deleteDebt(debt)
                    Snackbar.make(view, "Item Deleted", Snackbar.LENGTH_LONG).apply {
                        setAction("UNDO" ) {
                            viewModel.saveDebt(debt)
                        }
                        show()
                    }
                }
            }

            ItemTouchHelper(itemTouchHelper).apply {
                attachToRecyclerView(debtsRv)
            }
        })
        mInterstitialAd = InterstitialAd(requireContext())
        initializeInterstitialAd(mAppUnitId)
        loadInterstitialAd(mInterstitialAdUnitId)
        floatingButton.setOnClickListener{
            if(mInterstitialAd.isLoaded){
                mInterstitialAd.show()
            }else{
                findNavController().navigate(R.id.action_debtsFragments_to_addDebtsFragment)
            }
        }
        runAdEvents()
    }

    private fun setupRecyclerview() = debtsRv.apply {
        debtsRecyclerAdapter = DebtsRecyclerAdapter()
        adapter = debtsRecyclerAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initializeInterstitialAd(appUnitId: String) {
        MobileAds.initialize(requireContext(), appUnitId)
    }
    private fun loadInterstitialAd(interstitialAdUnitId: String) {
        mInterstitialAd.adUnitId = interstitialAdUnitId
        mInterstitialAd.loadAd(AdRequest.Builder()
            .addTestDevice("BBCA5E24BC5636FC66C9E085A1DB6C0A")
            .build())
    }

    private fun runAdEvents(){
        mInterstitialAd.adListener = object :AdListener(){
            override fun onAdClicked() {
                super.onAdClicked()
                mInterstitialAd.adListener.onAdClosed()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                findNavController().navigate(R.id.action_debtsFragments_to_addDebtsFragment)
            }
        }
    }
}