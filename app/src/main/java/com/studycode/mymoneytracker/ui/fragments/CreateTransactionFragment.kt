package com.studycode.mymoneytracker.ui.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Transactions
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import com.studycode.mymoneytracker.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.fragment_add_income.*
import kotlinx.android.synthetic.main.fragment_create_transaction.*
import kotlinx.android.synthetic.main.fragment_create_transaction.adview
import kotlinx.android.synthetic.main.fragment_create_transaction.payee_container
import kotlinx.android.synthetic.main.fragment_create_transaction.transaction_receipt
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateTransactionFragment : Fragment(R.layout.fragment_create_transaction) {
    private val viewModel: MainViewModel by viewModels()
    val args: CreateTransactionFragmentArgs by navArgs()

    companion object {
        private const val TAG = "CreateTransactionFragme"
    }

    private val mAppUnitId: String by lazy {
        "ca-app-pub-7628201468416367~8045665967"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val budget = args.budget
//        val transactionName = transaction.text.toString()
        val budgetAmount = budget.amount
        val sdf = SimpleDateFormat("dd/M/yyyy  hh:mm:ss")
        val currentDate = sdf.format(Date())

        loadBannerAd()
        initializeBannerAd(mAppUnitId)

        payee_container.text = budget.category
        btn_save_transaction.setOnClickListener {
            if (!TextUtils.isEmpty(transaction.text)) {
                if (!TextUtils.isEmpty(transaction_amount.text)) {
                    val transactionAmount = transaction_amount.text.toString().toFloat()
                    val transactionName = transaction.text.toString()
                    if (transactionAmount < budgetAmount) {
                        val receipt: Bitmap =
                            (transaction_receipt.getDrawable() as BitmapDrawable).getBitmap()
                        val transaction = Transactions(
                            transactionName,
                            transactionAmount.toString().toFloat(),
                            currentDate,
                            receipt
                        )
                        viewModel.saveTransaction(transaction)
                        Snackbar.make(requireView(), "Transaction saved", Snackbar.LENGTH_LONG)
                            .show()
                        updateBudget()
                        findNavController().navigate(R.id.transactionsFragment)
                    } else {
                        Snackbar.make(requireView(), "Insufficient Balance", Snackbar.LENGTH_LONG)
                            .show()
                    }
                } else {
                    Snackbar.make(requireView(), "This Field cant be empty", Snackbar.LENGTH_LONG)
                        .show()
                }
            } else {
                Snackbar.make(requireView(), "This Field cant be empty", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        btnCapturePhoto.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog, null)
            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(mDialogView)
                .setTitle("Choose Image")
            val mAlertDialog = dialogBuilder.show()

            mDialogView.gallery_tv.setOnClickListener {
                mAlertDialog.dismiss()
                openGallery()
            }
            mDialogView.camera_tv.setOnClickListener {
                openCamera()
                mAlertDialog.dismiss()
            }
        }
    }

    private fun updateBudget() {
        val _budget = args.budget
        val budgetName = payee_container.text.toString()
        val amount = transaction_amount.text
        val balance = (_budget.amount - amount.toString().toFloat())
        Log.d(TAG, "updateBudget: $balance")
        if (budgetName.isEmpty() || amount.isNullOrEmpty()) {
            Snackbar.make(requireView(), "All Fields are required", Snackbar.LENGTH_LONG).show()
        } else {
            val budget = Budget(budgetName, _budget.amount, amount.toString().toFloat(), balance)
            viewModel.updateBudget(budget)
            Log.d(TAG, "updateBudget: $budget")
//            _budget.id?.let { viewModel.updateBudget(budget) }
//            val id = _budget.id
//            if (_budget.id == id) {
//                viewModel.updateBudget(budget)
//            } else {
//                Toast.makeText(requireContext(), "Unable to update", Toast.LENGTH_LONG).show()
//            }
        }
    }

    private fun initializeBannerAd(appUnitId: String) {
        MobileAds.initialize(requireContext())
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("BBCA5E24BC5636FC66C9E085A1DB6C0A"))
                .build()
        )
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Constants.REQUEST_PICK_IMAGE)
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(intent, Constants.REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                Constants.REQUEST_PERMISSION
            )
        }
    }

    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder()
//            .addTestDevice("BBCA5E24BC5636FC66C9E085A1DB6C0A")
            .build()
        adview.loadAd(adRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap
                transaction_receipt.setImageBitmap(bitmap)
            } else if (requestCode == Constants.REQUEST_PICK_IMAGE) {
                val uri = data?.getData()
                transaction_receipt.setImageURI(uri)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adview.resume()
        checkCameraPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkCameraPermission()
    }

}