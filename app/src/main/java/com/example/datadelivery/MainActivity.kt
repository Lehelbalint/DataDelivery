package com.example.datadelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = StudentViewModelFactory(DateDeliveryRepository())
        val studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)


        val navView: BottomNavigationView = binding.navView

       val controller = findNavController(R.id.nav_host_fragment_activity_main)
//        controller.addOnDestinationChangedListener { _, destination, _ ->
//            if (destination.id == R.id.login_fragment || destination.id==R.id.welcome_fragment || destination.id==R.id.register_Fragment) {
//
//                navView.visibility = View.GONE
//            } else {
//                navView.visibility = View.VISIBLE
//            }
//        }
        controller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.logIn ) {

                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.notifications -> {
                    controller.navigate(R.id.notifications2)
                    true
                }
                R.id.myCourses -> {
                    controller.navigate(R.id.myCourses)
                    true
                }
                R.id.statistics -> {
                    controller.navigate(R.id.charts)
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }

    }}