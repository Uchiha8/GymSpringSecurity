## Gym Spring Security Application

### How to run

* Configure jdk 17(first I tried develpe the application with 21 but there were problems while running the application)
* PostgresSQL relational database(databse name = 'finaldemo', password = #####)
* In order to test REST API end points swagger link: http://localhost:8080/swagger-ui/index.html#/

### Architecture of Application

#### Domain (Domain is a package which includes 5 entites of our model and couple of enum classes)

  * User
  * Trainee
  * Trainer
  * TrainingType
  * Training
  * Role(Enum)
  * Token
  * TokenType(Enum)

#### DTO (DTO is a package which includes our Request and Response dto inorder to hide our actual models, as we know our dto classes should me immutable class therefore they are implemented in record class type) 

#### Request

* StatusRequest
* ChangeLogin
* Login
* TraineeRegistrationRequest
* TraineeTrainingsRequest
* TrainerRegistrationRequest
* TrainerTrainingsRequest
* TrainingRequest
* UpdateTraineeRequest
* UpdateTrainerRequest

#### Response

* RegistrationResponse
* TraineeList
* TraineeProfileResponse
* TraineeTrainingsResponse
* TrainersList
* TrainerProfile
* TrainerTrainingsResponse
* UpdateTraineeResponse
* UpdateTrainerResponse

#### Repository (Repository is layer which stands between Database and Busnees logic layer. Implemented using Hibernate ORM using SessionFactory interface I executed crud operations on modules)

* UserRepository
* TraineeRepository
* TrainingRepository
* TrainingTypeRepository
* TrainerRepository
* TokenRepository

#### Service (Service layer which is localed between DAO and Controller. The main purpose of this layer is hide complaxity and busines logic of the request from client side. I mean Controller is for only handling request and return responce toward REST APIs. However, service classes is only for busines logic part)

* UserService
* TraineeService
* TrainerService
* TrainingService
* TrainingTypeService
* JwtService
* LogoutHandlerService

#### Utils (This folder basicly responsible for logger messages as well as validation of requests whether they are provided properly or not it checks for that)
* LoggingAspect
* ValidModule

#### Controller (Controller layer is for handling request from client side and return proper Response account condition of the result it provides proper status code and proper logger message about success or failer of operations to depeloper)

*  AuthenticationController
*  TraineeController
*  TrainerController
*  TrainingController
*  TrainingTypeController

#### Configuration (Well, this folder consistce of couple of configurations like swagger configuration with Authentication I mean with tokens, and cuple of beans for example passwordEncoder or AuthenticationManager and ect..., configuration for SecurityFilterChain in order to controler which endpoints permited all or has specific role smth like that, and last but not least filtering token checking for validations proccess.)

* ApplicationConfig
* JwtAuthenticationFilter
* OpenApiConfiguration
* SecurityConfiguration

#### resources (Basicly, Spring Boot auto-configures all the things for me I just add couple of configurations like database connections its in application.properties)

*application.properties

### Required Tasks for this module
  * Add Spring Security module to your project and configurate it for Authentication access for all endpoints (except Create Trainer/Trainee profile). 
  * Use Username/Password combination.   
  * Use salt and hashing to store user passwords in DB.
  * Configure Spring Security to use Login functionality.
  * Add Brute Force protector. Block user for 5 minutes on 3 unsuccessful logins
  * Implement Logout functionality and configure it in Spring Security. 
  * Implement Authorization − Bearer token for Create Profile and Login functionality Use JWT token implementation. 
  * Configure CORS policy in Spring Security.

### Implementations of required tasks
* Implemented Spring Security task and provided Authentication and Authorization
* While Authenticating we check username and password in order to return token
* Implemented PasswordEncoder while registering using BCryptPasswordEncoder concrete class which implements PasswordEncoder interface
* Login functionality implemented
* Unfortunatly, my eventHandler did not work while counting failed login attemps, that's why I could not implements this task
* Logout method developed really well while user logouts his/her tokens automaticlly revokes and expires, and and it will not satisfy the validation conditions
* GenerateToken function generates token in Bearer type and tokens are implemented in JWT token format using io.jsonwebtoken libraries like (jjwt-api, jjwt-impl, jjwt-jackson)
* CORS configuration allows requests from any origin, any method, and with any headers.

### TEST Coverage
Unit tests are implemented except for configuration and reached to 83% linear test coverage which satisfies the condition.
