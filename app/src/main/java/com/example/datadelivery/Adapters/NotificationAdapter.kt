package com.example.datadelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datadelivery.Fragments.Notifications

interface OnNotificationItemClickListener{
    fun onItemClick(position: Int)
}

class NotificationAdapter(private val list: List<Data_G>,  private val listener: Notifications) :  RecyclerView.Adapter<NotificationAdapter.DataViewHolder>()  {


    inner class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var courseName: TextView = itemView.findViewById(R.id.subject)
        var date: TextView = itemView.findViewById(R.id.dateTime)
        var myView: View = itemView.findViewById(R.id.notification_item)
        init {
            myView.setOnClickListener (this)
        }
        override fun onClick(p0: View?) {
                val position : Int = adapterPosition
                if( position != RecyclerView.NO_POSITION) {
                    when (p0!!.id) {
                        R.id.notification_item-> {
                            listener.onItemClick(position)
                        }
                    }
                }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        // ++createCounter
        //Log.i("XXX", "onCreateViewHolder ${createCounter}")
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return DataViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter;
        //Log.i("XXX", "onBindViewHolder ${bindCounter}")
        val currentItem = list[position]
        holder.courseName.text = currentItem.attributes.course?.data?.attributes?.name.toString()
        //Log.i("xxx-tch", getTeachersOfCourse(currentItem.attributes.name,allCourseList))
        holder.date.text = currentItem.attributes.date.toString()

    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        var createCounter: Int = 0
        var bindCounter: Int = 0
    }
}

