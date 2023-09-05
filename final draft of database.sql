
DROP DATABASE IF EXISTS tma;	
CREATE DATABASE tma;
USE tma;

CREATE TABLE projects (
id INT PRIMARY KEY AUTO_INCREMENT,
project VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE departments (
id INT PRIMARY KEY AUTO_INCREMENT,
department VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE employees (
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
role VARCHAR(25) NOT NULL, -- ENUM
department_id INT NOT NULL,
project_id INT NOT NULL DEFAULT 1,

FOREIGN KEY(department_id) REFERENCES departments(id),
FOREIGN KEY(project_id) REFERENCES projects(id)
) AUTO_INCREMENT = 101;

create table accounts (
app_user_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
role 		  VARCHAR(25) NOT NULL,     -- ENUM
enable        BOOLEAN     NOT NULL DEFAULT true,
locked        BOOLEAN     NOT NULL DEFAULT false,
username      varchar(255) NOT NULL UNIQUE,
password      varchar(255) NOT NULL,
employee_id   INT NOT NULL,

FOREIGN KEY(employee_id) REFERENCES employees(id)
);

-- INSERTING DATA

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
('Development'),
('Customer Service');

INSERT INTO employees (first_name, last_name, role, department_id, project_id)
VALUES
('Danny', 'Kirwan', 'MANAGER', 5, 1),
('Jade', 'Sale', 'ADMIN', 1, 2),
('Adam','Colbat', 'EMPLOYEE', 5, 10),
('Joe', 'Bloggs', 'MANAGER', 3, 1);

INSERT INTO accounts (username, password, role, employee_id)
VALUES 
('dkirwan101@company.com', 'dk123', 'MANAGER', 101),
('jsale102@company.com', 'js123', 'ADMIN', 102),
('acolbat103@company.com', 'ac123', 'EMPLOYEE', 103),
('jbloggs104@company.com','jb123', 'MANAGER', 104);






