using DataDeliveryAdmin.Models;
using Newtonsoft.Json;

namespace DataDeliveryAdmin.Models
{
	public class DepartmentViewModel
	{
		public List<DepartmentResponse> Data { get; set; }
	}

	public class DepartmentAttributes
	{
		[JsonProperty("department_name")]
		public string DepartmentName { get; set; }
		public DateTime CreatedAt { get; set; }
		public DateTime UpdatedAt { get; set; }
		public DateTime PublishedAt { get; set; }
		public int Year { get; set; }
	}
}
public class DepartmentResponse
{
	public int Id { get; set; }
	public DepartmentAttributes Attributes { get; set; }

}