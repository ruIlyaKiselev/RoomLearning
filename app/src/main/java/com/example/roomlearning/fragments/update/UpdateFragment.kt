package com.example.roomlearning.fragments.update

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomlearning.R
import com.example.roomlearning.data.UserViewModel
import com.example.roomlearning.model.Address
import com.example.roomlearning.model.User
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.fragment_update_first_name_tv.setText(args.selectedUser.firstName)
        view.fragment_update_last_name_tv.setText(args.selectedUser.lastName)
        view.fragment_update_age_tv.setText(args.selectedUser.age.toString())
        view.fragment_update_street_name_tv.setText(args.selectedUser.address.streetName)
        view.fragment_update_street_number_tv.setText(args.selectedUser.address.streetNumber.toString())

        view.fragment_update_save_button.setOnClickListener {
            updateUser()
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateUser() {
        val firstName = fragment_update_first_name_tv.text.toString()
        val lastName = fragment_update_last_name_tv.text.toString()
        val age = fragment_update_age_tv.text.toString()
        val streetName = fragment_update_street_name_tv.text.toString()
        val streetNumber = fragment_update_street_number_tv.text.toString()

        if (checkInput(firstName, lastName, age, streetName, streetNumber)) {
            // Create user object
            val user = User(
                args.selectedUser.id,
                firstName,
                lastName,
                Integer.parseInt(age),
                Address(streetName, Integer.parseInt(streetNumber)),
                args.selectedUser.profilePhoto
            )

            // Add data to database
            userViewModel.updateUser(user)
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show()

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            userViewModel.deleteUser(args.selectedUser)
            Toast.makeText(requireContext(), "Successfully removed " +
                    "${args.selectedUser.firstName} ${args.selectedUser.lastName}",
                Toast.LENGTH_SHORT).show()

            // Navigate back
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->

        }
        builder.setTitle("Delete ${args.selectedUser.firstName} ${args.selectedUser.lastName}?")
        builder.setMessage("Are you sure you want to delete ${args.selectedUser.firstName} " +
                "${args.selectedUser.lastName}?")
        builder.create().show()
    }
}