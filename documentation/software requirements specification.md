# Software requirements specification

## Description

The purpose of the application is to enable the user to track the time they spend on different types of tasks (lectures, exercises, etc.) related to university courses. Additional goals outside this core functionality include a graphical user interface, collating and presenting statistical information about the data the program accumulates to the user, and a scheduling feature which makes use of this data.

## Basic functionality

- The user can input data into the application:
   - The user can create new task types representing a a particular type of task related to a course, for example lectures or exercises.
   - The user can create new courses and add task types to them. A course can include an arbitrary amount of task types: It is up to the user to decide how accurately the time spent on a course is divided between various task types. For example, the user may or may not divide the exercises of a course down to various types.
   - Each course belongs to a particular user-determined subject, for example mathematics.
   - The user can add weekly entries on the time spent on a given type of task for a specific course. If the user adds an entry for the same task on the same week, the time will be added to the existing entry.
- User data is saved to and retrieved from a database.
  - When the user removes a course, it is deleted from their set of active courses, but the data persists in the database for later use. 
  - The user has access to administrative functions for permanently removing data, including courses, task types and task entries, including a feature for removing all user data.
- The application includes a console interface which allows the user to view, input and remove all types of data.

## Further goals
- A graphical interface as an alternative and possibly eventual replacement to the existing console interface, with feature parity.
- Statistics functionality: Existing user data is used to generate reports, for example "Average weekly time spent on course of subject 'Mathematics'", or "Average weekly time spent on task type 'Moodle' on course 'Tira'".
- Planning functionality: A calendar view where the user can schedule tasks. Data from the statistics functionality is used to suggest how much time a task of a given type has historically needed.
