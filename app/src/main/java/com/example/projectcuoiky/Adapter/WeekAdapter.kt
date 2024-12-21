package com.example.projectcuoiky.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcuoiky.R
import java.time.LocalDate

class WeekAdapter(val context: Context, private val weekDays: ArrayList<LocalDate>, val onDateSelected: (LocalDate) -> Unit) : RecyclerView.Adapter<WeekAdapter.WeekViewHolder>() {

    private var selectedPosition = -1

    class WeekViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.findViewById(R.id.dayTextView)
        val dayOfWeek: TextView = view.findViewById(R.id.day)
        val ItemDayOfWeek: CardView = view.findViewById(R.id.item_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_week_day, parent, false)
        return WeekViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val day = weekDays[position]
        val today = LocalDate.now()

        holder.dayTextView.text = day.dayOfWeek.name.substring(0, 3)
        holder.dayOfWeek.text = day.dayOfMonth.toString()

        setItem(holder, day, position, today)

        holder.itemView.setOnClickListener {
            onDateSelected(day)
            updateSelection(position)
        }
    }

     fun updateSelection(position: Int) {
        val oldPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(oldPosition)
        notifyItemChanged(selectedPosition)
    }


    fun setBackGroundAndCheck(holder: WeekViewHolder, colorResId: Int, colorText: Int) {
        val color = ContextCompat.getColor(context, colorResId)
        holder.ItemDayOfWeek.backgroundTintList = ColorStateList.valueOf(color)
        holder.dayOfWeek.setTextColor(colorText)
        holder.dayTextView.setTextColor(colorText)
    }



    fun setItem(holder: WeekViewHolder, day: LocalDate, position: Int, today: LocalDate) {
        if (position == selectedPosition) {
            setBackGroundAndCheck(holder, R.color.blueBocchi, Color.WHITE)
        } else if (day.isEqual(today)) {
            setBackGroundAndCheck(holder, R.color.YellowBocchi, Color.BLACK)
        } else {
            setBackGroundAndCheck(holder, R.color.white, Color.BLACK)
        }
    }

    override fun getItemCount(): Int {
        return weekDays.size
    }
}
