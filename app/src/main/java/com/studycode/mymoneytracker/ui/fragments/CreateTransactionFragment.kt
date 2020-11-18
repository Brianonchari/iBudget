package com.studycode.mymoneytracker.ui.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Transactions
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import com.studycode.mymoneytracker.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_transaction.*
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CreateTransactionFragment : Fragment(R.layout.fragment_create_transaction) {
    private val viewModel: MainViewModel by viewModels()
    val args: CreateTransactionFragmentArgs by navArgs()

    companion object {
        private const val TAG = "CreateTransactionFragme"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val budget = args.budget
        val budgetAmount = budget!!.amount
        val sdf = SimpleDateFormat("dd/M/yyyy  hh:mm a")
        val currentDate = sdf.format(Date())
        payee_container.text = budget.category
        btn_save_transaction.setOnClickListener {
            if (!TextUtils.isEmpty(transaction.text)) {
                if (!TextUtils.isEmpty(transaction_amount.text)) {
                    val transactionAmount = transaction_amount.text.toString().toFloat()
                    val transactionName = transaction.text.toString()
                    val category = budget.category
                    if (transactionAmount < budgetAmount) {
                        val receipt: Bitmap =
                            (transaction_receipt.getDrawable() as BitmapDrawable).getBitmap()
                        val transaction = Transactions(
                            transactionName,
                            transactionAmount.toString().toFloat(),
                            category,
                            receipt,
                            currentDate
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
            showCameraDialog()
        }
    }

    private fun showCameraDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Open Camera?")
            .setMessage("Open camera to take a photo of your receipt")
            .setIcon(R.drawable.ic_baseline_camera_alt_24)
            .setPositiveButton("Yes") { _, _ ->
                openCamera()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
        dialog.show()
    }

    private fun updateBudget() {
        val _budget = args.budget
        val bAmount = _budget!!.balance
        val amountSpent = transaction_amount.text.toString().toFloat()
        val balance = (bAmount - amountSpent)
        _budget.balance = balance
        _budget.amountSpent = amountSpent
        viewModel.updateBudget(_budget)
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
        checkCameraPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkCameraPermission()
    }
}