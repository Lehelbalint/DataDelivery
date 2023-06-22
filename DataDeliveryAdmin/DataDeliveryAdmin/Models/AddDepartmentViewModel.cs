namespace DataDeliveryAdmin.Models
{
    public class AddDepartmentViewModel
    {
         public Data data { get; set; }
    }

    public class Data
    { 
            public string department_name { get; set; }
            public string year { get; set; }
    }
}
