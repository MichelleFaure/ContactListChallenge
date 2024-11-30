package com.example.contactlistexample

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.adapter.ContactAdapter
import com.example.contactlistexample.data.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var checkBoxIsAvailable: CheckBox
    private lateinit var buttonAddContact: Button
    private lateinit var buttonFilterContacts: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind form elements
        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        checkBoxIsAvailable = findViewById(R.id.checkBoxIsAvailable)
        buttonAddContact = findViewById(R.id.buttonAddContact)
        buttonFilterContacts = findViewById(R.id.buttonFilterContacts)

        // Set up RecyclerView
        setRecyclerViewAdapter(contactList)

        // Set click listener for the add contact button
        buttonAddContact.setOnClickListener {
            addContact()
        }

        // Set click listener for filter
        buttonFilterContacts.setOnClickListener {
            val filteredList = contactList.filter { it.isAvailable }
            setRecyclerViewAdapter(filteredList)
        }
    }

    private fun addContact() {
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val isAvailable = checkBoxIsAvailable.isChecked

        // Validate fields
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create Contact object and add to list
        val contact = Contact(name, phone, isAvailable)
        contactList.add(contact)

        // Update RecyclerView
        adapter.notifyItemInserted(contactList.size - 1)

        setRecyclerViewAdapter(contactList)

        // Clear form fields
        etName.text.clear()
        etPhone.text.clear()
        checkBoxIsAvailable.isChecked = false
    }

    private fun setRecyclerViewAdapter(contactList: List<Contact>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}