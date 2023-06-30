package com.example.datadelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.datadelivery.API.DateDeliveryRepository
import com.example.datadelivery.ViewModel.SharedUserViewModel
import com.example.datadelivery.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationListener {
    private lateinit var binding: ActivityMainBinding
    val sharedUserViewModel : SharedUserViewModel by viewModels()
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
            if (destination.id == R.id.logIn || destination.id== R.id.questionnaire ) {

                navView.visibility = View.GONE
            }
//            if (destination.id == R.id.filter && sharedUserViewModel.currentUser==null)
//            {
//                navView.visibility= View.GONE
//            }
            else {
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
                    controller.navigate(R.id.filter)
                    true
        }
        R.id.Logout ->
        {
            showLogoutConfirmationDialog(controller)
            false
        }
        else -> super.onOptionsItemSelected(it)
    }
}

}

override fun hideBottomNavigation() {
    val navView: BottomNavigationView = binding.navView
    navView.visibility=View.GONE
}
private fun showLogoutConfirmationDialog(controller: NavController) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Loging out")
    builder.setMessage("Are you sure you want to log out?")
    builder.setPositiveButton("Yes") { _, _ ->
        sharedUserViewModel.loggedIn=0
        controller.navigate(R.id.logIn)
        }
        builder.setNegativeButton("No", null)
        val dialog = builder.create()
        dialog.show()
    }

}

interface BottomNavigationListener {
    fun hideBottomNavigation()
}

