# Software requirements specification

## Description

The purpose of the application is to enable the user to track the time they spend on different types of tasks (lectures, exercises, etc.) related to university courses. Additional goals outside this core functionality include presenting statistical information about the data the program accumulates to the user, and a scheduling feature which makes use of this data.

## Basic functionality

- The user can input data into the application:
  - The user can create new task types.
  - The user can create new courses and add task types to them. A course can include an arbitrary amount of task types: It is up to the user to decide how accurately the time spent on a course is divided between various task types.
  - The user can add weekly or daily entries on the time spent on a given type of task for a specific course.
  - The data is saved to and retrieved from a database.
  - When the user removes a course, it is deleted from their set of active courses, but the data persists in the database for later use. A feature to permanently delete a course and all data related to it will also be included.

- The application uses a graphical interface to present to the user their active courses, the task types belonging to each of them, and the time spent on them so far.
  - The graphical interface is also used to input courses, task types, and time spent on them.

Further goals
- Grouping of courses by subject
- Statistics functionality: Existing user data is used to generate reports, for example "Average weekly time spent on course of subject 'Mathematics'", or "Average weekly time spent on task type 'Moodle' on course 'Tira'".
- Planning functionality: A calendar view where the user can schedule tasks. Data from the statistics functionality is used to suggest how much time a task of a given type has historically needed.