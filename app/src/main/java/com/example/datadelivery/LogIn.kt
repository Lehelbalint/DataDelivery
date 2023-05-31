package com.example.datadelivery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.databinding.FragmentLogInBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogIn.newInstance] factory method to
 * create an instance of this fragment.
 */
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


//        button.setOnClickListener {
//            val email = editText1.text.toString().trim()
//            val password = editText2.text.toString().trim()
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(
//                    this.requireContext(),
//                    "Please, enter your email and password",
//                    Toast.LENGTH_LONG
//                ).show()
//            } else {
//                loginViewModel.login(LoginRequest(email, password))
//            }
//        }
//
//        loginViewModel.loginResult.observe(viewLifecycleOwner) {
//            // Save data to preferences
//            if( it == LoginResult.INVALID_CREDENTIALS){
//                Toast.makeText(
//                    this.requireContext(),
//                    "Invalid credentials",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//            if ( it == LoginResult.SUCCESS ) {
//                val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
//                val edit = prefs.edit()
//                edit.putString("token", MyApplication.token)
//                edit.putLong("deadline", MyApplication.deadline)
//                edit.putString("email", editText1.text.toString())
//                edit.apply()
//                findNavController().navigate(R.id.activities)
//            }
//        }
//
//    }