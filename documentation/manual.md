# User manual

## Acquiring and running program

The newest release (and all previous releases) can be found at the ![Releases page](https://github.com/elucca/OTM-Studytrack/releases)

The program is ran using the following console command:
```
java -jar <name of file>.jar
```
For example, for the first release the command would be:
```
java -jar OTM-Studytrack-v0.1-alpha.jar
```

## Options view

On starting the program, the available options will be displayed. At any time, an input of '1' will display the options again

## Adding, viewing and removing user data

The user can add three types of data: Courses, task types and task entries. A task type represents a type of recurring task making up part of the coursework of a course, for example lectures or programming exercises. The user can create an arbitrary number of task types for each course they create. Task entries represent weekly log entries related to a particular task of a particular course. Their principal feature is the time spent on the task on that course week, which is given by the user.

Duplicate data is handled as follows: An attempt to add a duplicate course will result in no changes, as will an attempt to add a duplicate task type to a given course. For task entries, only one entry per week is allowed, but the user is free to log time spent to an entry as many times as they like. If an entry for a given task and week already exists, the newly logged time is added to it.

There are several options for viewing the data previousl input. These options are likely to be expanded in future versions.

The interface allows for removing courses, task types and task entries. When removing a course, all of its task types and the entries related to those task types are deleted. As the user may wish to remove a course from their list of active courses,the "Remove course from active courses" feature does not permanently delete any data. Courses may be reactivated and permanently deleted using features under the "Administrative functions" section.
