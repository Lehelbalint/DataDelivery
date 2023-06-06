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
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.NotificationViewModel
import com.example.datadelivery.NotificationViewModelFactory
import com.example.datadelivery.ViewModels.SharedUserViewModel
import com.example.datadelivery.databinding.FragmentCourseDetailBinding


class CourseDetail : Fragment() {

    val sharedViewModel: SharedUserViewModel by activityViewModels()
    private lateinit var notificationViewModel: NotificationViewModel
    private var _binding: FragmentCourseDetailBinding? = null
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
        _binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val currentCourse = sharedViewModel.currentUser.attributes.courses.data[sharedViewModel.position]
            Log.i("xxx-try", sharedViewModel.currentUser.attributes.courses.data[sharedViewModel.position].toString())
        notificationViewModel.getGrades()
        notificationViewModel.gradeList.observe(viewLifecycleOwner) {
            val currentCourseGrades =
                notificationViewModel.gradeList.value?.data?.filter { it -> it.attributes.course?.data?.attributes?.name == currentCourse.attributes.name }
            val myGrades =
                currentCourseGrades?.filter { it -> it.attributes.student.data.attributes.name == sharedViewModel.currentUser.attributes.name }

            val string = myGrades?.filter { it -> !it.attributes.final }?.joinToString(", ") { "${it.attributes.grade}-${it.attributes.percantage}%" }
            binding.grades.text=string
            val final = myGrades?.filter { it.attributes.final }
            if (final != null) {
                if(final.isNotEmpty()) {
                    Log.i("xxx-try2", final.toString())
                    binding.finalGrade.text=final[0].attributes.grade.toString()
                } else {
                    binding.finalGrade.text = "-"
                }
            }
            binding.showChartButton.setOnClickListener {
                // Diagram ablak megjelenítése
                showChartDialog()
            }

        }


    }
    private fun showChartDialog() {
        // Diagram ablakot létrehozása és megjelenítése
        val chartDialog = Charts()
        chartDialog.show(parentFragmentManager, "chart_dialog")
    }
}


