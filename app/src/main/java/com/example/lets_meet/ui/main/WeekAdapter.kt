import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lets_meet.R
import java.text.SimpleDateFormat
import java.util.*
class WeekAdapter(
    private val dates: List<Date>,
    private val onDateClick: (Date) -> Unit
) : RecyclerView.Adapter<WeekAdapter.DayViewHolder>() {
    var selectedDate: Date = Date()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view, onDateClick)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val date = dates[position]
        holder.bind(date, date == selectedDate)
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    class DayViewHolder(
        itemView: View,
        private val onDateClick: (Date) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvDay: TextView = itemView.findViewById(R.id.tv_day)
        private val tvDayOfWeek: TextView = itemView.findViewById(R.id.tv_weekday)
        private val dayFormat = SimpleDateFormat("EE", Locale.getDefault())
        fun bind(date: Date, isSelected: Boolean) {
            val sdf = SimpleDateFormat("dd", Locale.getDefault())
            tvDay.text = sdf.format(date)
            tvDayOfWeek.text = dayFormat.format(date.time)
            itemView.setOnClickListener {
                onDateClick(date)
            }
            if (isSelected || date==onDateClick) {
                // 선택된 날짜 스타일링, 예를 들면 배경색 변경
                tvDay.setBackgroundResource(R.drawable.select)
            }
            else {
                tvDay.setBackgroundResource(0) // 기본 배경
            }
        }
    }
}
