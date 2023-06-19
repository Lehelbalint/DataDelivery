package com.example.datadelivery.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.datadelivery.*
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.Models.Data
import com.example.datadelivery.ViewModels.SharedChartsViewModel
import com.example.datadelivery.ViewModels.SharedUserViewModel
import com.example.datadelivery.databinding.FragmentAllCourseDetailBinding


class AllCourseDetail : Fragment() {

    val sharedViewModel: SharedUserViewModel by activityViewModels()
    private lateinit var notificationViewModel: NotificationViewModel
    private var _binding: FragmentAllCourseDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = NotificationViewModelFactory(DateDeliveryRepository())
        notificationViewModel = ViewModelProvider(this, factory).get(NotificationViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllCourseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentCourse = sharedViewModel.courseList.data[sharedViewModel.position]
        notificationViewModel.getGrades()
        notificationViewModel.gradeList.observe(viewLifecycleOwner) {
            val currentCourseGrades =
                notificationViewModel.gradeList.value?.data?.filter { it -> it.attributes.course?.data?.attributes?.name == currentCourse.attributes.name }
            binding.courseName.text = currentCourse.attributes.name
            if (currentCourseGrades != null) {
                val finalGrades = currentCourseGrades.filter { it.attributes.final}
                    if (finalGrades.isNotEmpty()) {
                    binding.avg.text = calculateFinalGradeAverage(currentCourseGrades).toString()
                    binding.median.text = calculateMedianGrade(currentCourseGrades).toString()
                    binding.passRate.text = calculatePassingRate(currentCourseGrades).toString()
                }
                else {
                        binding.avg.text = ""
                        binding.median.text = ""
                        binding.passRate.text = ""
                    }
            }
            notificationViewModel.getRatings()
            notificationViewModel.ratings.observe(viewLifecycleOwner)
            {
                val currentCourseRatings =
                    notificationViewModel.ratings.value?.data?.filter { it -> it.attributes.course.data.attributes.name == currentCourse.attributes.name}
                    binding.courseRating.rating = calculateCourseRating(currentCourseRatings).toFloat()
                    binding.difficultyLevel.rating = calculateCourseLevel(currentCourseRatings).toFloat()
            }
        }


    }

    private fun calculateCourseLevel(currentCourseRatings: List<Data>?): Double {
        val courseLevelSum = currentCourseRatings?.sumBy { it.attributes.course_level }
        val count = currentCourseRatings?.size
        return if (courseLevelSum != null && count != null)
            (courseLevelSum.toDouble() / count.toDouble())
        else
            0.0
    }

    private fun calculateFinalGradeAverage(grades: List<Data_G>?): Double {
            val finalGrades = grades
                ?.filter { it.attributes.final }
                ?.map { it.attributes.grade }

            val sum = finalGrades?.sum()
            val count = finalGrades?.size
        if (sum != null) {
            if (count != null) {
                return if (count > 0) sum.toDouble() / count else 0.0
            }
        }
        return 0.0
        }
    private fun calculateMedianGrade(dataList: List<Data_G>?): Double {
        val finalGrades = dataList
            ?.filter { it.attributes.final }
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
        val finalGrades = dataList
            ?.filter { it.attributes.final }
            ?.map { it.attributes.grade }

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

}


