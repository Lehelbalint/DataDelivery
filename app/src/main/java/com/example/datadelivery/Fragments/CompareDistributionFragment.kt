package com.example.datadelivery.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.datadelivery.Data_G
import com.example.datadelivery.R
import com.example.datadelivery.ViewModel.SharedChartsViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class CompareDistribution : DialogFragment() {

    private lateinit var barChart: BarChart
    private lateinit var dataset1: BarDataSet
    private lateinit var dataset2: BarDataSet

    val sharedChartsViewModel : SharedChartsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_compare_distribution, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barChart = view.findViewById(R.id.bar_chart)
        setupChart()
    }

    private fun setupChart() {
        val entries1 = mutableListOf<BarEntry>()
        val entries2 = mutableListOf<BarEntry>()

        val gradeCounts1 = calculateGradeCounts(sharedChartsViewModel.GradesHistogram)
        val gradeCounts2 = calculateGradeCounts(sharedChartsViewModel.GradeHistogram2)

        for (i in gradeCounts1.indices) {
            val gradeCount = gradeCounts1[i]
            entries1.add(BarEntry(i.toFloat(), gradeCount.toFloat()))
        }

        for (i in gradeCounts2.indices) {
            val gradeCount = gradeCounts2[i]
            entries2.add(BarEntry(i.toFloat(), gradeCount.toFloat()))
        }

        dataset1 = BarDataSet(entries1, "Grade Distribution 1")
        dataset1.color = setColor("#FF0000") // Egyik szín
        dataset2 = BarDataSet(entries2, "Grade Distribution 2")
        dataset2.color = setColor("#00FF00") // Másik szín

        val data = BarData(dataset1, dataset2)
        data.barWidth = 0.35f // Szélesség beállítása

        barChart.data = data
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)
        barChart.groupBars(0f, 0.08f, 0.02f) // Csoportosítás beállítása
        barChart.animateY(1000)
    }

    private fun setColor(color: String): Int {
        val red = Integer.parseInt(color.substring(1, 3), 16)
        val green = Integer.parseInt(color.substring(3, 5), 16)
        val blue = Integer.parseInt(color.substring(5, 7), 16)
        return Color.rgb(red, green, blue)
    }

    private fun calculateGradeCounts(grades: List<Data_G>): List<Int> {
        val gradeCounts = IntArray(11)

        for (grade in grades) {
            if (grade.attributes.grade in 1..10) {
                gradeCounts[grade.attributes.grade]++
            }
        }

        return gradeCounts.toList()
    }
}