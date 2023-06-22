package com.example.datadelivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.ViewModels.SharedUserViewModel
import com.example.datadelivery.databinding.FragmentLogInBinding

class LogIn : Fragment() {
    val sharedViewModel: SharedUserViewModel by activityViewModels()
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(DateDeliveryRepository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.getStudents()
        loginViewModel.userList.observe(viewLifecycleOwner) {
            val Users = loginViewModel.userList.value
            if (Users != null) {
                //Log.i("xxx-pfm", Users.data.toString())
            }
            binding.guest.setOnClickListener {
                findNavController().navigate(R.id.action_logIn_to_filter)
            }
            binding.button.setOnClickListener {
                val studentCode = binding.studentCodeEdittext.text.toString().trim()
                val password = binding.passwordEdittext.text.toString().trim()
                if (studentCode.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this.requireContext(),
                        "Please, enter your student code and password",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else if (Users != null) {
                    if (findStudent(Users.data,studentCode,password)) {
                        sharedViewModel.currentUser =  findStudentById(studentCode,Users.data)
                        // Log.i("xxx-pfm",sharedViewModel.currentUser.attributes.courses.data[0].attributes.name)
                        findNavController().navigate(R.id.notifications2)
                    }
                    else
                    {
                        Toast.makeText(
                            this.requireContext(),
                            "INVALID CODE OR PASSWORD",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}

fun findStudent(studentData: List<Student>, student_code: String, password: String): Boolean {
    return studentData.any { student ->
        student.attributes.neptun_id == student_code &&
                student.attributes.password == password
    }
}
fun findStudentById(neptun_id: String ,students: List<Student>): Student {
    return students.find { it.attributes.neptun_id == neptun_id }!!
}
