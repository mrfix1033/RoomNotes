package ru.mrfix1033.roomnotes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mrfix1033.roomnotes.database.Contact
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(findViewById(R.id.toolbar))

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextSurname: EditText = findViewById(R.id.editTextSurname)
        val editTextPhoneNumber: EditText = findViewById(R.id.editTextPhoneNumber)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        contactViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ContactViewModel::class.java]
        contactViewModel.contacts.observe(this) {list ->
            recyclerViewAdapter.updateList(list)
        }

        val dateFormatter = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ROOT)
        dateFormatter.timeZone = TimeZone.getTimeZone("GMT+3")

        recyclerViewAdapter = RecyclerViewAdapter(dateFormatter) { contact ->
            contactViewModel.deleteContact(contact)
        }
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val name = editTextName.text.toString().trim()
            val surname = editTextSurname.text.toString().trim()
            val phoneNumber = editTextPhoneNumber.text.toString().trim()
            if (name.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.all_fields_must_be_filled), Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val person = Contact(name, surname, phoneNumber, Date().time)
            contactViewModel.insertContact(person)

            editTextName.text.clear()
            editTextSurname.text.clear()
            editTextPhoneNumber.text.clear()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemExit) finishAffinity()
        return super.onOptionsItemSelected(item)
    }
}