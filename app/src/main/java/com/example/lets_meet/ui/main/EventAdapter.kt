package com.example.lets_meet.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lets_meet.databinding.ItemEventBinding
import com.example.lets_meet.model.Event

class EventAdapter(private val events: List<Event>, private val selectedDate: String) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEventBinding.inflate(inflater, parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event, selectedDate)
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event, selectedDate: String) {
            if(event.date == selectedDate) {
                binding.textViewTitle.text = event.title
                binding.textViewTime.text = "${event.starttime} ~ ${event.endtime}"
                binding.root.visibility = View.VISIBLE
            } else {
                binding.root.visibility = View.GONE
            }
        }
    }
}
