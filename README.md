# TeamManagementApp

This is a demo of a team management app, that allows managers to see what projects their employees/teams are currently assigned to. 

There are 3 different access levels depending on the persons role within the company.
- Admin - Has access to view all employees, create new employees and update employee information. When a new employee is registered, a user account will automatically be created.
- Manager - Has access to view all employees within their department/team, can update which project an employee is working on. 
- Employee - Has access to view themselves and what project they are on and can view team members of the same project/team.


## How to use 
### Web App
With a registered account, in your browser, use endpoint -> http://localhost:8080/login to navigate to the login page and enter your credentials to gain access. You will then be directed to your own homepage.

### Admin Access

The API exposes three endpoints
|#|Description|Request Type|Endpoint|
|---|---|---|---|
|1|View all employees|GET|http://localhost:8080/employees|
|2|Create a new employee (single)|POST|http://localhost:8080/admin/register/employees|
|3|Update an existing employee by employee id (provide id number as a replacement for {id} in the endpoint)|PATCH|http://localhost:8080/admin/employees/{id}|

**Note:** POST & PATCH requests have the following requirements:
1. First name, last name, role and department fields must be present.
2. Role field must be one of the following choices (Admin, Manager or Employee)
3. Department field must be one of the following choices (HR, Finance, Marketing, Sales, Design, Development or Customer Service)
4. Project field must be one of the following choices (Unassigned, New Starters, Attendance, Payroll, Accounts, Products, Advertising, Web App Frontend, Web App Backend, Logo or Web App)
5. If project field is not present, project: Unassigned, will automatically be assigned. 
6. Field inputs are not cap sensitive. 
7. When using an API platform (such as Postman), provide fields as a request body in JSON format (see examples below)

#### POST Request Example
```JSON
{
    "firstName": "Employee",
    "lastName": "Example",
    "role": "Manager",
    "department": "Design",
    "project": "Logo"
}
```

#### PATCH Request Example
PATCH request for updating existing employee does not require project to be provided.
```JSON
{
    "firstName": "Employee",
    "lastName": "Example",
    "role": "Admin",
    "department": "HR"
}
```
---

