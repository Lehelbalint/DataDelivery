package com.example.datadelivery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.datadelivery.ViewModel.SharedGradeViewModel
import com.example.datadelivery.databinding.FragmentNotificationDetailBinding

class NotificationDetail : Fragment() {

    val sharedGradeViewModel: SharedGradeViewModel by activityViewModels()
    private var _binding: FragmentNotificationDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentGrade = sharedGradeViewModel.myGrades[sharedGradeViewModel.position]
        binding.courseName.text= currentGrade.attributes.course.data.attributes.name
        binding.grade.text=currentGrade.attributes.grade.toString()
        binding.date.text = currentGrade.attributes.date
        binding.teacherName.text = currentGrade.attributes.teacher.data.attributes.name
        if (currentGrade.attributes.final)
        {
            binding.gradeType.text="Final Grade"
        }
        else
        binding.gradeType.text = "${currentGrade.attributes.percantage}"+"% of final grade"
    }


    }
