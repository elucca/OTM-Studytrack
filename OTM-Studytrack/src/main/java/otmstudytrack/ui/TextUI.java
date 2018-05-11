package otmstudytrack.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import otmstudytrack.domain.StudytrackService;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskEntry;
import otmstudytrack.domain.data.TaskType;

public class TextUI {

    private Scanner reader;
    private StudytrackService service;

    public TextUI(Scanner reader, StudytrackService service) {
        this.reader = reader;
        this.service = service;
    }

    public void start() {
        printOptions();
        
        try {
            handleInput();
        } catch (SQLException e) {
            handleDatabaseException(e);
        }
    }

    private void handleInput() throws SQLException {
        while (true) {
            int input = -1;
            try {
                input = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
                System.out.println("");
                continue;
            }

            System.out.println("");

            if (input == 1) {
                printOptions();
            }

            if (input == 2) {
                System.out.print("Input the subject of the course: ");
                String subject = reader.nextLine();

                System.out.print("Input the name of the course: ");
                String name = reader.nextLine();
                service.addCourse(name, subject);

                System.out.println("");
            }

            if (input == 3) {
                System.out.print("Input the name of the course the task belongs to: ");
                String courseName = reader.nextLine();
                System.out.print("Input the name of the new task: ");
                String taskName = reader.nextLine();

                if (service.addTaskType(taskName, courseName) == false) {
                    System.out.println("Course not found: Task not added.");
                }

                System.out.println("");
            }

            if (input == 4) {
                System.out.print("Input the name of the course the entry is related to: ");
                String courseName = reader.nextLine();
                Course foundCourse = service.getCourse(courseName);
                if (foundCourse == null) {
                    System.out.println("Course not found: Entry not added.");
                }

                System.out.print("Input the name of the task the entry is related to: ");
                String taskName = reader.nextLine();
                if (!foundCourse.getTaskTypes().contains(new TaskType(taskName, foundCourse))) {
                    System.out.println("Task not found: Entry not added.");
                }

                System.out.print("Input the course week the entry is related to: ");
                int courseWeek = Integer.parseInt(reader.nextLine());

                System.out.print("Specify hours spent: ");
                int hours = Integer.parseInt(reader.nextLine());
                System.out.print("Specify minutes spent: ");
                int minutes = Integer.parseInt(reader.nextLine());

                service.addTaskEntry(courseWeek, taskName, courseName, hours, minutes);

                System.out.println("");
            }

            if (input == 5) {
                System.out.print("Input name of course to be removed: ");
                String courseName = reader.nextLine();
                if (service.setCourseActive(courseName, false)) {
                    System.out.println("Course removed from active courses. Data retained in inactive courses.");
                    System.out.println("");
                } else {
                    System.out.println("Course not found: Course not removed from active courses.");
                    System.out.println("");
                }
            }

            if (input == 6) {
                System.out.print("The task and any related entires will be permanently deleted. Are you sure? (y/n): ");
                if (confirmYN()) {
                    System.out.print("Input name of the course the task belongs to: ");
                    String courseName = reader.nextLine();
                    System.out.print("Input name of the task to be removed: ");
                    String taskName = reader.nextLine();

                    if (service.removeTaskType(courseName, taskName)) {
                        System.out.println("");
                    } else {
                        System.out.println("Task not found: Task not removed.");
                        System.out.println("");
                    }
                } else {
                    System.out.println("");
                }

            }

            if (input == 7) {
                System.out.print("The entry will be permanently deleted. Are you sure? (y/n): ");
                if (confirmYN()) {
                    System.out.print("Input the name of the course the entry relates to: ");
                    String courseName = reader.nextLine();
                    System.out.print("Input name of the task the entry is related to: ");
                    String taskName = reader.nextLine();
                    System.out.print("Input the course week the entry is related to: ");
                    //Add check that it actually is an int, currently crashes if not
                    int week = Integer.parseInt(reader.nextLine());
                    service.removeTaskEntry(week, taskName, courseName);
                } else {
                    System.out.println("");
                }
            }

            if (input == 8) {
                List<Course> courses = service.getCoursesByActive(true);
                System.out.println("Active courses:");
                for (Course course : courses) {
                    System.out.println("  " + course.getName());
                }
                System.out.println("");
            }

            if (input == 9) {
                System.out.print("Please input the name of the course to list its tasks: ");
                String course = reader.nextLine();
                List<TaskType> tasks = service.getTaskTypesOfCourse(course);
                for (TaskType task : tasks) {
                    System.out.println(task.getName());
                }
                System.out.println("");
            }

            if (input == 10) {
                System.out.print("Please input the name of the course: ");
                String course = reader.nextLine();
                System.out.print("Please input the name of the task: ");
                String task = reader.nextLine();

                List<TaskEntry> entries = service.getTaskType(task, course).getEntries();
                for (TaskEntry entry : entries) {
                    System.out.println("Task: " + entry.getTaskType().getName() + ", time spent: " + entry.getTimeSpent().toString());
                }

                System.out.println("");
            }

            if (input == 11) {
                System.out.println("Please input the name of the course: ");
                String course = reader.nextLine();

                System.out.print("Time spent on " + course + ": " + service.getTimeSpentOnCourse(course).toString());
                System.out.println("");
            }

            if (input == 12) {
                List<Course> courses = service.getCoursesByActive(false);
                System.out.println("Inactive courses:");
                for (Course course : courses) {
                    System.out.println("  " + course.getName());
                }
                System.out.println("");
            }

            if (input == 13) {
                System.out.print("Input name of course to reactivate: ");
                String courseName = reader.nextLine();
                Course foundCourse = service.getCourse(courseName);
                if (foundCourse.isActive()) {
                    System.out.println("Course is already active. No changes made.");
                    continue;
                }

                service.setCourseActive(courseName, true);
                System.out.println("");
            }

            if (input == 14) {
                System.out.print("The course along with all of its associated tasks and entries will be permanently deleted. Are you sure? (y/n): ");
                if (confirmYN()) {
                    System.out.print("Input the name of the course to remove: ");
                    String courseName = reader.nextLine();
                    if (service.removeCourse(courseName)) {
                        System.out.println("Course removed.");
                    } else {
                        System.out.println("Course not found: No changes made.");
                        System.out.println("");
                    }
                }
            }

            if (input == 15) {
                System.out.print("ALL user data (courses, tasks and entries) will be permanently deleted. Are you sure? (y/n): ");
                if (confirmYN()) {
                    service.removeAllData();
                    System.out.println("Data removed.");
                } else {
                    System.out.println("Operation aborted: No changes made.");

                }
                System.out.println("");
            }

            if (input == 16) {
                break;
            }
        }

    }

