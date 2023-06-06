package com.example.datadelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datadelivery.Fragments.AllCourses

interface OnItemClickListenerForAll{
    fun onItemClick(position: Int)
}

class AllCoursesAdapter( private val allCourseList: List<CourseDataItem>,
                    private val listener: AllCourses
)  :  RecyclerView.Adapter<AllCoursesAdapter.DataViewHolder>() {
    inner class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var courseName: TextView = itemView.findViewById(R.id.courseName)
        var teacherName: TextView = itemView.findViewById(R.id.teacher_name)
        var detailsIc: ImageView = itemView.findViewById(R.id.details_ic)
        init{
            //  detailsButton.setOnClickListener(this)
            detailsIc.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position : Int = adapterPosition
            if( position != RecyclerView.NO_POSITION) {
                when (p0!!.id) {
                    R.id.details_ic-> {
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
            LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        return DataViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter
        //Log.i("XXX", "onBindViewHolder ${bindCounter}")
        val currentItem = allCourseList[position]
        holder.courseName.text = currentItem.attributes.name
        //Log.i("xxx-tch", getTeachersOfCourse(currentItem.attributes.name,allCourseList))

        holder.teacherName.text = currentItem.attributes.teachers.data.joinToString(", ") { it.attributes.name }
    }

    override fun getItemCount(): Int {
        return allCourseList.size
    }

    companion object {
        var createCounter: Int = 0
        var bindCounter: Int = 0
    }
}
