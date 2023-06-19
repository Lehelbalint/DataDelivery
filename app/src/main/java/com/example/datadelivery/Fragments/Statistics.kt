package com.example.datadelivery.Fragments

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
import com.example.datadelivery.NotificationViewModel
import com.example.datadelivery.NotificationViewModelFactory
import com.example.datadelivery.ViewModels.SharedChartsViewModel
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

            }
        }
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

}