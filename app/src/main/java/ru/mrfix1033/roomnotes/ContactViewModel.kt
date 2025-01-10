package ru.mrfix1033.roomnotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mrfix1033.roomnotes.database.Contact
import ru.mrfix1033.roomnotes.database.MyDatabase

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ContactRepository(MyDatabase.getInstance(application).getContactDao())
    val contacts = repository.getNotes()

    fun insertContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(contact)
    }
}
