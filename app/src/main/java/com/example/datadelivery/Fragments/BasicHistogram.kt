package com.example.datadelivery.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.datadelivery.R
import com.example.datadelivery.ViewModels.SharedChartsViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry



class BasicHistogram : DialogFragment() {

    private lateinit var barChart: BarChart
    val sharedChartsViewModel : SharedChartsViewModel by activityViewModels ()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barChart = view.findViewById(R.id.bar_chart)
        setupChart()
    }

    private fun setupChart() {
        val entries = mutableListOf<BarEntry>()
        var counter = 0
        for (i in sharedChartsViewModel.GradesHistogram) {
            val grade = i.attributes.grade.toFloat()
            entries.add(BarEntry(counter.toFloat(), grade))
            counter += 1
        }



        val dataset = BarDataSet(entries, "Your Grades")
        dataset.color = setColor("#06d48f")
        val data = BarData(dataset)
        data.barWidth =0.5f

        barChart.data = data
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)
        barChart.animateY(1000)
    }
     fun setColor (color: String): Int {
        val hexColor = "#06d48f"

        val red = Integer.parseInt(hexColor.substring(1, 3), 16)
        val green = Integer.parseInt(hexColor.substring(3, 5), 16)
        val blue = Integer.parseInt(hexColor.substring(5, 7), 16)

         return Color.rgb(red, green, blue)

    }
}
