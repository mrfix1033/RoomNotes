package ru.mrfix1033.roomnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.mrfix1033.roomnotes.database.Person

class RecyclerViewAdapter(private val list: List<Person>) :
    RecyclerView.Adapter<RecyclerViewAdapter.PersonViewHolder>() {

    class PersonViewHolder(itemView: View) : ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewSurname: TextView = itemView.findViewById(R.id.textViewSurname)
        val textViewPhoneNumber: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)!!
        return PersonViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.run {
            list[position].run {
                textViewName.text = name
                textViewSurname.text = surname
                textViewPhoneNumber.text = phoneNumber
            }
        }
    }
}
