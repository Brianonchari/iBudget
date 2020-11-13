package com.studycode.mymoneytracker.onboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studycode.mymoneytracker.R

class IntroSliderAdapter(private val introSlide: List<IntroSlide>) :
    RecyclerView.Adapter<IntroSliderAdapter.IntroSliderViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSliderViewModel {
        return IntroSliderViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slider_item_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
       return  introSlide.size
    }

    override fun onBindViewHolder(holder: IntroSliderViewModel, position: Int) {
     holder.bind(introSlide[position])
    }

    inner class IntroSliderViewModel(view: View) : RecyclerView.ViewHolder(view) {
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)
        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlider)
        fun bind(introSlide: IntroSlide) {
            textTitle.text = introSlide.title
            textDescription.text = introSlide.description
            imageIcon.setImageResource(introSlide.icon)
        }
    }
}