package ru.mrfix1033.roomnotes

import androidx.lifecycle.LiveData
import ru.mrfix1033.roomnotes.database.Contact
import ru.mrfix1033.roomnotes.database.ContactDao

class ContactRepository(private val contactDao: ContactDao) {
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
    
    fun getNotes(): LiveData<List<Contact>> {
        return contactDao.getAllContacts()
    }
}