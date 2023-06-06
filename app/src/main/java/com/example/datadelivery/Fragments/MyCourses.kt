package com.example.datadelivery.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datadelivery.*
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.ViewModels.SharedUserViewModel
import com.example.datadelivery.databinding.FragmentMyCoursesBinding


class MyCourses : Fragment() , OnItemClickListener {

    val sharedViewModel: SharedUserViewModel by activityViewModels()
    private lateinit var myCoursesViewModel: MyCoursesViewModel
    private var _binding: FragmentMyCoursesBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: CourseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MyCoursesViewModelFactory(DateDeliveryRepository())
       myCoursesViewModel = ViewModelProvider(this, factory).get(MyCoursesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myCoursesViewModel.getCourses()
        myCoursesViewModel.courseList.observe(viewLifecycleOwner) {
            sharedViewModel.courseList = myCoursesViewModel.courseList.value!!
            Log.i("xxx-pfmc", sharedViewModel.courseList.toString())
                val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view_courses)
                    adapter = CourseAdapter(
                        sharedViewModel.currentUser.attributes.courses.data,
                        sharedViewModel.courseList.data,
                        this)
                recycler_view.adapter = adapter
                binding.recyclerViewCourses.layoutManager = LinearLayoutManager(this.context)
                binding.recyclerViewCourses.setHasFixedSize(true)
            }
        binding.allCoursesListener.setOnClickListener {
            findNavController().navigate(R.id.allCourses)
        }

        }


    override fun onItemClick(position: Int) {
        sharedViewModel.position = position
        findNavController().navigate(R.id.action_myCourses_to_courseDetail)

    }


}