    private void printOptions() {
        System.out.println("OTM-Studytrack - Console interface");

        System.out.println("");
        System.out.println("Options:");
        System.out.println("");

        System.out.println("  1. Show options");

        System.out.println("");

        System.out.println("  Input data:");
        System.out.println("    2. Add a new course");
        System.out.println("    3. Add a new task type to a course");
        System.out.println("    4. Log an entry for a task");

        System.out.println("");

        System.out.println("  Remove data:");
        System.out.println("    5. Remove course from active courses");
        System.out.println("    6. Remove task from a course");
        System.out.println("    7. Remove a log entry from a task");

        System.out.println("");

        System.out.println("  Display data:");
        System.out.println("    8. List active courses");
        System.out.println("    9. List task types of a course");
        System.out.println("    10. List the entries of a task");
        System.out.println("    11. Display time spent on a course");

        System.out.println("");

        System.out.println("  Administrative functions:");
        System.out.println("    12. List inactive courses");
        System.out.println("    13. Reactivate a course");
        System.out.println("    14. Permanently remove a course");
        System.out.println("    15. Permanently remove all data");

        System.out.println("");

        System.out.println("  16. Exit program");

        System.out.println("");

        System.out.println("Input: ");
    }

    private boolean confirmYN() {
        while (true) {
            char input = reader.nextLine().charAt(0);
            if (input == 'y') {
                System.out.println("");
                return true;
            }
            if (input == 'n') {
                System.out.println("");
                return false;
            }

            System.out.print("Please input 'y' or 'n'.");
            System.out.println("");
        }
    }

    private void handleDatabaseException(SQLException e) {
        System.out.println("A database error has occurred. No data has been altered.");
    }

}
