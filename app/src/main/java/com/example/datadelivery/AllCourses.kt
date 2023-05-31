package com.example.datadelivery

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
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.databinding.FragmentAllCoursesBinding
import com.example.datadelivery.databinding.FragmentMyCoursesBinding

class AllCourses : Fragment() , OnItemClickListenerForAll{

    val sharedViewModel: SharedUserViewModel by activityViewModels()
    private var _binding: FragmentAllCoursesBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: AllCoursesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view_all_courses)
            adapter = AllCoursesAdapter(
                sharedViewModel.courseList.data,
                this)
            recycler_view.adapter = adapter
            binding.recyclerViewAllCourses.layoutManager = LinearLayoutManager(this.context)
            binding.recyclerViewAllCourses.setHasFixedSize(true)
        }

    override fun onItemClick(position: Int) {
        sharedViewModel.allPosition = position
        findNavController().navigate(R.id.action_myCourses_to_courseDetail)
    }
}

