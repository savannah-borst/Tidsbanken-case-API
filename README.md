# Noroff Bootcamp Tidsbanken Case API
[![web](https://img.shields.io/static/v1?logo=heroku&message=Online&label=Heroku&color=430098)](https://api-tidsbanken-case.herokuapp.com/swagger-ui/index.html)

## Assignment
The Tidsbanken Case API is a Java Spring Boot application which exposes several web API endpoints. 
The application is connected with a PostgreSQL database for storing data and all endpoints are OAUTH2.0 protected with Keycloak as an identity provider. 
The application is used in combination with a single page web application that simulates a vacation request calendar called The Tidsbanken Vacation Management System.

## How To Use

### Swagger
All endpoint documentation on Swagger is available via this [website](https://api-tidsbanken-case.herokuapp.com/swagger-ui/index.html) hosted by Heroku.

#### How To Login 

To use the endpoints in Swagger u need to authorize. After being redirected to keycloak you need to insert the following credentials:

As normal user with: <br />
Username: testuser <br />
Password: 1234

And as admin with: <br />
Username: testadmin <br />
Password: 12345

### Tidsbanken Vacation Management System
The single page web application which consumes the API endpoints is available at this [website](https://morning-citadel-07481.herokuapp.com/login) hosted by Heroku.

#### How To Login 

The user can login as user with: <br />
Username: testuser <br />
Password: 1234

And as admin using: <br />
Username: testadmin <br />
Password: 12345

## Future works
- Add settings functionality
- Add vacationdays functionality

## Built With
[IntelliJ IDEA](https://www.jetbrains.com/idea/)

[Spring Framework](https://spring.io/)

[Hibernate](https://hibernate.org/)

[PostgreSQL](https://www.postgresql.org/)

[Swagger](https://swagger.io/)

[Docker](https://www.docker.com/)

[Heroku](https://www.heroku.com/)

## Credits
[Dianto Bosman](https://github.com/diantobosman)

[Iljaas Dhonre](https://github.com/iljaasdhonre)

[Savannah Borst](https://github.com/savannah-borst)

## License
[MIT](https://choosealicense.com/licenses/mit/)
