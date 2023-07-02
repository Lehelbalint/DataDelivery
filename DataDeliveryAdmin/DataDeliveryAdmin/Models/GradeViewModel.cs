namespace DataDeliveryAdmin.Models
{
    public class GradeViewModel
    {
        public List<Data_G> Data { get; set; }

	}

    public class Data_G
    {
        public int Id { get; set; }
        public Attributes_G Attributes { get; set; }
    }

    public class Attributes_G
    {
        public int Grade { get; set; }
        public int Percantage { get; set; }
        public bool Final { get; set; }
        public string CreatedAt { get; set; }
        public string UpdatedAt { get; set; }
        public string PublishedAt { get; set; }
        public string Date { get; set; }

        public string Note { get; set; }   
        public Course_Grade Course { get; set; }
        public Student_G Student { get; set; }
        public Teacher_G Teacher { get; set; }
    }

    public class Course_Grade
    {
        public CourseData_G Data { get; set; }
    }

    public class CourseData_G
    {
        public int Id { get; set; }
        public CourseAttributes_G Attributes { get; set; }
    }

    public class CourseAttributes_G
    {
        public string Name { get; set; }
        public string CreatedAt { get; set; }
        public string UpdatedAt { get; set; }
        public string PublishedAt { get; set; }
    }

    public class Student_G
    {
        public StudentData_G Data { get; set; }
    }

    public class StudentData_G
    {
        public int Id { get; set; }
        public StudentAttributes_G Attributes { get; set; }
    }

    public class StudentAttributes_G
    {
        public string Neptun_Id { get; set; }
        public string Email { get; set; }
        public string Name { get; set; }
        public string CreatedAt { get; set; }
        public string UpdatedAt { get; set; }
        public string PublishedAt { get; set; }
        public string Password { get; set; }
    }

    public class Teacher_G
    {
        public TeacherData_G Data { get; set; }
    }

    public class TeacherData_G
    {
        public int Id { get; set; }
        public TeacherAttributes_G Attributes { get; set; }
    }

    public class TeacherAttributes_G
    {
        public string Name { get; set; }
        public string Email { get; set; }
        public string CreatedAt { get; set; }
        public string UpdatedAt { get; set; }
        public string PublishedAt { get; set; }
    }
}
