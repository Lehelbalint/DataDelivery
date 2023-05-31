package com.example.datadelivery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.databinding.FragmentNotificationsBinding

class Notifications : Fragment() {

    val sharedViewModel: SharedUserViewModel by activityViewModels()
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
            Log.i("xxx-not", notificationViewModel.gradeList.value.toString())
            Log.i("xxx-cur", sharedViewModel.currentUser.toString())
            val grades = notificationViewModel.gradeList.value
            val myGrades = grades?.data?.filter { it ->
                it.attributes.student.data.id == sharedViewModel.currentUser.id
            }?.sortedByDescending { grade ->
                grade.attributes.createdAt
            }
            Log.i("xxx-not",myGrades.toString())
            val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view_notifications)
            adapter = NotificationAdapter(myGrades!!)
            recycler_view.adapter = adapter
            binding.recyclerViewNotifications.layoutManager = LinearLayoutManager(this.context)
            binding.recyclerViewNotifications.setHasFixedSize(true)
        }

    }


}