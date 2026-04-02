# 📚 CodeCraftHub API Reference

Base URL: `http://localhost:8080`

---

## 1. Get All Courses
Retrieve a list of all saved courses.

* **URL:** `/api/courses`
* **Method:** `GET`
* **Success Response:** `200 OK`

```json
[
  {
    "id": 1,
    "name": "Python Basics",
    "description": "Learn Python fundamentals",
    "target_date": "2025-12-31",
    "status": "Not Started",
    "created_at": "2026-04-02 10:30:00"
  }
]
```

---

## 2. Get Course by ID
Retrieve a specific course using its ID.

* **URL:** `/api/courses/{id}`
* **Method:** `GET`
* **Success Response:** `200 OK`
* **Error Response:** `404 Not Found`

```json
{
  "id": 1,
  "name": "Python Basics",
  "description": "Learn Python fundamentals",
  "target_date": "2025-12-31",
  "status": "Not Started",
  "created_at": "2026-04-02 10:30:00"
}
```

---

## 3. Add a New Course
Create a new course entry.

* **URL:** `/api/courses`
* **Method:** `POST`
* **Headers:** `Content-Type: application/json`
* **Success Response:** `201 Created`

```json
{
  "name": "Spring Boot API",
  "description": "Mastering backend development",
  "target_date": "2026-10-15",
  "status": "Not Started"
}
```

---

## 4. Update a Course
Update an existing course.

* **URL:** `/api/courses/{id}`
* **Method:** `PUT`
* **Headers:** `Content-Type: application/json`
* **Success Response:** `200 OK`
* **Error Response:** `404 Not Found | 400 Bad Request`

```json
{
  "status": "In Progress"
}
```

---

## 5. Delete a Course
Remove a course from the list.

* **URL:** `/api/courses/{id}`
* **Method:** `DELETE`
* **Success Response:** `200 OK`
* **Error Response:** `404 Not Found`

```json
{
  "message": "Course deleted successfully"
}
```

---

## 6. Get Course Statistics
Retrieve a summary dashboard of all learning progress.

* **URL:** `/api/courses/stats`
* **Method:** `GET`
* **Success Response:** `200 OK`

```json
{
  "total_courses": 3,
  "in_progress": 1,
  "not_started": 2,
  "completed": 0
}
```
