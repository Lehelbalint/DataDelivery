package com.example.datadelivery.Fragments

import android.content.Context
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
import com.example.datadelivery.BottomNavigationListener
import com.example.datadelivery.R
import com.example.datadelivery.ViewModel.FilterViewModel
import com.example.datadelivery.ViewModel.FilterViewModelFactory
import com.example.datadelivery.ViewModel.SharedChartsViewModel
import com.example.datadelivery.ViewModel.SharedUserViewModel
import com.example.datadelivery.databinding.FragmentFilterBinding


class Filter : Fragment() {
    private var bottomNavigationListener: BottomNavigationListener? = null
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private lateinit var filterViewModel: FilterViewModel
    val sharedChartsViewModel : SharedChartsViewModel  by activityViewModels()
    val sharedUserViewModel : SharedUserViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationListener) {
            bottomNavigationListener = context
        }
    }
    override fun onDetach() {
        super.onDetach()
        bottomNavigationListener = null
    }

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
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(sharedUserViewModel.loggedIn== 0)
        {
            hideBottomNavigation()
        }

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

        binding.send.setOnClickListener {
            sharedChartsViewModel.course = binding.spinnerCourse.selectedItem.toString()
            sharedChartsViewModel.department = binding.spinnerDepartment.selectedItem.toString()
            sharedChartsViewModel.type = binding.spinnerGradeType.selectedItem.toString()
            sharedChartsViewModel.year = binding.spinnerYear.selectedItem.toString()

            findNavController().navigate(R.id.action_filter_to_statistics2)

        }
        binding.button2.setOnClickListener {
            sharedChartsViewModel.course = binding.spinnerCourse.selectedItem.toString()
            sharedChartsViewModel.department = binding.spinnerDepartment.selectedItem.toString()
            sharedChartsViewModel.type = binding.spinnerGradeType.selectedItem.toString()
            sharedChartsViewModel.year = binding.spinnerYear.selectedItem.toString()
            findNavController().navigate(R.id.action_filter_to_secondFilter)
        }
    }
    private fun hideBottomNavigation() {
        bottomNavigationListener?.hideBottomNavigation()
    }

}