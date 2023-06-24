package com.example.datadelivery.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.R
import com.example.datadelivery.ViewModel.FilterViewModel
import com.example.datadelivery.ViewModel.FilterViewModelFactory
import com.example.datadelivery.ViewModel.SharedChartsViewModel
import com.example.datadelivery.databinding.FragmentSecondFilterBinding



class SecondFilter : Fragment() {

    private var _binding: FragmentSecondFilterBinding? = null
    private val binding get() = _binding!!
    private lateinit var filterViewModel: FilterViewModel
    val sharedChartsViewModel : SharedChartsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = FilterViewModelFactory(DateDeliveryRepository())
        filterViewModel = ViewModelProvider(this, factory).get(FilterViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSecondFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val yearOptions = listOf("All","2021", "2022", "2023")
        val gradeTypeOptions = listOf("All","Final", "Partial")

        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, yearOptions)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerYear.adapter = yearAdapter

        val gradeTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gradeTypeOptions)
        gradeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGradeType.adapter = gradeTypeAdapter

        filterViewModel.getDepartments()
        filterViewModel.departmentList.observe(viewLifecycleOwner)
        {

            val departmentNames = mutableListOf<String>()
            departmentNames.add("All")
            filterViewModel.departmentList.value?.data?.let { it1 -> departmentNames.addAll( it1.map { it.attributes.department_name }) }
            val departmentAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, departmentNames)
            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDepartment.adapter = departmentAdapter
        }
        filterViewModel.getCourses()
        filterViewModel.courseList.observe(viewLifecycleOwner)
        {
            val courseNames = mutableListOf<String>()
            courseNames.add("All")
            filterViewModel.courseList.value?.data?.let { it1 -> courseNames.addAll(it1.map { it.attributes.name }) }
            val courseAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, courseNames)
            courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCourse.adapter = courseAdapter

        }

        binding.button2.setOnClickListener {
            sharedChartsViewModel.course2 = binding.spinnerCourse.selectedItem.toString()
            sharedChartsViewModel.department2 = binding.spinnerDepartment.selectedItem.toString()
            sharedChartsViewModel.type2 = binding.spinnerGradeType.selectedItem.toString()
            sharedChartsViewModel.year2 = binding.spinnerYear.selectedItem.toString()

            findNavController().navigate(R.id.action_secondFilter_to_compare)
        }
    }

}