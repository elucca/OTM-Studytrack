package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import otmstudytrack.domain.StudytrackService;
import otmstudytrack.UI.TextUI;
import otmstudytrack.data.dao.CourseDao;
import otmstudytrack.data.dao.FakeCourseDao;
import otmstudytrack.data.dao.FakeTaskEntryDao;
import otmstudytrack.data.dao.FakeTaskTypeDao;
import otmstudytrack.data.dao.TaskEntryDao;
import otmstudytrack.data.dao.TaskTypeDao;

public class Main {
    
    public static void main(String[] args) throws SQLException {
        //Initializes the program
        
        //Init db
        TaskEntryDao entryDao = new FakeTaskEntryDao();
        TaskTypeDao taskDao = new FakeTaskTypeDao(entryDao);
        CourseDao courseDao = new FakeCourseDao(taskDao);
        Connection conn = DriverManager.getConnection("jdbc:sqlite:db/studytrack.db");
        
        //Init logic
        StudytrackService service = new StudytrackService(courseDao, taskDao, entryDao, conn);
        
        //Init UI
        Scanner reader = new Scanner(System.in);
        TextUI textUI = new TextUI(reader, service);
        textUI.start();
    }

}