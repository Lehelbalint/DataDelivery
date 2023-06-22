using System.ComponentModel.DataAnnotations;

namespace DataDeliveryAdmin.Models
{
    public class LoginViewModel
    {
        [Required(ErrorMessage = "Az email mező kitöltése kötelező.")]
        [EmailAddress(ErrorMessage = "Érvényes email címet adj meg.")]
        public string Email { get; set; }

        [Required(ErrorMessage = "A jelszó mező kitöltése kötelező.")]
        [DataType(DataType.Password)]
        public string Password { get; set; }
    }
}
