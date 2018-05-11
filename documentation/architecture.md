# Architecture description

## Structure

The overall structure of the program follows a three tier architecture with the following package structure:

![Package diagram](misc/package_diagram.png)

An overall package and class diagram of the program:

![UML diagram](misc/architecture_diagram.png)

## Text interface

The program currently uses a simple console interface. It is isolated from program logic through dependency injection: To interact with program logic, the TextUI class relies on an instance of StudytrackService passed to it as a cosntructor parameter.

## Program logic

Program logic functionality is provided by the StudytrackService class. It implements all functionality required by the UI layer, and calls methods of the persistence layer (otmstudytrack.database package) in order to retrieve and save data. Persistence layer classes are isolated from program logic and are provided through dependency injection.

The types of user data handled by the program (courses, task types and task entries) are represented by their respective classes, which reside in the otmstudytrack.domain.data package. They possess the following structure:

![Data class diagram](misc/datadiagram.png)

## Persistence layer

Data persistence is implemented using an Sqlite3 database. Interacting with the database is the sole responsibility of the otmstudytrack.database package. The Database class serves as an abstraction of the database itself and handles its creation and connectivity. The classes handling the the persistence of each type of user data - SqlCourseDao, SqlTaskTypeDao, SqlEntryDao - follow the Data Access Object pattern.

## Control flow example

The following sequence diagram depicts the control flow in the case of the user adding a course which does not yet exist in the database. This is a representative example of the manner in which the user layer, domain layer and data access layers interact.

![Adding a course - sequence diagram](misc/addcourseseq.png) 
