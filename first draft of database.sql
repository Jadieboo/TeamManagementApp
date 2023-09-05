
DROP DATABASE IF EXISTS tma;	
CREATE DATABASE tma;
USE tma;
 
CREATE TABLE roles (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
role VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS projects;
CREATE TABLE projects (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
project VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE departments (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
department VARCHAR(255) NOT NULL
);

CREATE TABLE employees (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
role_id INT NOT NULL,
department_id INT NOT NULL,
project_id INT DEFAULT 1,
FOREIGN KEY(role_id) REFERENCES roles(id),
FOREIGN KEY(department_id) REFERENCES departments(id),
FOREIGN KEY(project_id) REFERENCES projects(id)
);

CREATE TABLE accounts (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(50) NOT NULL,
employee_id INT,
FOREIGN KEY(employee_id) REFERENCES employees(id)
);

-- INSERTING DATA

INSERT INTO roles (role) 
VALUES 
('Admin'),
('Manager'),
('Employee');

INSERT INTO projects (project) 
VALUES 
('Unassigned'),
('New Starters'),
('Attendance'),
('Payroll'),
('Accounts'),
('Products'),
('Advertising'),
('Web App Frontend'),
('Web App Backend'),
('Logo'),
('Web App');


INSERT INTO departments (department) 
VALUES
('HR'),
('Finance'),
('Marketing'),
('Sales'),
('Design'),
('Development');

INSERT INTO employees (first_name, last_name, role_id, department_id, project_id)
VALUES
('Danny', 'Kirwan', 2, 5, 1),
('Jade', 'Sale', 1, 1, 2),
('Adam','Colbat', 3, 5, 10);

INSERT INTO employees (first_name, last_name, role_id, department_id)
VALUES
('Joe', 'Bloggs', 2, 3);

INSERT INTO accounts (email, password, employee_id)
VALUES 
('dKirwan@mail.com', 'dk123', 1),
('jSale@mail.com', 'js123', 2),
('aColbat@mail.com', 'ac123', 3),
('jBloggs@mail.com','jb123', 4);


DELETE FROM employees 
WHERE id = 5;

SELECT * FROM employees;

SELECT * FROM accounts;



