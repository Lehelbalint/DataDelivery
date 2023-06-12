package com.example.datadelivery

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.Models.AddRatingRequest
import com.example.datadelivery.ViewModels.QuestionnaireViewModel
import com.example.datadelivery.ViewModels.QuestionnaireViewModelFactory
import com.example.datadelivery.ViewModels.SharedGradeViewModel
import com.example.datadelivery.ViewModels.SharedUserViewModel
import com.example.datadelivery.databinding.FragmentQuestionnaireBinding

class Questionnaire : Fragment() {
    val sharedViewModel: SharedUserViewModel by activityViewModels()
    val sharedGradeViewModel: SharedGradeViewModel by activityViewModels()
    private lateinit var questionnaireViewModel: QuestionnaireViewModel
    private var _binding: FragmentQuestionnaireBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = QuestionnaireViewModelFactory(DateDeliveryRepository())
        questionnaireViewModel = ViewModelProvider(this, factory).get(QuestionnaireViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPopupMessage()
        binding.submit.setOnClickListener {
            val rateData = AddRatingRequest(
                data = AddRatingRequest.Data(
                interest_rate = binding.ratingBar1.rating.toInt(),
                relevance_rate = binding.ratingBar2.rating.toInt(),
                friendly_teacher_rate = binding.ratingBar3.rating.toInt(),
                fair_pointing_rate = binding.ratingBar4.rating.toInt(),
                material_availability_rate = binding.ratingBar5.rating.toInt(),
                timeschedule_rate = binding.ratingBar6.rating.toInt(),
                flexibility_rate = binding.ratingBar7.rating.toInt(),
                course_level = binding.ratingBar8.rating.toInt(),
                teacher_rate = binding.ratingBar9.rating.toInt(),
                final_course_rate = binding.ratingBar10.rating.toInt(),
                    course = AddRatingRequest.Data.Course(
                        connect = listOf(sharedGradeViewModel.myGrades[sharedGradeViewModel.position].attributes.course?.data?.id) as List<Int>
                    ),
                    student = AddRatingRequest.Data.Student(
                        connect = listOf(sharedViewModel.currentUser.id)
                    ),
                )
                )

            questionnaireViewModel.addRating(rateData)
            questionnaireViewModel.message.observe(viewLifecycleOwner)
            {
                Log.i("xxx-ins","sikerult")

            }
            findNavController().navigate(R.id.notificationDetail)

        }

    }

    private fun showPopupMessage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New Final Grade")
        builder.setMessage("You must fill it out to see your final grade")

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

}