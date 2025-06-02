# Bulletin-Implementation
Simple bulletin board web application using Spring Boot Web Framework with bootstrap

### Requirement

1. PostgreSQL
2. JDK 21

### Step to run the project

1. Create table in PostgreSQL using **table.sql** file
2. Run **dump.sql** for sample data
3. Create **.env** file in the root of project
4. Add this 3 line and edit to match your database path, username, and password

   ```properties
   DB_URL=jdbc:postgresql://localhost:5432/Your_Database_name
   DB_Username=postgres
   DB_Password=your_password
   ```
5. Build the project using maven `./mvnw clean package`
6. Run the project `./src/main/java/com.hansen.BulletinPost/BulletinPostApplication.java`
