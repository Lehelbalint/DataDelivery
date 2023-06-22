namespace DataDeliveryAdmin.Models
{
    public class PostGradeViewModel
    {
        public GradeData_P data { get; set; }
    }
    public class GradeData_P
    {
        public int grade { get; set; }
        public int percantage { get; set; }
        public bool final { get; set; }
        public string date { get; set; }
        public CourseConnect course { get; set; }
        public StudentConnect student { get; set; }
        public TeacherConnect teacher { get; set; }
    }

    public class CourseConnect
    {
        public List<int> connect { get; set; }
    }

    public class StudentConnect
    {
        public List<int> connect { get; set; }
    }

    public class TeacherConnect
    {
        public List<int> connect { get; set; }
    }
}
