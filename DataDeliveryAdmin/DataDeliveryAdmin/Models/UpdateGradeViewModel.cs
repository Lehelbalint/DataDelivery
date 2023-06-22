namespace DataDeliveryAdmin.Models
{
	public class UpdateGradeViewModel
	{
		public DataModel data { get; set; }
	}

	public class DataModel
	{
		public int grade { get; set; }
		public int percantage { get; set; }
		public bool final { get; set; }
		public string date { get; set; }
	}
}
