using DataDeliveryAdmin.Models;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System.Security.Claims;
using System.Text;

namespace DataDeliveryAdmin.Controllers
{
    public class AccountController : Controller
    {
        [HttpGet]
        public IActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Login(LoginViewModel model)
        {
            if (ModelState.IsValid)
            {
                bool isValidUser = await AuthenticateUserAsync(model.Email, model.Password);

                if (isValidUser)
                {
                    var claims = new List<Claim>
                    {
                        new Claim(ClaimTypes.Name, model.Email)
                    };

                    var claimsIdentity = new ClaimsIdentity(
                        claims, CookieAuthenticationDefaults.AuthenticationScheme);

                    await HttpContext.SignInAsync(
                        CookieAuthenticationDefaults.AuthenticationScheme,
                        new ClaimsPrincipal(claimsIdentity));


                    return RedirectToAction("Index", "Home");


                }
                else
                {
                    ModelState.AddModelError(string.Empty, "Hibás felhasználónév vagy jelszó.");
                    return View(model);
                }
            }

            return View(model);
        }

        [HttpGet]
        public async Task<IActionResult> Logout()
        {
            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            return RedirectToAction("Index", "Home");
        }

        private async Task<bool> AuthenticateUserAsync(string email, string password)
        {
            using (var client = new HttpClient())
            {
                var loginData = new Dictionary<string, string>
        {
            { "identifier", email },
            { "password", password }
        };

                var json = JsonConvert.SerializeObject(loginData);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                var response =client.PostAsync("http://localhost:1337/api/auth/local", content).Result;

                if (response.IsSuccessStatusCode)
                {
                    var responseContent = await response.Content.ReadAsStringAsync();
                    var tokenResponse = JsonConvert.DeserializeObject<TokenResponse>(responseContent);

                    // Itt ellenőrizheted a tokenResponse-t vagy végrehajthatod további logikát

                    return true;
                }

                return false;
            }
        }
        public class TokenResponse
        {
            public string jwt { get; set; }
            public UserResponse user { get; set; }
        }

        public class UserResponse
        {
            public string email { get; set; }
            // További felhasználói adatokat adhatsz hozzá a válaszhoz
        }
    }
}
