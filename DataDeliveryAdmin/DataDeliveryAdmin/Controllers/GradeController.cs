using DataDeliveryAdmin.Models;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System.Diagnostics;
using System.Net;
using System.Reflection;
using System.Text;

namespace DataDeliveryAdmin.Controllers
{
    public class GradeController : Controller
    {
        Uri baseAdress = new Uri("http://192.168.132.227:1337/api/");

        private readonly HttpClient _client;

        public GradeController()
        {
            _client = new HttpClient();
            _client.BaseAddress = baseAdress;
        }

        [HttpGet]
        public async Task<IActionResult> Index(string clientName)
        {
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Login", "Account");
            }
            GradeViewModel list = new GradeViewModel();
            GradeViewModel filteredList = new GradeViewModel(); 
            HttpResponseMessage response = await _client.GetAsync("grades?populate=*");

            if (response.IsSuccessStatusCode)
            {
                string data = await response.Content.ReadAsStringAsync();
                list = JsonConvert.DeserializeObject<GradeViewModel>(data);
				string loggedInTeacherEmail = User.Identity.Name;
                
                filteredList.Data = list.Data
                .Where(grade => grade.Attributes.Teacher.Data.Attributes.Email == loggedInTeacherEmail).ToList();
                if (string.IsNullOrEmpty(clientName))
                { 
                    return View(filteredList); 
                }
                else
                {
                    filteredList.Data = filteredList.Data
                        .Where(grade => grade.Attributes.Student.Data.Attributes.Neptun_Id == clientName || 
                        grade.Attributes.Course.Data.Attributes.Name==clientName).ToList();
                    return View(filteredList);
                }

			}
            return View(list);
        }
        [HttpGet]
        public IActionResult Edit(int id) {
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Login", "Account");
            }
            try
            {
                SingleGradeViewModel currentGrade = new SingleGradeViewModel();
                currentGrade.Data = new DataModelS();
                currentGrade.Data.Attributes = new AttributesModel();
                HttpResponseMessage response = _client.GetAsync(_client.BaseAddress + "grades/" + id).Result;
                if (response.IsSuccessStatusCode)
                {
                    string data = response.Content.ReadAsStringAsync().Result;
                    currentGrade = JsonConvert.DeserializeObject<SingleGradeViewModel>(data);
                }
                return View(currentGrade);
            }
            catch (Exception ex)
            {
                return View();
            }
        }
        [HttpPost]
        public async Task<IActionResult> EditAsync(SingleGradeViewModel model) {
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Login", "Account");
            }
            try
            {
                UpdateGradeViewModel updateGradeViewModel = new UpdateGradeViewModel();
                updateGradeViewModel.data = new DataModel();
                updateGradeViewModel.data.grade = model.Data.Attributes.Grade;
                updateGradeViewModel.data.percantage = model.Data.Attributes.Percantage;
                updateGradeViewModel.data.final = model.Data.Attributes.Final;
                updateGradeViewModel.data.date = model.Data.Attributes.Date.ToString("yyyy-MM-dd"); ;

                string data = JsonConvert.SerializeObject(updateGradeViewModel);
                StringContent content = new StringContent(data, Encoding.UTF8, "application/json");
                HttpResponseMessage response = _client.PutAsync(_client.BaseAddress + "grades/" + model.Data.Id, content).Result;
                if (response.IsSuccessStatusCode)
                {
                    return RedirectToAction("Index");
                }
                if (response.StatusCode == HttpStatusCode.BadRequest)
                {
                    string responseContent = await response.Content.ReadAsStringAsync();
                    var errorResponse = JsonConvert.DeserializeObject<dynamic>(responseContent);
                    string errorMessage = errorResponse.message;

                    Console.WriteLine("Hiba történt: " + errorMessage);
                    return View();
                }
                else
                    return View();
            }
            catch (Exception ex)
            {
                return View();
            }
        }

        [HttpGet]
        public IActionResult Delete(int id)
        {
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Login", "Account");
            }
            HttpResponseMessage response = _client.DeleteAsync(_client.BaseAddress + "grades/" + id).Result;

            if (response.IsSuccessStatusCode)
            {
                return RedirectToAction("Index");   
            }
            return View();
        }

		
	}
}
