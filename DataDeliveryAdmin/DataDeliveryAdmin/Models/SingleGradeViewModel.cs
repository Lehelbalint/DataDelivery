namespace DataDeliveryAdmin.Models
{

		public class SingleGradeViewModel
	{
			public DataModelS Data { get; set; }
		}

		public class DataModelS
		{
        public DataModelS()
        {
            Id = 0; // Alapértelmezett érték
            Attributes = new AttributesModel();
        }

        public int Id { get; set; }
        public AttributesModel Attributes { get; set; }
    }

		public class AttributesModel
		{
			public int Grade { get; set; }
			public int Percantage { get; set; }
			public bool Final { get; set; }
			public DateTime CreatedAt { get; set; }
			public DateTime UpdatedAt { get; set; }
			public DateTime PublishedAt { get; set; }
			public DateTime Date { get; set; }
		}
	}

