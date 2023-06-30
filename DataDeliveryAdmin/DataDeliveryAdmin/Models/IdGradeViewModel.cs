namespace DataDeliveryAdmin.Models
{
	public class IdGradeViewModel
	{
		public int CourseId { get; set; }
		public int StudentId { get; set;}
		public int TeacherId { get; set;}
		public int DepartmentId { get; set;}
		public int Grade { get; set;}
		public int Percentage { get; set;}
		public DateTime Date { get; set; }

		public List<Data_G> grades { get; set; }

		public float finalGrade { get; set; }
	}
}