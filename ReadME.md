# Cinema

# Table of Contents

[Project purpose](#purpose)

[Project structure](#structure)

[For developer](#developer)

[Running application](#apprun)

[Controllers](#controllers)

## <a name='purpose'></a>Project purpose

Purpose of this project is to get used with Spring and Hibernate.

This application is simple version of backend version of cinema web site.<br>
There are REST controllers for:
* Register user and log in with DB authentication
* Adding movies and getting list of all movies
* Adding cinema halls and getting list of all cinema halls
* Adding movie sessions and getting movie session for certain movie at certain date
* Adding tickets into shopping cart and getting all tickets in shopping cart
* Completing order and getting order history

## <a name='structure'></a>Used technologies

- Java 11
- Hibernate
- Spring WebMVC
- Spring Security
- Tomcat
- Maven
- MavenCheckstylePlugin 3.1.0
- Log4j2
- Lombok

## <a name='developer'></a>For developer
To run this project you need to install:

- <a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html">Java 11</a>
- <a href="https://tomcat.apache.org/download-90.cgi">Tomcat</a>
- <a href="https://www.mysql.com/downloads/">MySQL 8</a>

<hr>

Add this project to your IDE as Maven project.

Add Java SDK 11 in project structure.

Configure Tomcat:
- Add artifact
- Add Java SDK 11

Change a path to your Log file in **src/main/resources/log4j2.xml** on line 4.

<hr>

To configure MySQL settings use file **src/main/resources/db.properties**:
- Create schema "cinema" or change to your schema name in **db.url**
- Change **db.url** to your MySQL address 
(for example jdbc:mysql://localhost:3306/cinema where cinema is your schema name)
- Change **db.username** and **db.password** to username and password for your MySQL

## <a name='apprun'></a>Running application

By default application will initialize with two roles **USER** and **ADMIN** and two users:
* username: **admin@admin.com**, password: **123**, role: **ADMIN**

When first time starts, it will open page for inject init data. Use admins credentials to do it.

## <a name='controllers'></a> Controllers
***"http://localhost:8080/cinema_war_exploded/"***

* **POST** [PERMIT ALL] ***"/login"*** {"email": "", "password":""} - login user
* **POST** [PERMIT ALL] ***"/registration"*** {"email": "", "password":"", "repeatPassword":""} - register user

***"/cinemahalls"***
*    **GET** [ANY ROLE] ***"/"*** - get all cinema halls
*   **POST** [ADMIN] ***"/add"*** {"capacity": , "description": ""} - add new cinema hall
    
***"/movies"***
*    **GET** [ANY ROLE] ***"/"*** - get all movies
*   **POST** [ADMIN] ***"/add"*** {"title": "", "description": ""} - add new movie
    
***"/moviesessions"***
* **GET** [ANY ROLE] ***"/"*** - get all movie sessions
* **POST** [ADMIN] ***"/add"*** {"movieId": , "cinemaHallId": , "showTime":""} - add new movie session
* **GET** [ANY ROLE] ***"/available?movie_id=&date="*** - get available movie sessions
        
***"/orders"***
*    **POST** [ANY ROLE] ***"/"*** - get all user orders
*   **POST** [ANY ROLE] ***"/compete"*** - complete user orders in shopping cart
    
***"/shoppingcarts"***
*   **GET** [ANY ROLE] ***"/byuser"*** - get user shopping cart
*   **POST** [ADMIN] ***"/addmoviesession"*** {"movieId": , "cinemaHallId": , "showTime":""} - add movie session to shopping cart

***"/users"***
*    **GET** [ADMIN] ***"/byemail"*** - get user by email
*   **POST** [ADMIN] ***"/add"*** {"email": "", "password":""} - add new user
