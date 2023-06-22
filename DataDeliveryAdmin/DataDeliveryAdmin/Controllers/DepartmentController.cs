using DataDeliveryAdmin.Models;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace DataDeliveryAdmin.Controllers
{
    public class DepartmentController : Controller
    {
        Uri baseAdress = new Uri("http://192.168.100.8:1337/api/");

        private readonly HttpClient _client;

        public DepartmentController()
        {
            _client = new HttpClient();
            _client.BaseAddress = baseAdress;
        }

        [HttpGet]
        public async Task<IActionResult> Index()
        {
            DepartmentViewModel list = new DepartmentViewModel();
            HttpResponseMessage response = await _client.GetAsync("departments");

            if (response.IsSuccessStatusCode)
            {
                string data = await response.Content.ReadAsStringAsync();
                list = JsonConvert.DeserializeObject<DepartmentViewModel>(data);
            }
            return View(list);
        }

        [HttpGet]
        public IActionResult Create()
        {
            if (User.Identity.IsAuthenticated)
            {
                var userEmail = User.Identity.Name;
                return View();
            }
            else
            {
                return RedirectToAction("Login", "Account");
            }
        }

        [HttpPost]
        public IActionResult Create(AddDepartmentViewModel model)
        {
            try
            {
                string data = JsonConvert.SerializeObject(model);
                StringContent content = new StringContent(data, Encoding.UTF8, "application/json");
                HttpResponseMessage response = _client.PostAsync(_client.BaseAddress + "departments", content).Result;

                if (response.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index");
                }
            }
            catch (Exception ex)
            {
                return View();
            }

            return View();
        }
    }

}