package com.studycode.mymoneytracker.ui.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.studycode.mymoneytracker.utils.Constants.REQUEST_IMAGE_CAPTURE
import com.studycode.mymoneytracker.utils.Constants.REQUEST_PERMISSION
import com.studycode.mymoneytracker.utils.Constants.REQUEST_PICK_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.fragment_add_income.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddIncomeFragement : Fragment(R.layout.fragment_add_income) {

    private val viewModel: MainViewModel by viewModels()
//    private val REQUEST_PERMISSION = 100
//    private val REQUEST_IMAGE_CAPTURE = 1
//    private val REQUEST_PICK_IMAGE = 2
    companion object {
        private const val TAG = "AddIncomeFragement"
    }

    private val mAppUnitId: String by lazy {
        "ca-app-pub-7628201468416367~8045665967"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkCameraPermission()
    }

    override fun onResume() {
        super.onResume()
        adview.resume()
        checkCameraPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkCameraPermission()
        saveIncome.setOnClickListener {
            if (payee_name.text!!.isEmpty() || amountEt.text!!.isEmpty()) {
                Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_LONG).show()
            } else {
                addIncome()
                findNavController().navigate(R.id.dashBoardFragment)
            }
        }

        btCapturePhoto.setOnClickListener{
            val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog,null)
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
        val receipt: Bitmap = (transaction_receipt.getDrawable() as BitmapDrawable).getBitmap()
        Log.d(TAG, "addIncome:$month $year ")

        val source_of_income = Income(income,receipt ,  amount.toFloat(), month, year)
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
            .build()
        adview.loadAd(adRequest)
    }


    override fun onPause() {
        super.onPause()
        adview.pause()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }

    private fun openCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap
                transaction_receipt.setImageBitmap(bitmap)
            } else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.getData()
                transaction_receipt.setImageURI(uri)
            }
        }
    }

}