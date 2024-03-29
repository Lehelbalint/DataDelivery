package com.example.datadelivery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datadelivery.Fragments.MyCourses


interface OnItemClickListener{
    fun onItemClick(position: Int)
}

class CourseAdapter(private val list: List<CourseItem>, private val allCourseList: List<CourseDataItem>,
                    private val listener: MyCourses
)  :  RecyclerView.Adapter<CourseAdapter.DataViewHolder>() {
    inner class DataViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var courseName: TextView = itemView.findViewById(R.id.FilteredData)
        var teacherName: TextView = itemView.findViewById(R.id.teacher_name)
        var detailsIc: ImageView= itemView.findViewById(R.id.details_ic)
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        ++bindCounter;
        //Log.i("XXX", "onBindViewHolder ${bindCounter}")
        val currentItem = list[position]
        holder.courseName.text = currentItem.attributes.name
        //Log.i("xxx-tch", getTeachersOfCourse(currentItem.attributes.name,allCourseList))

       holder.teacherName.text = getTeachersOfCourse(currentItem.attributes.name,allCourseList)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        var createCounter: Int = 0
        var bindCounter: Int = 0
    }
}
fun getTeachersOfCourse(courseName: String, courses: List<CourseDataItem>): String {
    val course = courses.find { it.attributes.name == courseName }
    return if( course !=null)
        course.attributes.teachers.data.joinToString(", ") { it.attributes.name }
    else "Jhon"
}