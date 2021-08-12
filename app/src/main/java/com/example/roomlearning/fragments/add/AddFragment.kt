package com.example.roomlearning.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomlearning.R
import com.example.roomlearning.model.User
import com.example.roomlearning.data.UserViewModel
import com.example.roomlearning.model.Address
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.lang.StringBuilder

class AddFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.fragment_add_add_button.setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val firstName = fragment_add_first_name_tv.text.toString()
        val lastName = fragment_add_last_name_tv.text.toString()
        val age = fragment_add_age_tv.text.toString()
        val streetName = fragment_add_street_name_tv.text.toString()
        val streetNumber = fragment_add_street_number_tv.text.toString()

        if (checkInput(firstName, lastName, age, streetName, streetNumber)) {
            // Create user object
            val user = User(
                0,
                firstName,
                lastName,
                Integer.parseInt(age),
                Address(streetName, Integer.parseInt(streetNumber)),
                userViewModel.bitmap.value!!
            )

            // Add data to database
            userViewModel.addUser(user)
            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show()


            // Navigate back
            findNavController().navigateUp()
        } else {
            Toast.makeText(context, "Bad input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInput(
        firstName: String,
        lastName: String,
        age: String,
        streetName: String,
        streetNumber: String
    ): Boolean {
        return !(TextUtils.isEmpty(firstName)
                || TextUtils.isEmpty(lastName)
                || TextUtils.isEmpty(age)
                || !TextUtils.isDigitsOnly(age)
                || TextUtils.isEmpty(streetName)
                || TextUtils.isEmpty(streetNumber)
                || !TextUtils.isDigitsOnly(streetNumber)
                )
    }
}