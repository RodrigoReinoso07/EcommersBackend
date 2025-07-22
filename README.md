Pasos para ejecutar el backend

1) crear archivo .env en el directorio del proyecto al mismo nivel que el archivo pom.xml
2) cargar las siguientes variables en dicho archivo
3) Ejecutar el siguiente Script en MySQL Workbench:
   CREATE DATABASE ecommerse
5) Cargar en el Archivo .env generado anteriormente:

DB_URL=jdbc:mysql://localhost:3306/ecommerce
DB_USER=root ("o credenciales que posea en mysql workbench")
DB_PASS=root ("o credenciales que posea en mysql workbench")
FRONTEND_URL=http://localhost:5173/ ("url frontend") 

4) Ejecutar backend con F5

5) Ejecutar el siguiente SCRIPT en Mysql Workbench:

   USE ecommerse;
   INSERT INTO user (id, createdat, enable, firs_name, last_name, password, updatedat, username) VALUES ('1', '2025-07-22 12:48:51.234808', '1', 'admin', 'admin', '$2a$10$7JmbSkyZLKDBp3qdt/CTG.G2ZTx/pIUix44AykPJWQyPifScHjQfq', '2025-07-22 12:48:51.234808', 'admin');
   INSERT INTO user (id, createdat, enable, firs_name, last_name, password, updatedat, username) VALUES ('2', '2025-07-22 12:56:13.169108', '1', 'user', 'user', '$2a$10$4i1O1xzbCFFdZjdToKxeKeMY9nDMWnNtYIoye.sw4PvZ1l32Bfo4a', '2025-07-22 12:56:13.169108', 'user');
   INSERT INTO user_role (id, createdat, role, updatedat, user_id) VALUES ('1', '2025-07-17 00:43:59.637227', 'ROLE_USER', '2025-07-22 12:48:51.234808', '1');
   INSERT INTO user_role (id, createdat, role, updatedat, user_id) VALUES ('2', '2025-07-17 00:43:59.637227', 'ROLE_ADMIN', '2025-07-22 12:48:51.234808', '2');
   INSERT INTO user_role (id, createdat, role, updatedat, user_id) VALUES ('3', '2025-07-17 00:43:59.637227', 'ROLE_USER', '2025-07-22 12:48:51.234808', '2');

6) Utilizar la aplicacion.


