# TeamManagementApp

Team Management App (TMA) is a management system designed to make managing project allocation easier and improve collaboration.

There are 3 different access levels depending on the persons role within the company.
- Admin - Can view all employees, create new employees and update employee information. When a new employee is registered, a company user account will automatically be created.
- Manager - Can view each employee within their team and see what project they are assigned to. Also has the ability to assign them to a different project. 
- Employee - Can view their colleagues who are working on the same project as them.


## How to use 
### Web App
With a registered account, in your browser, use endpoint -> http://localhost:8080/login to navigate to the login page and enter your credentials to gain access. You will then be directed to your own homepage. Endpoints listed below will also be accessible through the web application using navigational links.

<!-- CHECK ALL IMPORTANT WEB ENDPOINTS ARE COVERED -->

|Access|Description|Request Type|Endpoint|
|---|---|---|---|
|Admin|Create new employee page|GET|http://localhost:8080/admin/new/employees|
|Admin|View all employees|GET|http://localhost:8080/admin/view/employees|
|Manager|View employees within team|GET|http://localhost:8080/manager/view/employees|
|Manager|View employee by id (provide id number as a replacement for {id} in the url)|GET|http://localhost:8080/manager/view/employees/{id}|
|Manager|Update an employee's assigned project (provide id number as a replacement for {id} in the url)|PATCH|http://localhost:8080/manager/view/employees/update/{id}|
|Employee|View colleagues within team|GET|http://localhost:8080/employee/view/colleagues|


### API
#### Admin Access

The API exposes three endpoints
|#|Description|Request Type|Endpoint|
|---|---|---|---|
|1|View all employees|GET|http://localhost:8080/admin/employees|
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

<!-- MIGHT NEED TO TAKE THE EMPLOYEE ACCESS OUT -->

#### Employee Access
The API exposes two endpoints
|#|Description|Request Type|Endpoint|
|---|---|---|---|
|1|View all colleagues in the same team (including admins & managers)|GET|http://localhost:8080/employee/colleagues|
|2|View all colleagues in the same team and project (not including admins & managers)|GET|http://localhost:8080/employee/colleagues/project|
