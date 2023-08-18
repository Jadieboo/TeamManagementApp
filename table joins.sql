
-- joins roles, dept and projects onto employee
SELECT employees.id 'Employee ID', CONCAT(employees.first_name, " ", employees.last_name) 'Name', employees.role 'Role',
departments.department 'Department',
projects.project 'Project'
FROM employees
JOIN departments
ON employees.department_id = departments.id
JOIN projects
ON employees.project_id = projects.id;

-- selects all from accounts
SELECT * FROM accounts;

-- joins accounts onto employee details (not showing password)
SELECT employees.id 'Employee ID', CONCAT(employees.first_name, " ", employees.last_name) 'Name', employees.role 'Role',
departments.department 'Department',
projects.project 'Project',
accounts.username 'Account Username'
FROM employees
JOIN departments
ON employees.department_id = departments.id
JOIN projects
ON employees.project_id = projects.id
JOIN accounts
ON accounts.employee_id = employees.id;