#Delivery to Tietoevry

##8.1

UI is created using Thymeleaf, for returning to the main code, without Thymeleaf:

```bash
git reset --hard 13814ceeee68f4f3e580a2be95a6e97c9ffc2b68
```

or look here - [Commit without Thymeleaf files](https://github.com/probzyg/bookstore/tree/13814ceeee68f4f3e580a2be95a6e97c9ffc2b68)


Description of choices:

1)I decided to create this WEB application on Spring Boot, because:

- It provides a flexibility in Java Bean, XML configurations, and Database Transactions
- It includes Servlet Container 
- Spring Boot is globally one of the most popular ways of building REST API
- It injects dependencies automatically 

2)I decided to go with DDD (Domain Driven Design), because it is a major software design approach. 
It's easy to understand and read.

3)I decided to include Thymeleaf for UI, because it is an easy way of building basic UI for current needs.

##8.2 Possible risks

- You mentioned that the system should handle 10 parallel user sessions per second. 
Make sure that there are no bottlenecks that could lead to poor performance under heavy load.

- While you've stated that the main page load time cannot exceed 2 seconds, this requirement 
might become challenging to maintain as the number of books increases.

- If the chosen technology stack becomes outdated or unsupported, it could lead to compatibility and security issues in the long run.

##8.4 Setup Guide:

You can find Setup Guide at README.md file in root folder.
