## Gym Spring MVC REST API Application

### How to run

* Configure java 21 version
* Developed using PostgresSQL relational database(databse name = 'hibernate', password = #####)
* Recommended Tomcat 10.1 server in order to deploy our artifact files

### Architecture of Application

#### Domain (Domain is a package which includes 5 entites of our model)

  * User
  * Trainee
  * Trainer
  * TrainingType
  * Training

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

#### Service (Service layer which is localed between DAO and Controller. The main purpose of this layer is hide complaxity and busines logic of the request from client side. I mean Controller is for only handling request and return responce toward REST APIs. However, service classes is only for busines logic part)

* UserService
* TraineeService
* TrainerService
* TrainingService
* TrainingTypeService

#### Controller (Controller layer is for handling request from client side and return proper Response account condition of the result it provides proper status code and proper logger message about success or failer of operations to depeloper)

*  UserController
*  TraineeController
*  TrainerController
*  TrainingController
* TrainingTypeController

#### Configuration (In case it is not SPring Boot therefore all configurations implemented manually, database connections, and configuration with tomcat server in order to deploy our jar file of project to localhost and send request to RestAPI)

* AppConfiguration
* SpringInitializer

#### resources (In resources class path I have mainly two configuration file)

* hibernate.cfg.xml (This configuration file mainly for connection with PostgresSQL database and Mapping entities into table)
* log4j2.xml (Logger configuration)

### Required Tasks for this module
  * Add Spring Security module to your project and configurate it for Authentication access for all endpoints (except Create Trainer/Trainee profile). 
  * Use Username/Password combination.   
  * Use salt and hashing to store user passwords in DB.
  * Configure Spring Security to use Login functionality.
  * Add Brute Force protector. Block user for 5 minutes on 3 unsuccessful logins
  * Implement Logout functionality and configure it in Spring Security. 
  * Implement Authorization − Bearer token for Create Profile and Login functionality Use JWT token implementation. 
  * Configure CORS policy in Spring Security. 

