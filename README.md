# OTM-Studytrack

Studytrack is a tool for tracking time spent on university coursework. It allows the user to define courses, tasks related to those courses, and add individual entries on time spent. It provides various ways to access the accumulated data to aid in the planning of future studies.

Note: All user data is stored locally in db/studytrack.db. The folder and database file will be created on program execution inside the folder the program resides in if they do not already exist.

## Documentation

[User manual](https://github.com/elucca/OTM-Studytrack/blob/master/documentation/manual.md)

[Requirements specification](https://github.com/elucca/OTM-Studytrack/blob/master/documentation/software%20requirements%20specification.md)

[Architecture description](https://github.com/elucca/OTM-Studytrack/blob/master/documentation/architecture.md)

[Timesheet](https://github.com/elucca/OTM-Studytrack/blob/master/documentation/timesheet.md)

## Releases

Week 6 - [v0.2-alpha](https://github.com/elucca/OTM-Studytrack/releases/tag/week6)

Week 5 - [v0.1-alpha](https://github.com/elucca/OTM-Studytrack/releases/tag/week5)

## Command line functionality

All console commands need to be ran in the OTM-Studytrack subfolder which contains the application, i.e. the folder with the _pom.xml_ file.

### Tests

Tests are ran using the command:
```
mvn test
```

A test coverage report is created using the below command. The report can be accessed by opening the file _target/site/jacoco/index.html_ using a browser.
```
mvn jacoco:report
```

### Checkstyle

Checkstyle is ran using the below command. Checkstyle follows the criteria defined in [checkstyle.xml](https://github.com/elucca/OTM-Studytrack/blob/master/OTM-Studytrack/checkstyle.xml). Potential errors can be seen by opening the file _target/site/checkstyle.html_ using a browser.
```
mvn jxr:jxr checkstyle:checkstyle
```

### Javadoc

Javadoc is generated using the command:
```
mvn javadoc:javadoc
```
The generated Javadocs are located at target/site/apidocs/index.html, which can be opened using a browser.

### Generating an executable jar package

A jar package of the program is created in the _target_ folder using the following command.
```
mvn package
```

