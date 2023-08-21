# Bookstore
Welcome to the Bookstore app project built with Spring Boot and Java.

Target OS - Windows 11 64-bit
Database - PostgreSQL 

## "Short" setup guide to run locally:

Download and install JDK 17 x64 from Oracle:


[JDK 17](https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.exe)


Download and install a PostgreSQL v15.4 database for Windows:


[PostgreSQL v15.4](https://sbp.enterprisedb.com/getfile.jsp?fileid=1258649)


Download and install Docker application for Windows:


[Docker](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe?utm_source=docker&utm_medium=webreferral&utm_campaign=dd-smartbutton&utm_location=module)


Create PostgreSQL database in Docker application, using PowerShell/CMD:

```bash
docker run --name bookstore -e POSTGRES_PASSWORD=docker -p 5432:5432 -d postgres
```

Clone the repository locally:

```bash
git clone git@github.com:probzyg/bookstore.git
```

Open the project in IDE - I'm using IntelliJ IDEA 2023.1.5, selecting root folder bookstore through File->Open...

In IDE, from root folder go:

```
bookstore->src->main->java->com.example.bookstore
```

In this folder find BookstoreApplication.java file, open it and run it by pressing Run button or, 
if you use IntellIJ IDEA, just press Shift+F10 combination.

Access the Web application through url: http://localhost:{port}. In the place of {port} input the port at which Tomcat has started.
You can find it at second line from the bottom at Run console in IDE, there will be analogue of this:

```
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
```

In my case, the port is 8080, so I have to go to http://localhost:8080


The features:

At http://localhost:8080 - you get list of all the books, with the latest added in the beginning, as JSON object.
This url returns books by pages, by default - 50 at one page. 
To change the page number, you need to use url provided lower, but instead of {page number} you need to input your desired
page number, which, by default, is set to 1.

```
http://localhost:8080?page={page number}
``` 

To change the count of the books shown per page, you need to use url provided lower, but instead of {page number} you need to input your desired
page number, and instead of {size} you need to input your desired size of the page (count per page).

```
http://localhost:8080?page={page number}&size={size}
```

For example, to open page number 2 and set count per page to 25 records, you need to follow this url:

```
http://localhost:8080?page=2&size=25
```


At http://localhost:8080/add-book - 
- you need to authenticate using login "probzyg" and password "Password123"
- it returns html file to add books where you can input necessary data into input fields
- then press button "Add Book" and it adds the written book to Bookstore database. 
- After the book is added, the code returns another html file, where is written that the book has been added and the title of the book. 
Also, there is a button to redirect to add-book url. 
- If the book is already in DB the code returns html file with description and button to return to http://localhost:8080/add-book
- If you leave one or more input field empty - it return html file "Bad request" and redirection button to http://localhost:8080/add-book
- Also, you can be redirected to this url by pressing button "Add Book" at http://localhost:8080


At http://localhost:8080/e-com: 
- you can get the list of book entities for Ecommerce platforms, containing only titles of the books and prices.
- There is an api endpoint, that returns JSON data the same way - containing only price and the title of the book. 
You can find it at http://localhost:8080/e-com/api