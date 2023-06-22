namespace DataDeliveryAdmin.Models
{
    public class CourseViewModel
    {
        public List<CourseData> data { get; set; }
    }
    public class CourseData
    {
        public int id { get; set; }
        public CourseAttributes attributes { get; set; }
    }

    public class CourseAttributes
    {
        public string name { get; set; }
        public DateTime createdAt { get; set; }
        public DateTime updatedAt { get; set; }
        public DateTime publishedAt { get; set; }
        public TeachersData teachers { get; set; }
    }

    public class TeachersData
    {
        public List<TeacherData> data { get; set; }
    }

    public class TeacherData
    {
        public int id { get; set; }
        public TeacherAttributes attributes { get; set; }
    }

    public class TeacherAttributes
    {
        public string name { get; set; }
        public string email { get; set; }
        public DateTime createdAt { get; set; }
        public DateTime updatedAt { get; set; }
        public DateTime publishedAt { get; set; }
    }

 
}
