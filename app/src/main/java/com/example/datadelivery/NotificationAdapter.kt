package com.example.datadelivery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(private val list: List<Data_G>):  RecyclerView.Adapter<NotificationAdapter.DataViewHolder>()  {


    inner class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var courseName: TextView = itemView.findViewById(R.id.course_name)
        var teacherName: TextView = itemView.findViewById(R.id.teacher_name)
        var grade: TextView = itemView.findViewById(R.id.grade)
        var gradeType: TextView = itemView.findViewById(R.id.grade_type)
        var date: TextView = itemView.findViewById(R.id.date)

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
        holder.teacherName.text = currentItem.attributes.teacher.data.attributes.name
        holder.grade.text = currentItem.attributes.grade.toString()
        if (currentItem.attributes.final)
        holder.gradeType.text = "Final"
        else
            holder.gradeType.text = currentItem.attributes.percantage.toString()
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

