namespace DataDeliveryAdmin.Models
{   
 
    public class StudentViewModel
    {
        public List<Student> data { get; set; }
        public int Courseid { get; set; }
    }
    public class Student
    {
        public int id { get; set; }
        public StudentAttributes attributes { get; set; }
    }
    public class StudentAttributes
    {
        public string neptun_id { get; set; }
        public string email { get; set; }
        public string name { get; set; }
        public DateTime createdAt { get; set; }
        public DateTime updatedAt { get; set; }
        public DateTime publishedAt { get; set; }
        public string password { get; set; }
        public DepartmentData department { get; set; }
        public GradeData grades { get; set; }
        public CourseData_S courses { get; set; }
    }

    public class GradeData
    {
        public List<Grade> data { get; set; }
    }
    public class CourseData_S
    {
        public List<Course> data { get; set; }
    }
    public class DepartmentData
    {
        public Department data { get; set; }
    }
    public class Department
    {
        public int id { get; set; }
        public DepartmentAttributes_S attributes { get; set; }
    }
    public class DepartmentAttributes_S
    {
        public string department_name { get; set; }
        public DateTime createdAt { get; set; }
        public DateTime updatedAt { get; set; }
        public DateTime publishedAt { get; set; }
        public int year { get; set; }
    }
    public class Grade
    {
        public int id { get; set; }
        public GradeAttributes attributes { get; set; }
    }
    public class GradeAttributes
    {
        public int grade { get; set; }
        public int percantage { get; set; }
        public bool final { get; set; }
        public DateTime createdAt { get; set; }
        public DateTime updatedAt { get; set; }
        public DateTime publishedAt { get; set; }
        public string date { get; set; }
    }
    public class Course
    {
        public int id { get; set; }
        public CourseAttributes attributes { get; set; }
    }
    public class CourseAttributes_S
    {
        public string name { get; set; }
        public DateTime createdAt { get; set; }
        public DateTime updatedAt { get; set; }
        public DateTime publishedAt { get; set; }
    }
}
