package com.studycode.mymoneytracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.OnBoardAdapter
import com.studycode.mymoneytracker.adapters.SliderList
import kotlinx.android.synthetic.main.activity_on_board.*

class OnBoardActivity : AppCompatActivity() {
    private lateinit var sliderAdapter: OnBoardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)

        loadSlider()
        showNextSlide()
    }

    private fun showNextSlide() {
        mbContinue.setOnClickListener {
            if (vpSlidePager.currentItem.plus(1) < sliderAdapter.itemCount) {
                vpSlidePager.currentItem += 1
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun loadSlider() {
        val sliderList = listOf(
            SliderList(
                getString(R.string.transactions),
                getString(R.string.first_bubble_description), R.drawable.ic_budget
            ),
            SliderList(
                getString(R.string.balance),
                getString(R.string.first_bubble_description), R.drawable.img
            ),
            SliderList(
                getString(R.string.app_name),
                getString(R.string.first_bubble_description), R.drawable.img
            )
        )

        val classyGoEffect = SpannableString(tvHeader.text.toString()).apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        this@OnBoardActivity,
                        R.color.black
                    )
                ),
                6, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        tvHeader.text = classyGoEffect

        sliderAdapter = OnBoardAdapter(sliderList)
        vpSlidePager.adapter = sliderAdapter
    }

    override fun onStart() {
        super.onStart()
        TabLayoutMediator(tvTabLayout, vpSlidePager) { _, _ -> }.attach()
    }
}

