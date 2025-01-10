package ru.mrfix1033.roomnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.mrfix1033.roomnotes.database.Contact
import java.text.DateFormat

class RecyclerViewAdapter(val dateFormatter: DateFormat, val functionToDelete: (Contact) -> Any) :
    RecyclerView.Adapter<RecyclerViewAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(itemView: View) : ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewSurname: TextView = itemView.findViewById(R.id.textViewSurname)
        private val textViewPhoneNumber: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
        private val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        private val imageViewTrash: ImageView = itemView.findViewById(R.id.imageViewTrash)

        fun bind(contact: Contact) {
            contact.run {
                textViewName.text = name
                textViewSurname.text = surname
                textViewPhoneNumber.text = phoneNumber
                textViewTime.text = dateFormatter.format(timeStamp)
                imageViewTrash.setOnClickListener {
                    functionToDelete(contact)
                }
            }
        }
    }

    private val contacts = ArrayList<Contact>()

    fun updateList(newList: List<Contact>) {
        contacts.clear()
        contacts.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)!!
        return PersonViewHolder(view)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
}
