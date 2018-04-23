# OTM-Studytrack

Studytrack is a tool for tracking time spent on university coursework. It allows the user to define courses, tasks related to those courses, and add individual entries on time spent. It provides various ways to access the accumulated data to aid in the planning of future studies.

## Documentation

[Requirements specification](https://github.com/elucca/OTM-Studytrack/blob/master/documentation/software%20requirements%20specification.md)

[Architecture description](https://github.com/elucca/OTM-Studytrack/blob/master/documentation/architecture.md)

[Timesheet](https://github.com/elucca/OTM-Studytrack/blob/master/documentation/timesheet.md)

## Command line functionality

Tests are ran using the command:
```
mvn test
```

A test coverage report is created using the below command. The report can be accessed by opening the file _target/site/jacoco/index.html_ using a browser.
```
mvn jacoco:report
```

Checkstyle is ran using the below command. Checkstyle follows the criteria defined in [checkstyle.xml](https://github.com/elucca/OTM-Studytrack/blob/master/OTM-Studytrack/checkstyle.xml). Potential errors can be seen by opening the file _target/site/checkstyle.html_ using a browser.
```
mvn jxr:jxr checkstyle:checkstyle
```

A jar package of the program is created in the _target_ folder using the following command.
```
mvn package
```

