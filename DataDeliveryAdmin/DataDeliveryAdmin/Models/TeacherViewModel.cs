namespace DataDeliveryAdmin.Models
{
    public class TeacherViewModel
    {
        public List<Teacher> Data { get; set; }
       
    }

    public class Teacher
    {
        public int Id { get; set; }
        public TeacherAttributes Attributes { get; set; }
    }

    public class TeacherAttributes_T
    {
        public string Name { get; set; }
        public string Email { get; set; }
        public DateTime CreatedAt { get; set; }
        public DateTime UpdatedAt { get; set; }
        public DateTime PublishedAt { get; set; }
    }

}
