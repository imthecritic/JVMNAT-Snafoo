# Snack Food JVM Nerdery Assessment Test

This is Jasmine Farley's submission for the JVM Nerdery Assement Test. This is a web application 
title "Snafoo," a app that allows the employees at the Nerdery vote and suggest snacks. 
It allows the user to vote up to three snacks per month, managed by cookies. 
(Flaw: you can avoid the limit by switching to incognito or clearing cookies and refreshing.)
 

This is my very first complete Spring MVC application. Any feedback would be appreciated!


Because I didn't want to spend more than the suggested 12 hours, I couldn't get to the Unit Tests because 
I wasn't familar on how to test certain things within Spring with unit tests.  
(Next time I'll try the TDD approach with a spring application)

### Timeline:

I was travelling 7/14/17-7/18/17, so I pulled down the code but wasn't able to 
work on it because I didn't have consistent internet or time, due to flights, trains, and 
 things related to tourism. When I returned I started a new retail job, so that also limited my time. 
During this time I wrote the POJO models.
 
When I started it on that Tuesday night I tried to start it but figured I needed to get familar with Spring. 
So Tuesday,Wednesday, and Thursday I was figuring things out. 

Friday-Sunday(7/21/17-7/23/17) is when I actually started coding. The project and spent about 
3-5 hours on it each of those days. 

## Base Project Architecture
* **Language**: Java 8
* **Build System**: Gradle
* **Web Framework**: Spring Boot
* **Data Persistence**: Spring Data JPA (H2 for development, MySQL for Production)
* **JSON**: Jackson
* **View Templates**: Freemarker

## Folder Structure
* `src/main/java` - Source code for the web application
* `src/main/resources` - Resources that will be included in the web application
* `test/java` - Unit tests
* `web` - Static HTML / CSS and assets that can be converted to templates for use in the project. You are not required to use these, but it
is a good way to save time.
