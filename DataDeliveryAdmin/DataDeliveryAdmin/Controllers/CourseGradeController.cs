using DataDeliveryAdmin.Models;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using System.Text;

namespace DataDeliveryAdmin.Controllers
{
    public class CourseGradeController : Controller
    {
        Uri baseAdress = new Uri("http://192.168.132.227:1337/api/");

        private readonly HttpClient _client;

        public CourseGradeController()
        {
            _client = new HttpClient();
            _client.BaseAddress = baseAdress;
        }

        [HttpGet]
        public async Task<IActionResult> Index()
        {
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Login", "Account");
            }
            CourseViewModel list = new CourseViewModel();
            HttpResponseMessage response = await _client.GetAsync("courses?populate=*");

            if (response.IsSuccessStatusCode)
            {
                string data = await response.Content.ReadAsStringAsync();
                list = JsonConvert.DeserializeObject<CourseViewModel>(data);
               
                List<CourseData> selectedCourses = new List<CourseData>();

                foreach (var courseData in list.data)
                {
                    foreach (var teacherData in courseData.attributes.teachers.data)
                    {
                        if (teacherData.attributes.email == User.Identity.Name)
                        {
                            selectedCourses.Add(courseData);
                            break;
                        }
                    }
                }

                // Új CourseViewModel példány létrehozása a kiválasztott kurzusokkal
                CourseViewModel selectedViewModel = new CourseViewModel
                {
                    data = selectedCourses
                };
                return View(selectedViewModel);
            }
            return View(list);
    
   
        }

        [HttpGet]
        public async Task<IActionResult> CourseStudents(int id)
        {
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Login", "Account");
            }
            StudentViewModel list = new StudentViewModel();
            HttpResponseMessage response = await _client.GetAsync("students?populate=*");

            if (response.IsSuccessStatusCode)
            {
                string data = await response.Content.ReadAsStringAsync();
                list = JsonConvert.DeserializeObject<StudentViewModel>(data);

                List<Student> selectedStudents = new List<Student>();

                foreach (var studentData in list.data)
                {
                    foreach (var courseData in studentData.attributes.courses.data)
                    {
                        if (courseData.id == id)
                        {
                            selectedStudents.Add(studentData);
                            break;
                        }
                    }
                }

                // Új CourseViewModel példány létrehozása a kiválasztott kurzusokkal
                StudentViewModel selectedViewModel = new  StudentViewModel
                {
                    data = selectedStudents
                };
                selectedViewModel.Courseid = id;
                return View(selectedViewModel);
            }
            return View(list);
        }

		[HttpGet]
		public async Task<IActionResult> AddGrade(int courseId,int studentId)
		{
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Login", "Account");
            }
            IdGradeViewModel model = new IdGradeViewModel()
            {
                CourseId = courseId,
                StudentId = studentId
            };
            TeacherViewModel list = new TeacherViewModel();
            HttpResponseMessage response = await _client.GetAsync("teachers");

            if (response.IsSuccessStatusCode)
            {
                string data = await response.Content.ReadAsStringAsync();
                list = JsonConvert.DeserializeObject<TeacherViewModel>(data);
                Teacher targetTeacher = list.Data.FirstOrDefault(s => s.Attributes.email == User.Identity.Name);
                if (targetTeacher != null)
                {
                    model.TeacherId = targetTeacher.Id;
                }

            }
            return View(model);
		}

        [HttpPost]
        public IActionResult AddGrade(IdGradeViewModel model)
        {
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Login", "Account");
            }

            PostGradeViewModel item = new PostGradeViewModel();

            // Adatok feltöltése a GradeData_P objektumba
            item.data = new GradeData_P
            {
                grade = model.Grade,
                percantage = model.Percentage,
                date = model.Date.ToString("yyyy-MM-dd"),
                course = new CourseConnect
                {
                    connect = new List<int> { model.CourseId }
                },
                student = new StudentConnect
                {
                    connect = new List<int> { model.StudentId }
                },
                teacher = new TeacherConnect
                {
                    connect = new List<int> { model.TeacherId }
                }
            };
            if (model.Percentage == 100)
            {
                item.data.final = true;
            }
            else
            {
                item.data.final = false;
            }
            try
            {
                string data = JsonConvert.SerializeObject(item);
                StringContent content = new StringContent(data, Encoding.UTF8, "application/json");
                HttpResponseMessage response = _client.PostAsync(_client.BaseAddress + "grades", content).Result;

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
