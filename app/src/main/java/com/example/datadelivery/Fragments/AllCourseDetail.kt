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
import com.example.datadelivery.Model.Data
import com.example.datadelivery.ViewModel.SharedUserViewModel
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
                if(currentCourseRatings != null)
                {
                    binding.courseRating.rating = calculateAverage(currentCourseRatings,"final_course_rate").toFloat()
                    binding.difficultyLevel.rating= calculateAverage(currentCourseRatings,"course_level").toFloat()
                    binding.ratingSystem.rating = calculateAverage(currentCourseRatings,"fair_pointing_rate").toFloat()
                    binding.teacherRating.rating = calculateAverage(currentCourseRatings,"teacher_rate").toFloat()
                    binding.friendlyTeacher.rating = calculateAverage(currentCourseRatings,"friendly_teacher_rate" ).toFloat()
                    binding.interestRate.rating =calculateAverage(currentCourseRatings,"interest_rate" ).toFloat()
                }
            }
        }


    }

    fun calculateAverage(dataList: List<Data>, field: String): Double {
        var sum = 0
        var count = 0

        dataList.forEach { data ->
            val fieldValue = when (field) {
                "interest_rate" -> data.attributes.interest_rate
                "relevance_rate" -> data.attributes.relevance_rate
                "friendly_teacher_rate" -> data.attributes.friendly_teacher_rate
                "fair_pointing_rate" -> data.attributes.fair_pointing_rate
                "material_availability_rate" -> data.attributes.material_availability_rate
                "timeschedule_rate" -> data.attributes.timeschedule_rate
                "flexibility_rate" -> data.attributes.flexibility_rate
                "course_level" -> data.attributes.course_level
                "teacher_rate" -> data.attributes.teacher_rate
                "final_course_rate" -> data.attributes.final_course_rate
                else -> throw IllegalArgumentException("Invalid field: $field")
            }

            sum += fieldValue
            count++
        }

        return if (count > 0) sum.toDouble() / count else 0.0
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


