-- joins roles onto employee
SELECT e.id, e.first_name, e.last_name, r.role
FROM employees e
JOIN roles r
ON e.role_id = r.id;

-- joins roles, dept and projects onto employee
SELECT e.id, e.first_name, e.last_name, r.role, d.department, p.project
FROM employees e
JOIN roles r
ON e.role_id = r.id
JOIN departments d
ON e.department_id = d.id
JOIN projects p
ON e.project_id = p.id;