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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mrfix1033.roomnotes.database.MyDatabase
import ru.mrfix1033.roomnotes.database.Person

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private val persons = mutableListOf<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val database = MyDatabase.getInstance(this)
        readDatabase(database)

        setSupportActionBar(findViewById(R.id.toolbar))

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextSurname: EditText = findViewById(R.id.editTextSurname)
        val editTextPhoneNumber: EditText = findViewById(R.id.editTextPhoneNumber)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerViewAdapter = RecyclerViewAdapter(persons)
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

            val person = Person(name, surname, phoneNumber)
            addPerson(database, person)
            persons.add(person)
            recyclerViewAdapter.notifyItemInserted(persons.size - 1)

            editTextName.text.clear()
            editTextSurname.text.clear()
            editTextPhoneNumber.text.clear()
        }
    }

    private fun addPerson(db: MyDatabase, person: Person) = CoroutineScope(Dispatchers.IO).launch {
        db.getPersonDao().insert(person)
    }

    private fun readDatabase(db: MyDatabase) = CoroutineScope(Dispatchers.IO).launch {
        persons.clear()
        persons.addAll(db.getPersonDao().getAllPersons())
        recyclerViewAdapter.notifyDataSetChanged()
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