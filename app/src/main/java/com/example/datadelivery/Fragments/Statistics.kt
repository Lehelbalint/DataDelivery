package com.example.datadelivery.Fragments

import DistributionHistogram
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.Data_G
import com.example.datadelivery.Model.Data
import com.example.datadelivery.NotificationViewModel
import com.example.datadelivery.NotificationViewModelFactory
import com.example.datadelivery.ViewModel.SharedChartsViewModel
import com.example.datadelivery.databinding.FragmentStatisticsBinding

class Statistics : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationViewModel: NotificationViewModel
    val sharedChartsViewModel : SharedChartsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = NotificationViewModelFactory(DateDeliveryRepository())
        notificationViewModel = ViewModelProvider(this, factory).get(NotificationViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationViewModel.getGrades()
        notificationViewModel.gradeList.observe(viewLifecycleOwner)
        {
            val currentGradeList = notificationViewModel.gradeList.value?.let { it1 ->
                filterData(
                    it1.data,sharedChartsViewModel.department,sharedChartsViewModel.course,sharedChartsViewModel.year,sharedChartsViewModel.type)
            }
            if (currentGradeList != null) {
                Log.i("xxx-grades",currentGradeList.size.toString())
                binding.avg.text=calculateGradeAverage(currentGradeList).toString()
                binding.median.text = calculateMedianGrade(currentGradeList).toString()
                binding.passRate.text = calculatePassingRate(currentGradeList).toString()+"%"
            }
            else {
                binding.avg.text = ""
                binding.median.text = ""
                binding.passRate.text = ""
            }
            notificationViewModel.getRatings()
            notificationViewModel.ratings.observe(viewLifecycleOwner)
            {
                if (sharedChartsViewModel.course != "All") {
                    val currentCourseRatings =
                        notificationViewModel.ratings.value?.data?.filter { it -> it.attributes.course.data.attributes.name == sharedChartsViewModel.course }

                    binding.courseRating.rating =
                        calculateCourseRating(currentCourseRatings).toFloat()
                    binding.difficultyLevel.rating =
                        calculateCourseLevel(currentCourseRatings).toFloat()
                }
                else
                {
                    binding.courseRating.rating =
                        calculateCourseRating(notificationViewModel.ratings.value?.data).toFloat()
                    binding.difficultyLevel.rating =
                        calculateCourseLevel(notificationViewModel.ratings.value?.data).toFloat()
                }
            }
            binding.button3.setOnClickListener {
                if (currentGradeList != null) {
                    sharedChartsViewModel.GradesHistogram = currentGradeList
                }
                showChartDialog()
            }
            binding.button4.setOnClickListener {
                if (currentGradeList != null) {
                    sharedChartsViewModel.GradesHistogram = currentGradeList
                }
                showDistributionChartDialog()
            }
        }



    }
    private fun showChartDialog() {
        // Diagram ablakot létrehozása és megjelenítése
        val chartDialog = BasicHistogram()
        chartDialog.show(parentFragmentManager, "chart_dialog")
    }
    private fun showDistributionChartDialog()
    {
        val chartDialog = DistributionHistogram()
        chartDialog.show(parentFragmentManager, "chart_dialog")
    }
    fun filterData(dataList: List<Data_G>, filterDepartment: String, filterCourse: String, filterYear: String, filterIsFinal: String): List<Data_G> {
        return dataList.filter { data ->
            val departmentMatch = filterDepartment == "All" || data.attributes.department.data.attributes.department_name == filterDepartment
            val courseMatch = filterCourse == "All" || data.attributes.course.data.attributes.name == filterCourse
            val year = data.attributes.date?.substring(0, 4)
            val yearMatch = filterYear == "All" || year == filterYear
            val isFinalMatch = filterIsFinal == "All" || (filterIsFinal == "Final" && data.attributes.final) || (filterIsFinal == "Partual" && !data.attributes.final)

            departmentMatch && courseMatch && yearMatch && isFinalMatch
        }
    }

    private fun calculateGradeAverage(grades: List<Data_G>?): Double {
        val onlyGrades = grades?.map { it.attributes.grade }
        val sum = onlyGrades?.sum()
        val count = onlyGrades?.size
        if (sum != null) {
            if (count != null) {
                return if (count > 0) sum.toDouble() / count else 0.0
            }
        }
        return 0.0
    }
    private fun calculateMedianGrade(dataList: List<Data_G>?): Double {
        val finalGrades = dataList
            ?.map { it.attributes.grade }
            ?.sorted()

        val count = finalGrades?.size
        if (count != null) {
            return if (count % 2 == 0) {
                val middleIndex = count / 2
                (finalGrades[middleIndex - 1] + finalGrades[middleIndex]) / 2.0
            } else {
                finalGrades[count / 2].toDouble()
            }
        }
        return 0.0
    }

    private fun calculatePassingRate(dataList: List<Data_G>?): Double {
        val finalGrades = dataList?.map { it.attributes.grade }

        val passingGrades = finalGrades?.filter { it >= 5 }
        val passingCount = passingGrades?.size
        val totalCount = finalGrades?.size

        if (passingCount != null) {
            if (totalCount != null) {
                return (passingCount.toDouble() / totalCount.toDouble()) * 100.0
            }
        }
        return 0.0
    }
    private fun calculateCourseRating(dataList: List<Data>?): Double {
        val courseRatingSum = dataList?.sumBy { it.attributes.final_course_rate }
        val count = dataList?.size
        Log.i("xxx-passRate", "$courseRatingSum + $count")
        return if (courseRatingSum != null && count != null)
            (courseRatingSum.toDouble() / count.toDouble())
        else
            0.0
    }
    private fun calculateCourseLevel(currentCourseRatings: List<Data>?): Double {
        val courseLevelSum = currentCourseRatings?.sumBy { it.attributes.course_level }
        val count = currentCourseRatings?.size
        return if (courseLevelSum != null && count != null)
            (courseLevelSum.toDouble() / count.toDouble())
        else
            0.0
    }

}