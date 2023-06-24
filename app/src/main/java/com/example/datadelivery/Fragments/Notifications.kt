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
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.Model.Rating
import com.example.datadelivery.NotificationAdapter
import com.example.datadelivery.NotificationViewModel
import com.example.datadelivery.NotificationViewModelFactory
import com.example.datadelivery.R
import com.example.datadelivery.ViewModel.SharedGradeViewModel
import com.example.datadelivery.ViewModel.SharedUserViewModel
import com.example.datadelivery.databinding.FragmentNotificationsBinding

class Notifications : Fragment() {

    val sharedViewModel: SharedUserViewModel by activityViewModels()
    val sharedGradeViewModel: SharedGradeViewModel by activityViewModels()
    private lateinit var notificationViewModel: NotificationViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = NotificationViewModelFactory(DateDeliveryRepository())
        notificationViewModel = ViewModelProvider(this, factory).get(NotificationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationViewModel.getGrades()
        notificationViewModel.gradeList.observe(viewLifecycleOwner) {
//            Log.i("xxx-not", notificationViewModel.gradeList.value.toString())
//            Log.i("xxx-cur", sharedViewModel.currentUser.toString())
            val grades = notificationViewModel.gradeList.value
            val myGrades = grades?.data?.filter { it ->
                it.attributes.student.data.id == sharedViewModel.currentUser.id
            }?.sortedByDescending { grade ->
                grade.attributes.createdAt
            }
            Log.i("xxx-not",myGrades.toString())
            if (myGrades != null) {
                sharedGradeViewModel.myGrades=myGrades
            }
            val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view_notifications)
            adapter = NotificationAdapter(myGrades!!,this)
            recycler_view.adapter = adapter
            binding.recyclerViewNotifications.layoutManager = LinearLayoutManager(this.context)
            binding.recyclerViewNotifications.setHasFixedSize(true)
        }

    }

    fun onItemClick(position: Int) {
        sharedGradeViewModel.position = position
        if (sharedGradeViewModel.myGrades[position].attributes.final) {
            notificationViewModel.getRatings()
            notificationViewModel.ratings.observe(viewLifecycleOwner)
            {
                Log.i("xxx-not", notificationViewModel.ratings.value.toString())
                val ratings = notificationViewModel.ratings.value
                val neptunId = sharedViewModel.currentUser.attributes.neptun_id
                val courseName =
                    sharedGradeViewModel.myGrades[position].attributes.course?.data?.attributes?.name
                if (courseName != null) {
                    val containsElement = ratings?.let { it1 ->
                        containsElementWithNeptunIdAndCourseName(
                            it1,
                            neptunId,
                            courseName
                        )
                    }
                    if (containsElement == true) {
                        findNavController().navigate(R.id.action_notifications2_to_notificationDetail)
                    } else {
                        findNavController().navigate(R.id.action_notifications2_to_questionnaire)
                    }
                }
            }
        }
        else
        {
            findNavController().navigate(R.id.action_notifications2_to_notificationDetail)
        }

        }
    fun containsElementWithNeptunIdAndCourseName(myClass: Rating, neptunId: String, courseName: String): Boolean {
        return myClass.data.any { data ->
            data.attributes.student.data.attributes.neptun_id == neptunId &&
                    data.attributes.course.data.attributes.name == courseName
        }
    }


}