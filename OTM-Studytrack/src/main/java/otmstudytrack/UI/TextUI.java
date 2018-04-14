package otmstudytrack.UI;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import otmstudytrack.domain.StudytrackService;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public class TextUI {

    private Scanner reader;
    private StudytrackService taskService;

    public TextUI(Scanner reader, StudytrackService taskService) {
        this.reader = reader;
        this.taskService = taskService;
    }

    public void start() throws SQLException {
        printOptions();
        handleInput();

    }

    private void handleInput() throws SQLException {
        while (true) {
            int input = Integer.parseInt(reader.nextLine());
            System.out.println("");

            if (input == 1) {
                printOptions();
            }

            if (input == 2) {
                System.out.print("Please input the name of the course to add: ");
                String name = reader.nextLine();

                taskService.addCourse(name);

                System.out.println("");
            }

            if (input == 3) {
                System.out.print("Please input the name of the course the task belongs to: ");
                String courseName = reader.nextLine();
                System.out.print("Please input the name of the new task: ");
                String taskName = reader.nextLine();

                if (taskService.addTaskType(taskName, courseName) == false) {
                    System.out.println("Course not found: Task type not added.");
                }

                System.out.println("");
            }

            if (input == 4) {
                System.out.print("Please input the name of the course the task belongs to: ");
                String courseName = reader.nextLine();
                System.out.print("Please input the name of the task the entry is related to: ");
                String taskName = reader.nextLine();
                System.out.print("Please input the course week the entry is related to: ");
                int courseWeek = Integer.parseInt(reader.nextLine());

                System.out.print("Please specify hours spent: ");
                int hours = Integer.parseInt(reader.nextLine());
                System.out.print("Please specify minutes spent: ");
                int minutes = Integer.parseInt(reader.nextLine());

                taskService.addTaskEntry(courseWeek, taskName, courseName, hours, minutes);

                System.out.println("");
            }

            if (input == 5) {
                List<Course> courses = taskService.getCourses();
                System.out.println("Courses:");
                for (Course course : courses) {
                    System.out.println("  " + course.getName());
                }
                System.out.println("");
            }

            if (input == 6) {
                //Should probably display time spent on each one to be actually useful
                System.out.print("Please input the name of the course to list its tasks: ");
                String course = reader.nextLine();
                List<TaskType> tasks = taskService.getTaskTypesOfCourse(course);
                for (TaskType task : tasks) {
                    System.out.println(task.getName());
                }
                System.out.println("");
            }

            if (input == 7) {
                //Printing the time spent duration is currently un-good, prints seconds
                //Replace it with something better to print hours and minutes
                //Parsing that possibly belongs to program logic and not UI
                System.out.print("Please input the name of the course:  ");
                String course = reader.nextLine();
                System.out.print("Please input the name of the task:  ");
                String task = reader.nextLine();

                List<TaskEntry> entries = taskService.getEntriesOfTaskType(task, course);
                for (TaskEntry entry : entries) {
                    System.out.println("Task: " + entry.getTaskType().getName() + ", time spent: " + entry.getTimeSpent().toString());
                }

                System.out.println("");
            }

            if (input == 8) {
                //Doesn't work, bug most likely elsewhere, will get caught in testing
                System.out.println("Please input the name of the course: ");
                String course = reader.nextLine();

                System.out.print("Time spent on " + course + ": " + taskService.getTimeSpentOnCourse(course));
                System.out.println("");
            }

            if (input == 9) {
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
        System.out.println("    4. Add a new entry for a task");

        System.out.println("");

        System.out.println("  Display data:");
        System.out.println("    5. List courses");
        System.out.println("    6. List task types of a course");
        System.out.println("    7. List the entries of a task");
        System.out.println("    8. Display time spent on a course");

        System.out.println("");

        System.out.println("  9. Exit program");

        System.out.println("");

        System.out.println("Input: ");
    }

}
